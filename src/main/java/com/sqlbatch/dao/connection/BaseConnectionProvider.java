package com.sqlbatch.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sqlbatch.util.DBParameterVO;

public abstract class BaseConnectionProvider {

	public Connection getConnection(DBParameterVO dbParameterVO) throws ClassNotFoundException, SQLException {
		Class.forName(getDriverClass());
		String urlConx = prepareConnectionUrl(dbParameterVO);
		return DriverManager.getConnection(urlConx, dbParameterVO.getDBUser(), dbParameterVO.getDBPassword());
	}
	
	protected String prepareConnectionUrl(DBParameterVO dbParameterVO) {
		return String.format(getConnectionURL(), dbParameterVO.getServerName(), 
				dbParameterVO.getDBPort(), dbParameterVO.getDBName());
	}

	public abstract String getDriverClass();
	
	public abstract String getConnectionURL();
	
}
