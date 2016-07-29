package com.sqlbatch.ui;

import com.sqlbatch.enums.DatabaseEnum;
import com.sqlbatch.exception.ControllerException;
import com.sqlbatch.main.Controller;
import com.sqlbatch.observer.ProgressObservable;
import com.sqlbatch.observer.ProgressObserver;
import com.sqlbatch.util.DBParameterVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class UISqlBatch extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(UISqlBatch.class);
	private JLabel lbDB;
	private JLabel lbDBName;
	private JLabel lbServerName;
	private JLabel lbDBPort;
	private JLabel lbDBUser;
	private JLabel lbDBPassword;
	private JComboBox<DatabaseEnum> cbDB;
	private JTextField txDBName;
	private JTextField txServerName;
	private JTextField txDBPort;
	private JTextField txDBUser;
	private JButton btExecute;
	private JButton btClean;
	private JFileChooser uploadComponent;
	private JPasswordField txDBPassword;
	private File uploadDirectory;
	private Container mainContainer;
	private JProgressBar progressBar;

	public UISqlBatch() {
		super("SQL Batch");
		this.mainContainer = getContentPane();
		useTheme();

		this.lbDB = new JLabel("Database:");
		this.lbDBName = new JLabel("Database Name:");
		this.lbServerName = new JLabel("Server Name:");
		this.lbDBPort = new JLabel("Database Port:");
		this.lbDBUser = new JLabel("User:");
		this.lbDBPassword = new JLabel("Password:");

		this.lbDB.setBounds(10, 15, 240, 15);
		this.lbDBName.setBounds(10, 55, 240, 15);
		this.lbServerName.setBounds(10, 95, 240, 15);
		this.lbDBPort.setBounds(10, 135, 240, 15);
		this.lbDBUser.setBounds(10, 175, 240, 15);
		this.lbDBPassword.setBounds(10, 215, 240, 15);

		this.mainContainer.add(this.lbDB);
		this.mainContainer.add(this.lbDBName);
		this.mainContainer.add(this.lbServerName);
		this.mainContainer.add(this.lbDBPort);
		this.mainContainer.add(this.lbDBUser);
		this.mainContainer.add(this.lbDBPassword);

		this.cbDB = new JComboBox<>(DatabaseEnum.values());
		this.txDBName = new JTextField();
		this.txServerName = new JTextField();
		this.txDBPort = new JTextField();
		this.txDBUser = new JTextField();
		this.txDBPassword = new JPasswordField();

		this.cbDB.setBounds(10, 30, 240, 20);
		this.txDBName.setBounds(10, 70, 240, 20);
		this.txServerName.setBounds(10, 110, 240, 20);
		this.txDBPort.setBounds(10, 150, 240, 20);
		this.txDBUser.setBounds(10, 190, 240, 20);
		this.txDBPassword.setBounds(10, 230, 240, 20);
		

		this.mainContainer.add(this.cbDB);
		this.mainContainer.add(this.txDBName);
		this.mainContainer.add(this.txServerName);
		this.mainContainer.add(this.txDBPort);
		this.mainContainer.add(this.txDBUser);
		this.mainContainer.add(this.txDBPassword);

		this.btExecute = new JButton("Execute");
		this.btClean = new JButton("Clean");

		this.btExecute.setBounds(10, 270, 80, 30);
		this.btExecute.setOpaque(false);
		this.btClean.setBounds(170, 270, 80, 30);
		this.btClean.setOpaque(false);
		
		this.progressBar = new JProgressBar();
		this.progressBar.setValue(0);
		this.progressBar.setStringPainted(true);
	    this.progressBar.setBounds(10, 310, 250, 30);
	    this.add(progressBar);

		this.mainContainer.add(this.btExecute);
		this.mainContainer.add(this.btClean);

		Image image = Toolkit.getDefaultToolkit()
				.getImage(UISqlBatch.class.getResource("/images/glyphicons-37-file.png"));

		setLayout(null);
		setVisible(true);
		setSize(310, 350);
		setIconImage(image);
		setResizable(false);
		setLocationRelativeTo(null);

		this.btExecute.addActionListener((event) -> UISqlBatch.this.onClickExecute());
		this.btClean.addActionListener((event) -> UISqlBatch.this.cleanFields());
		
		try {
			readProperties();
		} catch (IOException e) {
			log.debug("Error trying to read properties file", e);
		}
	}

	private void readProperties() throws IOException {
		final Properties properties = new Properties();
		try (final InputStream stream = 
		           new FileInputStream(new File(new URL(getClass().getProtectionDomain().getCodeSource().getLocation(), "sqlbatch.properties").toURI()))) {
			if (stream != null) {
				properties.load(stream);
				this.txDBName.setText(properties.getProperty("db.name"));
				this.txServerName.setText(properties.getProperty("server.name"));
				this.txDBPort.setText(properties.getProperty("db.port"));
				this.txDBUser.setText(properties.getProperty("db.user"));
				this.txDBPassword.setText(properties.getProperty("db.password"));

				String path = properties.getProperty("import.path");
				if (path != null) {
					path = path.replace('\\', '/');
					uploadDirectory = new File(path);
				}
			}
		} catch (URISyntaxException e) {
			throw new IOException(e);
		}
	}

	private void onClickExecute() {
		Cursor cursor = Cursor.getPredefinedCursor(3);
		this.mainContainer.setCursor(cursor);

		if (validateRequiredFields().booleanValue()) {
			if (uploadDirectory == null) {
				createUploadWindow();
			}
			
			this.progressBar.setValue(0);
			this.btExecute.setEnabled(false);
			DBParameterVO parameterVO = createParameterVO();
			Controller controller = new Controller();
			
			try {
				ProgressObservable observable = new ProgressObservable();
				observable.addObserver(new ProgressObserver(observable, progressBar));
				
				controller.insertBatch(parameterVO, this.uploadDirectory, observable);
				JOptionPane.showMessageDialog(this, "Batch executed!", "Alert", JOptionPane.INFORMATION_MESSAGE);
			} catch (ControllerException e) {
				JOptionPane.showMessageDialog(this, String.format(String.format("%s.", e.getLocalizedMessage())), "Alert", JOptionPane.ERROR_MESSAGE);
			} finally {
				this.btExecute.setEnabled(true);
			}
		}
		cursor = Cursor.getDefaultCursor();
		this.mainContainer.setCursor(cursor);
	}

	private void createUploadWindow() {
		uploadComponent = new JFileChooser();
		uploadComponent.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int valorRetorno = this.uploadComponent.showOpenDialog(this.mainContainer);
		if (valorRetorno == 0) {
			this.uploadDirectory = this.uploadComponent.getSelectedFile();
		}
	}

	private DBParameterVO createParameterVO() {
		DBParameterVO dbParameter = new DBParameterVO();
		dbParameter.setDatabaseEnum((DatabaseEnum) cbDB.getSelectedItem());
		dbParameter.setDBName(this.txDBName.getText());
		dbParameter.setServerName(this.txServerName.getText());
		dbParameter.setDBPort(this.txDBPort.getText());
		dbParameter.setDBUser(this.txDBUser.getText());
		dbParameter.setDBPassword(new String(this.txDBPassword.getPassword()));
		return dbParameter;
	}

	private Boolean validateRequiredFields() {
		Boolean valid = Boolean.valueOf(true);
		StringBuffer sb = new StringBuffer();

		if (this.txDBName.getText().trim().length() < 1) {
			sb.append("Database is required. \n");
		}
		if (this.txDBName.getText().trim().length() < 1) {
			sb.append("Database Name is required. \n");
		}
		if (this.txServerName.getText().trim().length() < 1) {
			sb.append("Server Name is required. \n");
		}
		if (this.txDBPort.getText().trim().length() < 1) {
			sb.append("Database Port is required. \n");
		}
		if (this.txDBUser.getText().trim().length() < 1) {
			sb.append("User is required. \n");
		}
		if (this.txDBPassword.getPassword().length < 1) {
			sb.append("Password is required. \n");
		}

		if (sb.toString().trim().length() > 0) {
			valid = false;
			JOptionPane.showMessageDialog(this, sb.toString());
		}

		return valid;
	}

	private void cleanFields() {
		this.cbDB.setSelectedItem(null);
		this.txDBName.setText("");
		this.txServerName.setText("");
		this.txDBPort.setText("");
		this.txDBUser.setText("");
		this.txDBPassword.setText("");
		this.uploadDirectory = null;
		this.progressBar.setValue(0);
	}

	private void useTheme() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Theme not found.");
			e.printStackTrace();
		} catch (InstantiationException e) {
			JOptionPane.showMessageDialog(this, "Theme not found.");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			JOptionPane.showMessageDialog(this, "Theme not found.");
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(this, "Theme not supported.");
			e.printStackTrace();
		}
	}
}