package com.sqlbatch.observer;

import java.util.ArrayList;
import java.util.List;

public class ProgressObservable {

	public List<ProgressObserver> observerList = new ArrayList<ProgressObserver>();
	private int totalOfFiles;
	private int currentFile;
	
	public void addObserver(ProgressObserver observer) {
		observerList.add(observer);
	}
	
	public void notifyObservers(int totalOfFiles, int currentFile) {
		this.totalOfFiles = totalOfFiles;
		this.currentFile = currentFile;
		observerList.forEach((observer) -> observer.notifyObserver());
	}

	public int getTotalOfFiles() {
		return totalOfFiles;
	}

	public int getCurrentFile() {
		return currentFile;
	}
	
}
