package com.sqlbatch.main;

import java.io.IOException;
import java.sql.SQLException;

import com.sqlbatch.ui.UISqlBatch;

public class App {
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		UISqlBatch frame = new UISqlBatch();
		frame.setDefaultCloseOperation(3);
	}
}