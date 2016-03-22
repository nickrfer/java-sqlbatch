package com.sqlbatch.util;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class CommandDirectory {

	private Queue<CommandFile> queueCommandFile = new LinkedList<CommandFile>();
	
	public CommandDirectory(File directory) {
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				queueCommandFile.add(new CommandFile(file));
			}
		}
	}
	
	public CommandFile nextFile() {
		return queueCommandFile.poll();
	}
	
	public boolean hasNext() {
		return !queueCommandFile.isEmpty();
	}
	
	public int getTotalFiles() {
		return queueCommandFile.size();
	}
}
