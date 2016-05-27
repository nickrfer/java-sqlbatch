package com.sqlbatch.dao.connection;

import com.sqlbatch.util.DBParameterVO;

public class OracleConnection extends BaseConnectionProvider {
	private static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	private static final String CONNECTION_URL = "jdbc:oracle:thin:%s/%s@%s:%s:%s";

	@Override
	protected String prepareConnectionUrl(DBParameterVO dbParameterVO) {
		return String.format(getConnectionURL(), dbParameterVO.getDBUser(),
				dbParameterVO.getDBPassword(), dbParameterVO.getServerName(), 
				dbParameterVO.getDBPort(), dbParameterVO.getDBName());
	}
	
	@Override
	public String getDriverClass() {
		return DRIVER_CLASS;
	}

	@Override
	public String getConnectionURL() {
		return CONNECTION_URL;
	}
	

}
