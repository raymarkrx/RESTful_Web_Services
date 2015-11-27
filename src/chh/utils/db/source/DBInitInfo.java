package chh.utils.db.source;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * 初始化，模拟加载所有的配置文件
 * 
 * @author Ken.F
 * 
 */
public class DBInitInfo {
	public static List<DBBean> beans = new ArrayList<DBBean>();

	static Properties prop = new Properties();

	static {
		try {
			InputStream in = DBInitInfo.class
					.getResourceAsStream("/conf.properties");
			prop.load(in);

			String separtor = prop.getProperty("string.separtor");
			String urlStr = prop.getProperty("oracle.db.url");
			if (!StringUtils.isEmpty(urlStr)) {
				String[] urls = urlStr.split(separtor);
				for (int i = 0; i < urls.length; i++) {
					DBBean beanOracle = new DBBean();
					beanOracle.setDriverName(prop.getProperty(
							"oracle.db.driverName").split(separtor)[i]);
					beanOracle.setUrl(urls[i]);
					beanOracle.setUserName(prop.getProperty(
							"oracle.db.userName").split(separtor)[i]);
					beanOracle.setPassword(prop.getProperty(
							"oracle.db.password").split(separtor)[i]);
					beanOracle.setDataSource(prop.getProperty(
							"oracle.db.dataSource").split(separtor)[i]);
					beanOracle
							.setConnectionTestQuery(prop.getProperty(
									"oracle.db.connectionTestQuery").split(
									separtor)[i]);
					beanOracle.setConnectionTimeout(prop.getProperty(
							"oracle.db.connectionTimeout").split(separtor)[i]);
					beanOracle.setMaxPoolSize(prop.getProperty(
							"oracle.db.maxPoolSize").split(separtor)[i]);
					beanOracle.setMaxLifetime(prop.getProperty(
							"oracle.db.maxLifetime").split(separtor)[i]);
					beanOracle.setMinIdleSize(prop.getProperty(
							"oracle.db.minIdleSize").split(separtor)[i]);
					beans.add(beanOracle);
				}
			}

			urlStr = prop.getProperty("mysql.db.url");
			if (!StringUtils.isEmpty(urlStr)) {
				String[] urls = urlStr.split(separtor);
				for (int i = 0; i < urls.length; i++) {
					DBBean beanMysql = new DBBean();
					beanMysql.setDriverName(prop.getProperty(
							"mysql.db.driverName").split(separtor)[i]);
					beanMysql.setUrl(urls[i]);
					beanMysql.setUserName(prop.getProperty("mysql.db.userName")
							.split(separtor)[i]);
					beanMysql.setPassword(prop.getProperty("mysql.db.password")
							.split(separtor)[i]);
					beanMysql.setDataSource(prop.getProperty(
							"mysql.db.dataSource").split(separtor)[i]);
					beanMysql.setConnectionTestQuery(prop.getProperty(
							"mysql.db.connectionTestQuery").split(separtor)[i]);
					beanMysql.setConnectionTimeout(prop.getProperty(
							"mysql.db.connectionTimeout").split(separtor)[i]);
					beanMysql.setMaxPoolSize(prop.getProperty(
							"mysql.db.maxPoolSize").split(separtor)[i]);
					beanMysql.setMaxLifetime(prop.getProperty(
							"mysql.db.maxLifetime").split(separtor)[i]);
					beanMysql.setMinIdleSize(prop.getProperty(
							"mysql.db.minIdleSize").split(separtor)[i]);
					beans.add(beanMysql);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
