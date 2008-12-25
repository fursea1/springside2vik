package org.springside.core.test.support;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.operation.DatabaseOperation;

/**
 * 仅暴露Dbunit对Excel的支持，是因为几种数据提供方案中，Excel编辑数据最容易，在实践中被使用的最广泛。 支持每次Setup是否清空数据的选择
 *
 * @author Anders.Lin
 */
public class DatabaseUnitHelper {

	public static final String DEFAULT_CONFIG_FILE = "DbUnit.properties";

	public static final String DEFAULT_DRIVER = "org.hsqldb.jdbcDriver";

	public static final String DEFAULT_PASS = "";

	public static final String DEFAULT_URL = "jdbc:hsqldb:mem:test";

	public static final String DEFAULT_USER = "sa";

	public static final String DRIVER = "connection.driver_class";

	public static final String PASS = "connection.password";

	public static final String URL = "connection.url";

	public static final String USER = "connection.username";

	public static final String XLSPATH = "xls.path";

	private static PropertiesConfiguration config = new PropertiesConfiguration();

	private static final Log log = LogFactory.getLog(DatabaseUnitHelper.class);

	private boolean alreadySetUp = false;

	private boolean cleanInsertEachSetup = false;

	private String connectionPassword;

	private String connectionUrl;

	private String connectionUser;

	private String driverClazz;

	private String xlsPath;

	public DatabaseUnitHelper() {
		doInit(DEFAULT_CONFIG_FILE);
	}

	/**
	 * Close the specified connection. Ovverride this method of you want to keep your connection alive between tests.
	 */
	protected void closeConnection(IDatabaseConnection connection) throws Exception {
		connection.close();
	}

	// 设定是否每次SetUp都清空数据
	public void doCleanInsertEachSetup(boolean flag) {
		this.cleanInsertEachSetup = flag;
	}

	public void doInit(Properties properties) {
		if (properties == null || properties.isEmpty()) {
			return;
		}
		this.driverClazz = properties.getProperty(DRIVER);
		this.connectionUrl = properties.getProperty(URL);
		this.connectionUser = properties.getProperty(USER);
		this.connectionPassword = properties.getProperty(PASS);
	}

	public void doInit(String path) {
		try {
			config.load(path);
		} catch (ConfigurationException e) {
			log.error("could not load the properties file : " + path, e);
		}
		Properties properties = new Properties();
		properties.setProperty(DRIVER, config.getString(DRIVER, DEFAULT_DRIVER));
		properties.setProperty(URL, config.getString(URL, DEFAULT_URL));
		properties.setProperty(USER, config.getString(USER, DEFAULT_USER));
		properties.setProperty(PASS, config.getString(PASS, DEFAULT_PASS));
		doInit(properties);
	}

	private void executeOperation(DatabaseOperation operation) throws Exception {
		if (operation != DatabaseOperation.NONE) {
			IDatabaseConnection connection = getConnection();
			try {
				operation.execute(connection, getDataSet());
			} finally {
				closeConnection(connection);
			}
		}
	}

	/**
	 * Returns the test database connection.
	 */
	public IDatabaseConnection getConnection() throws Exception {
		Class.forName(this.driverClazz);
		Connection jdbcConnection = DriverManager.getConnection(this.connectionUrl, this.connectionUser,
				this.connectionPassword);
		return new DatabaseConnection(jdbcConnection);
	}

	/**
	 * Returns the test dataset.
	 */
	protected IDataSet getDataSet() throws Exception {
		return new XlsDataSet(new FileInputStream(xlsPath));
	}

	public Connection getJdbcConnection() throws Exception {
		return this.getConnection().getConnection();
	}

	/**
	 * Returns the database operation executed in test setup.
	 */
	protected DatabaseOperation getSetUpOperation() throws Exception {
		if (!this.alreadySetUp) {
			return DatabaseOperation.CLEAN_INSERT;
		}
		if (this.cleanInsertEachSetup) {
			return DatabaseOperation.CLEAN_INSERT;
		}
		return DatabaseOperation.NONE;
	}

	/**
	 * Returns the database operation executed in test cleanup.
	 */
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.NONE;
	}

	public void setUp() {
		try {
			executeOperation(getSetUpOperation());
		} catch (Exception e) {
			throw new RuntimeException("error when doing dbunit setUp", e);
		}
	}

	public void setXlsPath(String path) {
		this.xlsPath = path;
	}

	public void tearDown() {
		try {
			executeOperation(getTearDownOperation());
		} catch (Exception e) {
			throw new RuntimeException("error when doing dbunit tearDown", e);
		}
	}
}
