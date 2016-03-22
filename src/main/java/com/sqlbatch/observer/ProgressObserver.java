package com.sqlbatch.observer;

import javax.swing.JProgressBar;

public class ProgressObserver {
	
	private ProgressObservable observable;
	private JProgressBar progressBar;
	
	public ProgressObserver(ProgressObservable observable, JProgressBar progressBar) {
		this.observable = observable;
		this.progressBar = progressBar;
	}

	public void notifyObserver() {
		progressBar.setValue(Math.round(((float)observable.getCurrentFile() / (float)observable.getTotalOfFiles()) * 100));
		progressBar.update(progressBar.getGraphics());
	}
	
}
