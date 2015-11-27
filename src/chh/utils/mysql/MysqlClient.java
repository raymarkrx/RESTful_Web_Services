package chh.utils.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import chh.utils.db.source.DataSourceProvider;
import chh.utils.db.source.common.ConnectionProvider;



public class MysqlClient {

	abstract static class Executor<T> {
		ConnectionProvider connProvider;

		public Executor(ConnectionProvider connectionProvider) {
			connProvider = connectionProvider;
		}

		abstract T execute(Connection conn);

		public T getResult() {
			T result = null;
			Connection conn = null;
			try {
				conn = getConnection();
				result = execute(conn);
			} catch (Throwable e) {
				throw new RuntimeException("Execute exception", e);
			} finally {
				closeConnection(conn);
			}
			return result;
		}

		private Connection getConnection() {
			return connProvider.getConnection();
		}

		private void closeConnection(Connection connection) {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RuntimeException("Failed to close connection", e);
				}
			}
		}
	}

	/**
	 * insert update delete SQL语句的执行的统一方法
	 * 
	 * @param connProvider
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数数组，若没有参数则为null
	 * @return 受影响的行数
	 */
	public static int executeUpdate(ConnectionProvider connProvider,
			final String sql, final Object[] params) {
		return new Executor<Integer>(connProvider) {
			@Override
			Integer execute(Connection conn) {
				int cnt = 0;
				try {
					PreparedStatement preparedStatement = conn
							.prepareStatement(sql);

					// 参数赋值
					if (params != null) {
						for (int i = 0; i < params.length; i++) {
							preparedStatement.setObject(i + 1, params[i]);
						}
					}

					// 执行
					cnt = preparedStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return cnt;
			}
		}.getResult();
	}

	/**
	 * SQL 查询将查询结果直接放入ResultSet中
	 * 
	 * @param connProvider
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数数组，若没有参数则为null
	 * @return 结果集
	 */
	public static ResultSet executeQueryRS(Connection conn, final String sql,
			final Object[] params) {
		ResultSet resultSet = null;

		try {
			// 调用SQL
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			// 参数赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					preparedStatement.setObject(i + 1, params[i]);
				}
			}

			// 执行
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * 获取结果集，并将结果放在List中
	 * 
	 * @param dataSource
	 * @param sql
	 *            SQL语句
	 * @return List 结果集
	 */
	public static List<Object> executeQuery(ConnectionProvider connProvider,
			final String sql, final Object[] params) {
		return new Executor<List<Object>>(connProvider) {
			@Override
			List<Object> execute(Connection conn) {
				List<Object> list = new ArrayList<Object>();
				try {
					// 执行SQL获得结果集
					ResultSet rs = executeQueryRS(conn, sql, params);

					// 创建ResultSetMetaData对象
					ResultSetMetaData rsmd = rs.getMetaData();

					// 获得结果集列数
					int columnCount = rsmd.getColumnCount();

					// 将ResultSet的结果保存到List中
					while (rs.next()) {
						Map<String, Object> map = new HashMap<String, Object>();
						for (int i = 1; i <= columnCount; i++) {
							map.put(rsmd.getColumnLabel(i), rs.getObject(i));
						}
						list.add(map);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return list;
			}
		}.getResult();
	}

	/**
	 * insert update delete SQL语句的执行的统一方法
	 * 
	 * @param connProvider
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数数组，若没有参数则为null
	 * @return 受影响的行数
	 */
	public static Object executeQuerySingle(ConnectionProvider connProvider,
			final String sql, final Object[] params) {
		return new Executor<Object>(connProvider) {
			@Override
			Object execute(Connection conn) {
				Object object = null;
				try {
					PreparedStatement preparedStatement = conn
							.prepareStatement(sql);

					// 参数赋值
					if (params != null) {
						for (int i = 0; i < params.length; i++) {
							preparedStatement.setObject(i + 1, params[i]);
						}
					}

					// 执行
					ResultSet resultSet = preparedStatement.executeQuery();

					if (resultSet.next()) {
						object = resultSet.getObject(1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return object;
			}
		}.getResult();
	}

	/**
	 * 存储过程带有一个输出参数的方法
	 * 
	 * @param connProvider
	 * @param sql
	 *            存储过程语句
	 * @param params
	 *            参数数组
	 * @param outParamPos
	 *            输出参数位置
	 * @param SqlType
	 *            输出参数类型
	 * @return 输出参数的值
	 */
	public static Object callProcedure(ConnectionProvider connProvider,
			final String sql, final Object[] params, final int outParamPos,
			final int sqlType) {
		return new Executor<Object>(connProvider) {
			@Override
			Object execute(Connection conn) {
				Object obj = null;
				try {
					// 调用存储过程
					CallableStatement callableStatement = conn.prepareCall(sql);

					// 给参数赋值
					if (params != null) {
						for (int i = 0; i < params.length; i++) {
							callableStatement.setObject(i + 1, params[i]);
						}
					}

					// 注册输出参数
					callableStatement
							.registerOutParameter(outParamPos, sqlType);

					// 执行
					callableStatement.execute();

					// 得到输出参数
					obj = callableStatement.getObject(outParamPos);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return obj;
			}
		}.getResult();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		String dataSource = "mysqlSource";
		String sql = "select s.* from tm_stock_code s where s.stock_code in (?)";
		Object[] params = new Object[] { "600886" };

		ConnectionProvider connProvider = DataSourceProvider	.prepare(dataSource);
		Object obj = MysqlClient.executeQuerySingle(connProvider, sql, params);
		System.out.println(obj);

		sql = "update sep_log s set s.user_id = ? where s.log_id = ?";
		params = new Object[] { "1", "5020350" };
		int uptCnt = MysqlClient.executeUpdate(connProvider, sql, params);
		System.out.println(uptCnt);
//
//		sql = "select s.* from sep_log s where s.log_id in (?)";
//		params = new Object[] { "5020350" };

		List<Object> list = MysqlClient
				.executeQuery(connProvider, sql, params);
		for (Object object : list) {
			Map<String, Object> map = (Map<String, Object>) object;
			Iterator iter = map.entrySet().iterator();
			System.out.println("=====================================");
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				System.out.println(key + " -- " + val);
			}
		}

		DataSourceProvider.cleanup(connProvider);
	}
}
