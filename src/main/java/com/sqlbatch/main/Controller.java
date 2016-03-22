package com.sqlbatch.main;

import java.io.File;

import org.apache.log4j.Logger;

import com.sqlbatch.dao.Dao;
import com.sqlbatch.exception.CommandFileException;
import com.sqlbatch.exception.ControllerException;
import com.sqlbatch.exception.DaoException;
import com.sqlbatch.observer.ProgressObservable;
import com.sqlbatch.util.CommandDirectory;
import com.sqlbatch.util.CommandFile;
import com.sqlbatch.util.DBParameter;

public class Controller {
	
	private static final Logger log = Logger.getLogger(Controller.class);
	
	public void insertBatch(DBParameter dbParameter, File directory, ProgressObservable observable) throws ControllerException {
		validateDirectory(directory);

		log.info("--------------------------------------------------------------------");
		log.info("Starting import");
		
		Dao dao = new Dao(dbParameter);
		try {
			CommandDirectory commandDirectory = new CommandDirectory(directory);
			int totalFilesInFolder = commandDirectory.getTotalFiles();
			log.info("Total files in folder: " + totalFilesInFolder);
			
			int currentFile = 1;
			while (commandDirectory.hasNext()) {
				CommandFile commandFile = commandDirectory.nextFile();
				
				log.info(String.format("Starting file: %s", commandFile.getFileName()));
				dao.insertBatch(dbParameter, commandFile);
				
				commandFile.moveFile();
				
				observable.notifyObservers(totalFilesInFolder, currentFile++);
			}
		} catch (DaoException e) {
			handleException(e);
		} catch (CommandFileException e) {
			handleException(e);
		} finally {
			try {
				dao.finishConnection();
			} catch (DaoException e) {
				handleException(e);
			}
		}
	}

	private void handleException(Exception e) throws ControllerException {
		log.error(e.getMessage(), e);
		throw new ControllerException(e.getMessage(), e);
	}

	private void validateDirectory(File directory) throws ControllerException {
		if (directory != null && directory.canRead()) {
			if (directory.listFiles().length == 0) {
				throw new ControllerException("Please inform a directory that has files with commands in it");
			}
		} else {
			throw new ControllerException("Directory not informed");
		}
	}

}