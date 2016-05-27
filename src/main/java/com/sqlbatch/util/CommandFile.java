package com.sqlbatch.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.sqlbatch.exception.CommandFileException;

public class CommandFile {

	private File file;
	private BufferedReader bufferedReader = null;
	private int currentLine;
	
	private static String[] DML_COMMANDS = new String[] {
			"INSERT", "DELETE", "UPDATE", "MERGE", "CALL"
	};
	
	public CommandFile(File file) {
		this.file = file;
	}

	public List<String> readPaginatedCommands() throws CommandFileException {
		try {
			if (bufferedReader == null) {
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			}
			return getPaginatedCommands();
		} catch (IOException e) {
			throw new CommandFileException(String.format("Error reading the file %s", file.getName()), e);
		}
	}

	private List<String> getPaginatedCommands() throws IOException {
		List<String> commandLines = new ArrayList<String>();
		
		int currentCommand = 0;
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			++currentLine;
			
			if (isDMLCommand(line)) {
				++currentCommand;
				commandLines.add(line);
			}
			if (currentCommand >= 1000) {
				break;
			}
		}
		return commandLines;
	}

	private boolean isDMLCommand(String line) {
		for (String dml : DML_COMMANDS) {
			if (line.startsWith(dml)) {
				return true;
			}
		}
		return false;
	}
	
	public String getFileName() {
		return file.getName();
	}
	
	public int getCurrentLine() {
		return currentLine;
	}
	
	public void moveFile() throws CommandFileException {
		File executedFolder = new File(file.getParent() + "/executed/");
		executedFolder.mkdirs();
		
		if (!file.renameTo(new File(executedFolder.getPath() + "/" + file.getName()))) {
			throw new CommandFileException(String.format("Error trying to move the file %s to folder 'executed'", getFileName()));
		}
	}
	
	public void close() throws CommandFileException {
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				throw new CommandFileException(String.format("Error trying to close the file reader of %s", file.getName()), e);
			}
		}
	}
	
}
