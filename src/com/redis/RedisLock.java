package com.redis;

import java.util.Random;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisLock {

	private ShardedJedis shardedJedis;// 切片额客户端连接

	private ShardedJedisPool shardedJedisPool;// 切片连接池

	/** 加锁标志 */
	public final String LOCKED = "TRUE";

	/** 毫秒与毫微秒的换算单位 1毫秒 = 1000000毫微秒 */
	public final long MILLI_NANO_CONVERSION = 1000 * 1000L;

	/** 默认超时时间（毫秒） */
	public final long DEFAULT_TIME_OUT = 1000;

	public final Random RANDOM = new Random();

	/** 锁的超时时间（秒），过期删除 */
	public final int EXPIRE = 3 * 60;

	private String key;

	/** 锁状态标志 */
	private boolean locked = false;

	public RedisLock(String key, ShardedJedisPool pool) {
		this.key = key + "_lock";
		this.shardedJedisPool = pool;
		this.shardedJedis = shardedJedisPool.getResource();
	}

	/**
	 * 加锁 应该以： lock(); try { doSomething(); } finally { unlock()； } 的方式调用
	 * 
	 * @param timeout
	 *            超时时间
	 * @return 成功或失败标志
	 */
	public boolean lock(long timeout) {
		long nano = System.nanoTime();
		timeout *= this.MILLI_NANO_CONVERSION;
		try {
			while ((System.nanoTime() - nano) < timeout) {
				if (this.shardedJedis.setnx(this.key, this.LOCKED) == 1) {
					this.shardedJedis.expire(this.key, this.EXPIRE);
					this.locked = true;
					return this.locked;
				}
				// 短暂休眠，避免出现活锁
				Thread.sleep(3, RANDOM.nextInt(500));
			}
		} catch (Exception e) {
			throw new RuntimeException("Locking error", e);
		}
		return false;
	}

	/**
	 * 加锁 应该以： lock(); try { doSomething(); } finally { unlock()； } 的方式调用
	 * 
	 * @param timeout
	 *            超时时间
	 * @param expire
	 *            锁的超时时间（秒），过期删除
	 * @return 成功或失败标志
	 */
	public boolean lock(long timeout, int expire) {
		long nano = System.nanoTime();
		timeout *= this.MILLI_NANO_CONVERSION;
		try {
			while ((System.nanoTime() - nano) < timeout) {
				if (this.shardedJedis.setnx(this.key, this.LOCKED) == 1) {
					this.shardedJedis.expire(this.key, expire);
					this.locked = true;
					return this.locked;
				}
				// 短暂休眠，避免出现活锁
				Thread.sleep(3, this.RANDOM.nextInt(500));
			}
		} catch (Exception e) {
			throw new RuntimeException("Locking error", e);
		}
		return false;
	}

	/**
	 * 加锁 应该以： lock(); try { doSomething(); } finally { unlock()； } 的方式调用
	 * 
	 * @return 成功或失败标志
	 */
	public boolean lock() {
		return lock(this.DEFAULT_TIME_OUT);
	}

	/**
	 * 解锁 无论是否加锁成功，都需要调用unlock 应该以： lock(); try { doSomething(); } finally {
	 * unlock()； } 的方式调用
	 */
	@SuppressWarnings("deprecation")
	public void unlock() {
		try {
			if (this.locked) {
				this.shardedJedis.del(this.key);
			}
		} finally {
			this.shardedJedisPool.returnResource(this.shardedJedis);
		}
	}
}
