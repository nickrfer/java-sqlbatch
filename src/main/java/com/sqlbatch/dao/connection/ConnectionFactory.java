package com.sqlbatch.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.sqlbatch.enums.DatabaseEnum;
import com.sqlbatch.util.DBParameterVO;

public class ConnectionFactory {
	private static Map<DatabaseEnum, BaseConnectionProvider> connectionMap;
	
	static {
		connectionMap.put(DatabaseEnum.DB2, new DB2Connection());
		connectionMap.put(DatabaseEnum.ORACLE, new OracleConnection());
		connectionMap.put(DatabaseEnum.SQLSERVER, new SQLServerConnection());
	}
	
	private static Connection conx;
	private DBParameterVO dbParameterVO;

	public ConnectionFactory(DBParameterVO dbParameterVO) {
		this.dbParameterVO = dbParameterVO;
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		if (conx == null) {
			DatabaseEnum databaseEnum = dbParameterVO.getDatabaseEnum();
			connectionMap.get(databaseEnum).getConnection(dbParameterVO);
		}
		return conx;
	}
	
	public void clearConnection() {
		conx = null;
	}

}