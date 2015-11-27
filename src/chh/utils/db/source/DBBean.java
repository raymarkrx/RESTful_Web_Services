package chh.utils.db.source;

/**
 * 这是外部可以配置的连接池属性 可以允许外部配置，拥有默认值
 * 
 * @author Ken.F
 * 
 */
public class DBBean {
	// 连接池属性
	private String driverName;
	private String url;
	private String userName;
	private String password;
	private String dataSource;
	private String connectionTestQuery;
	private String connectionTimeout;
	private String maxLifetime;
	private String maxPoolSize;
	private String minIdleSize;

	public DBBean() {

	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getConnectionTestQuery() {
		return connectionTestQuery;
	}

	public void setConnectionTestQuery(String connectionTestQuery) {
		this.connectionTestQuery = connectionTestQuery;
	}

	public String getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(String connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public String getMaxLifetime() {
		return maxLifetime;
	}

	public void setMaxLifetime(String maxLifetime) {
		this.maxLifetime = maxLifetime;
	}

	public String getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(String maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public String getMinIdleSize() {
		return minIdleSize;
	}

	public void setMinIdleSize(String minIdleSize) {
		this.minIdleSize = minIdleSize;
	}
}
