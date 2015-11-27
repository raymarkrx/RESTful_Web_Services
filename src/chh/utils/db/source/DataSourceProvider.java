package chh.utils.db.source;

import java.util.List;




import chh.utils.db.source.common.ConnectionProvider;
import chh.utils.db.source.common.HikariCPConnectionProvider;

import com.zaxxer.hikari.HikariConfig;

public class DataSourceProvider {
	private static ConnectionProvider getConnectionProvider(String dataSource) {
		List<DBBean> beans = DBInitInfo.beans;
		HikariConfig config = new HikariConfig();
		if (beans != null) {
			for (DBBean bean : beans) {
				if (dataSource.equals(bean.getDataSource())) {
					config.setDriverClassName(bean.getDriverName());
					config.setJdbcUrl(bean.getUrl());
					config.setUsername(bean.getUserName());
					config.setPassword(bean.getPassword());
					config.setConnectionTestQuery(bean.getConnectionTestQuery());
					config.setConnectionTimeout(Long.parseLong(bean
							.getConnectionTimeout()));
					config.setMinimumIdle(Integer.parseInt(bean
							.getMinIdleSize()));
					config.setMaxLifetime(Long.parseLong(bean.getMaxLifetime()));
					config.setMaximumPoolSize(Integer.parseInt(bean
							.getMaxPoolSize()));
					config.setPoolName(bean.getDataSource());
					break;
				}
			}
		}
		ConnectionProvider connectionProvider = new HikariCPConnectionProvider(	config);

		return connectionProvider;
	}

	/**
	 * 初始化数据库连接池
	 * 
	 * @param dataSource
	 * @return
	 */
	public static ConnectionProvider prepare(String dataSource) {
		ConnectionProvider connectionProvider = getConnectionProvider(dataSource);
		connectionProvider.prepare();
		return connectionProvider;
	}

	/**
	 * 关闭数据库连接池
	 * 
	 * @param connectionProvider
	 */
	public static void cleanup(ConnectionProvider connectionProvider) {
		connectionProvider.cleanup();
	}

}
