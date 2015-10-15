package com.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.chh.utils.PropertiesUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisClient {

	private static Logger log= LoggerFactory.getLogger("Data101ToMysql");  
	private static JedisPool jedisPool;// 非切片连接池
	private static ShardedJedisPool shardedJedisPool;// 切片连接池
	private static String host;
	private static int port;

	static {
		host=PropertiesUtils.getValue("redisHost");
		port=Integer.parseInt(PropertiesUtils.getValue("redisPort"));
		initialPool();
		initialShardedPool();
	}

	public RedisClient() {

	}

	/**
	 * 初始化非切片池
	 */
	private static void initialPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(20);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000l);
		config.setTestOnBorrow(true);

		jedisPool = new JedisPool(config, host, port);
	}

	/**
	 * 初始化切片池
	 */
	private static void initialShardedPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(100);
		config.setMaxIdle(10);
		config.setMaxWaitMillis(2000l);
		config.setTestOnBorrow(true);
		// slave链接
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		
		//old
		//shards.add(new JedisShardInfo("210.51.31.67", 6379, "master"));
		
		//new--chh
        JedisShardInfo jedisShardInfo = new JedisShardInfo(host, port, "master");
        jedisShardInfo.setSoTimeout(1000000);  
        
        shards.add(jedisShardInfo); 

		// 构造池
		shardedJedisPool = new ShardedJedisPool(config, shards);
	}

	/**
	 * 执行器，{@link com.futurefleet.framework.base.redis.RedisUtil}的辅助类，
	 * 它保证在执行操作之后释放数据源returnResource(jedis)
	 * 
	 * @version V1.0
	 * @author fengzhi
	 * @param <T>
	 */
	abstract static class Executor<T> {

		Jedis jedis;// 非切片额客户端连接
		JedisPool jedisPool;// 非切片连接池
		ShardedJedis shardedJedis;
		ShardedJedisPool shardedJedisPool;

		public Executor(ShardedJedisPool shardedJedisPool) {
			this.shardedJedisPool = shardedJedisPool;
			shardedJedis = this.shardedJedisPool.getResource();
		}

		public Executor(JedisPool jedisPool) {
			this.jedisPool = jedisPool;
			jedis = this.jedisPool.getResource();
		}

		/**
		 * 回调
		 * 
		 * @return 执行结果
		 */
		abstract T execute();

		/**
		 * 调用{@link #execute()}并返回执行结果 它保证在执行{@link #execute()}
		 * 之后释放数据源returnResource(jedis)
		 * 
		 * @return 执行结果
		 */
		@SuppressWarnings("deprecation")
		public T getResult() {
			T result = null;
			try {
				result = execute();
			} catch (Throwable e) {
				throw new RuntimeException("Redis execute exception", e);
			} finally {
				if (shardedJedis != null) {
					shardedJedisPool.returnResource(shardedJedis);
				}
			}
			return result;
		}
	}

	
	/**
	 * 判断是否是怠速起步开始
	 * 
	 * @param key
	 * @param speed
	 * @param dateTime
	 * @return
	 */
	public static Map<String, String> isStartBegin(final String key,
			final String speed, final String dateTime) {
		return new Executor<Map<String, String>>(shardedJedisPool) {
			@Override
			Map<String, String> execute() {
				RedisLock lock = new RedisLock(key, shardedJedisPool);
				String beginDateTime = null;
				Map<String, String> rtnMap = null;

				try {
					lock.lock();
					String value = speed + "," + dateTime;
					shardedJedis.lpush(key, value);
					//System.out.println("### 判断怠速起步开始,新增缓存元素：key[" + key+ "], value[" + value + "]  ###");

					if (shardedJedis.llen(key) >= 5) {
						shardedJedis.ltrim(key, 0, 4);

						//System.out.println("### 判断怠速起步开始:缓存大小["+ shardedJedis.llen(key) + "] ,key[" + key	+ "], values" + shardedJedis.lrange(key, 0, -1)+ " ###");

						List<String> values = shardedJedis.lrange(key, 0, -1);
						String[] value1 = values.get(0).split(",");
						String[] value2 = values.get(1).split(",");
						String[] value3 = values.get(2).split(",");
						String[] value4 = values.get(3).split(",");
						String[] value5 = values.get(4).split(",");
						if (Double.parseDouble(value1[0]) > 0
								&& Double.parseDouble(value2[0]) > 0
								&& Double.parseDouble(value3[0]) > 0
								&& Double.parseDouble(value4[0]) == 0
								&& Double.parseDouble(value5[0]) == 0){
							if (value3.length > 1) {
								beginDateTime = value3[1];
							} else if (value4.length > 1) {
								beginDateTime = value4[1];
							}

							rtnMap = new HashMap<String, String>();

							rtnMap.put(Constants.START_BEGIN_SUCCESS_KEY,
									Constants.SUCCESS);
							rtnMap.put(Constants.DATA_TIME, beginDateTime);

							shardedJedis.ltrim(key, 0, 1);
							//System.out.println("### 起步开始之后的缓存大小：["	+ shardedJedis.llen(key) + "] ,key[" + key	+ "], values"+ shardedJedis.lrange(key, 0, -1) + " ###");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
				return rtnMap;
			}
		}.getResult();
	}
	
	/**根据key，得到对应的value,value是list类型
 * @param key
 * @param speed
 * @return
 */
	public static  Map<String, Object>  getListValue(final String key) {
		return new Executor<Map<String, Object>>(shardedJedisPool) {
			@Override
			Map<String, Object> execute() {
				String newKey=key;
				RedisLock lock = new RedisLock(newKey, shardedJedisPool);
				Map<String, Object> rtnMap = null;
				try {
					lock.lock();
					List<String> values = shardedJedis.lrange(newKey, 0, -1);//倒序的speed数组，只放s5,s4,s3这3个速度 (s5是最新速度，s3是最老速度)
					String s5 = values.get(0);
					String s4 = values.get(1);
					String s3 = values.get(2);
					
					System.out.println(s5+","+s4+","+s3);
					
					rtnMap = new HashMap<String, Object>();
					rtnMap.put(Constants.RESULT,values);
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
				return rtnMap;
			}
		}.getResult();
	}
	
	//判断data101是否有序
	public static  Map<String, Object>  testOrder(final String key,final String suffix,final String unixtimestamp ) {
		return new Executor<Map<String, Object>>(shardedJedisPool) {
			@Override
			Map<String, Object> execute() {
				String testKey=key+suffix;//组成新的key
				RedisLock lock = new RedisLock(testKey, shardedJedisPool);
				Map<String, Object> rtnMap =new HashMap<String, Object>();
				try {
					lock.lock();
					//压入队列
					shardedJedis.lpush(testKey,  unixtimestamp);
					if (shardedJedis.llen(testKey) >= 20) {
						shardedJedis.ltrim(testKey, 0, 19);
					}
					rtnMap.put(key, shardedJedis.lrange(testKey, 0, -1));

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
				return rtnMap;
			}
		}.getResult();
	}
	
	
	

	/**
	 * 更新车辆行驶状态
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String updateDriverStatus(final String key, final String value) {
		return new Executor<String>(shardedJedisPool) {
			@Override
			String execute() {
				RedisLock lock = new RedisLock(key + Constants.KEY_DRIVER_STATUS, shardedJedisPool);
				Long rtn = -1l;
				try {
					lock.lock();
					rtn = shardedJedis.setnx(key + Constants.KEY_DRIVER_STATUS, value);//将 key 的值设为 value ，当且仅当 key 不存在。
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
				return Long.toString(rtn);
			}
		}.getResult();
	}

	/**
	 * 得到车辆行驶状态
	 * 
	 * @param key
	 * @return
	 */
	public static String getDriverStatus(final String key) {
		return new Executor<String>(shardedJedisPool) {
			@Override
			String execute() {
				RedisLock lock = new RedisLock(key + Constants.KEY_DRIVER_STATUS, shardedJedisPool);
				String status = null;

				try {
					lock.lock();
					status = shardedJedis.get(key + Constants.KEY_DRIVER_STATUS);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
				return status;
			}
		}.getResult();
	}

	public static void main(String[] args) throws Exception {
		String key = "+8618616969935";
		String[] axs = new String[] { "0", "0", "0", "1", "2", "3" };
		String[] speeds = new String[] { "0", "0", "0", "10", "20", "30" };
		for (String ax : axs) {
			// Map<String, String> map = RedisClient.isStartBegin(key,
			// String.valueOf(speed), "20150818");
			// if (map != null) {
			// System.out.println("map.size-------------" + map.size());
			// System.out.println("isStartBegin-------------"
			// + map.get(Constants.START_BEGIN_SUCCESS_KEY));
			// System.out.println("StartBeginDatetime-------------"
			// + map.get(Constants.DATA_TIME));
			// }

			
			
			Thread.sleep(1000);
		}
	}
}