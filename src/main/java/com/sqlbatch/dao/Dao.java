package com.sqlbatch.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.sqlbatch.dao.connection.ConnectionFactory;
import com.sqlbatch.exception.CommandFileException;
import com.sqlbatch.exception.DaoException;
import com.sqlbatch.util.CommandFile;
import com.sqlbatch.util.DBParameterVO;

public class Dao {
	
	private static final Logger log = Logger.getLogger(Dao.class);
	private ConnectionFactory factory;
	private int lastCommitLine;
	private String lastCommand;

	public Dao(DBParameterVO vo) {
		this.factory = new ConnectionFactory(vo);
	}

	public void insertBatch(DBParameterVO vo, CommandFile commandFile) throws DaoException {
		Statement stmt = null;
		
		try {
			stmt = createStatement();
			executeBatchCommand(commandFile, stmt);
		} catch (SQLException e) {
			throw new DaoException(String.format("Command with error. %n %nFile: %s %nLine: %d %nCommand: %s %nLine of Last Commit: %d ", 
					commandFile.getFileName(), commandFile.getCurrentLine(), lastCommand, lastCommitLine), e);
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			closeResources(commandFile, stmt);
		}
	}

	private void executeBatchCommand(CommandFile commandFile, Statement stmt)
			throws CommandFileException, SQLException {
		List<String> commandList = null;
		while (!(commandList = commandFile.readPaginatedCommands()).isEmpty()) {
			
			for (String command : commandList) {
				lastCommand = command;
				stmt.addBatch(command);
			}
			stmt.executeBatch();
			lastCommitLine = commandFile.getCurrentLine();
		}
	}
	
	private Statement createStatement() throws DaoException {
		try {
			return this.factory.getConnection().createStatement();
		} catch (SQLException e) {
			throw new DaoException("Error when trying to connect to the DB", e);
		} catch (ClassNotFoundException e) {
			throw new DaoException("Error ocurred when searching for the DB Connection adapter", e);
		}
	}
	
	public void finishConnection() throws DaoException {
		try {
			factory.getConnection().close();
			factory.clearConnection();
		} catch (Exception e) {
			log.debug("Error trying to finish DB connection", e);
		}
	}

	private void closeResources(CommandFile comandoArquivo, Statement stmt) throws DaoException {
		try {
			comandoArquivo.close();
		} catch (CommandFileException e) {
			throw new DaoException(e.getMessage(), e);
		}
		try {
			stmt.close();
		} catch (Exception e) {
			log.debug("Error trying to close file resources", e);
		}
	}

}