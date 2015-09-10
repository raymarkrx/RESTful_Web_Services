package com.aichuche.util;
///**
// * Copyright (c) 2013, BFAS-BFRD, Bianfeng. All rights reserved.
// */
//package com.bianfeng.bfas.bfrd.util;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.bianfeng.base.util.BaseConfiguration;
//
///**
// * 
// * @author Peyton Peng
// * @date 2012-3-22
// *
// * Providing all configuration items for BFRD Service.
// */
//public final class ServerConfiguration extends BaseConfiguration{
//	
//	private static int logMemCacheMaxSize;
//	private static int logMemCacheQueueSize;
//	private static int logStoreMaxFailTimes;
//	
//	private static String serverInstanceId = "BFRD.singleton";
//	
//	private static String hadoopStoragePath;
//	private static String packageStoragePath;
//	private static String repairStoragePath;
//
//	private static String tsdbHost;
//	private static int tsdbPort;
//	private static int tsdbMaxClients;
//	private static int tsdbMaxSingleClientQueueSize;
//	private static String bfdcHost;
//	private static int bfdcPort;
//	private static int bfdcConnPoolSize;
//	private static String activeMQURL;
//	private static String kafkaURL;
//	private static String hiveHost;
//	private static int hivePort;
//	
//	private static String redisHost;
//	private static int redisPort;
//	private static int redisTimeout;
//	private static String redisPass;
//
//	private static String zookeeperURL;
//	private static String esURL;
//	private static String hbaseZookeeperQuorum;
//	private static String clusterName;
//
//	private static boolean jpaCacheEnabled;
//
//	private static int useHiveCheckDays;
//
//	private static final ServerConfiguration sc = new ServerConfiguration(); 
//	
//	private static Map<String, Boolean> activeMQEnableOptionMap = new HashMap<String, Boolean>();
//	
//	private ServerConfiguration(){
//		//empty
//	}
//
//	public static ServerConfiguration getInstance() {
//		return sc;
//	}
//
//	public static boolean isJpaCacheEnabled() {
//		return jpaCacheEnabled;
//	}
//
//	public static void setJpaCacheEnabled(boolean jpaCacheEnabled) {
//		ServerConfiguration.jpaCacheEnabled = jpaCacheEnabled;
//	}
//
//	public static String getBfdcHost() {
//		return bfdcHost;
//	}
//
//	public static void setBfdcHost(String bfdcHost) {
//		ServerConfiguration.bfdcHost = bfdcHost;
//	}
//
//	public static int getBfdcPort() {
//		return bfdcPort;
//	}
//
//	public static void setBfdcPort(int bfdcPort) {
//		ServerConfiguration.bfdcPort = bfdcPort;
//	}
//	
//	public static int getLogMemCacheMaxSize() {
//		return logMemCacheMaxSize;
//	}
//
//	public static void setLogMemCacheMaxSize(int logMemCacheMaxSize) {
//		ServerConfiguration.logMemCacheMaxSize = logMemCacheMaxSize;
//	}
//
//	public static int getLogMemCacheQueueSize() {
//		return logMemCacheQueueSize;
//	}
//
//	public static void setLogMemCacheQueueSize(int logMemCacheQueueSize) {
//		ServerConfiguration.logMemCacheQueueSize = logMemCacheQueueSize;
//	}
//
//	public static int getLogStoreMaxFailTimes() {
//		return logStoreMaxFailTimes;
//	}
//
//	public static void setLogStoreMaxFailTimes(int logStoreMaxFailTimes) {
//		ServerConfiguration.logStoreMaxFailTimes = logStoreMaxFailTimes;
//	}
//	
//	public static String getServerInstanceId() {
//		return serverInstanceId;
//	}
//
//	public static void setServerInstanceId(String serverInstanceId) {
//		ServerConfiguration.serverInstanceId = serverInstanceId;
//	}
//	
//	public static String getHadoopStoragePath() {
//		return hadoopStoragePath;
//	}
//
//	public static void setHadoopStoragePath(String hadoopStoragePath) {
//		ServerConfiguration.hadoopStoragePath = hadoopStoragePath;
//	}
//
//	public static String getPackageStoragePath() {
//		return packageStoragePath;
//	}
//
//	public static void setPackageStoragePath(String packageStoragePath) {
//		ServerConfiguration.packageStoragePath = packageStoragePath;
//	}
//
//	public static String getRepairStoragePath() {
//		return repairStoragePath;
//	}
//
//	public static void setRepairStoragePath(String repairStoragePath) {
//		ServerConfiguration.repairStoragePath = repairStoragePath;
//	}
//
//	public static String getTsdbHost() {
//		return tsdbHost;
//	}
//
//	public static void setTsdbHost(String tsdbHost) {
//		ServerConfiguration.tsdbHost = tsdbHost;
//	}
//
//	public static int getTsdbPort() {
//		return tsdbPort;
//	}
//
//	public static void setTsdbPort(int tsdbPort) {
//		ServerConfiguration.tsdbPort = tsdbPort;
//	}
//
//	public static int getTsdbMaxClients() {
//		return tsdbMaxClients;
//	}
//
//	public static void setTsdbMaxClients(int tsdbMaxClients) {
//		ServerConfiguration.tsdbMaxClients = tsdbMaxClients;
//	}
//
//	public static int getTsdbMaxSingleClientQueueSize() {
//		return tsdbMaxSingleClientQueueSize;
//	}
//
//	public static void setTsdbMaxSingleClientQueueSize(
//			int tsdbMaxSingleClientQueueSize) {
//		ServerConfiguration.tsdbMaxSingleClientQueueSize = tsdbMaxSingleClientQueueSize;
//	}
//	
//	public static int getBfdcConnPoolSize() {
//		return bfdcConnPoolSize;
//	}
//
//	public static void setBfdcConnPoolSize(int bfdcConnPoolSize) {
//		ServerConfiguration.bfdcConnPoolSize = bfdcConnPoolSize;
//	}
//
//	
//	public static void setActiveMQURL(String activeMQURL) {
//		ServerConfiguration.activeMQURL = activeMQURL;
//	}
//
//	public static String getActiveMQURL() {
//		return ServerConfiguration.activeMQURL;
//	}
//	
//	public static String getKafkaURL() {
//		return kafkaURL;
//	}
//
//	public static void setKafkaURL(String kafkaURL) {
//		ServerConfiguration.kafkaURL = kafkaURL;
//	}
//
//	public static String getZookeeperURL() {
//		return zookeeperURL;
//	}
//
//	public static void setZookeeperURL(String zookeeperURL) {
//		ServerConfiguration.zookeeperURL = zookeeperURL;
//	}
//
//	public static String getEsURL() {
//		return esURL;
//	}
//
//	public static void setEsURL(String esURL) {
//		ServerConfiguration.esURL = esURL;
//	}
//
//	public static String getHbaseZookeeperQuorum() {
//		return hbaseZookeeperQuorum;
//	}
//
//	public static void setHbaseZookeeperQuorum(String hbaseZookeeperQuorum) {
//		ServerConfiguration.hbaseZookeeperQuorum = hbaseZookeeperQuorum;
//	}
//
//	public static boolean enableProducer(String queueName) {
//		String key = "activatemq_" + queueName + "_producer_enable";
//		return getActiveMQOptionEnable(key);
//	}
//
//	public static boolean enableConsumer(String queueName) {
//		String key = "activatemq_" + queueName + "_consumer_enable";
//		return getActiveMQOptionEnable(key);
//	}
//
//	private static boolean getActiveMQOptionEnable(String key) {
//		if(activeMQEnableOptionMap.containsKey(key)){
//			return activeMQEnableOptionMap.get(key);
//		}else{
//			Boolean enabled = Boolean.valueOf(prop.getProperty(key, "true"));
//			activeMQEnableOptionMap.put(key, enabled);
//			return enabled;
//		}
//	}
//
//	public static int getUseHiveCheckDays() {
//		return useHiveCheckDays;
//	}
//
//	public static void setUseHiveCheckDays(int useHiveCheckDays) {
//		ServerConfiguration.useHiveCheckDays = useHiveCheckDays;
//	}
//
//
//	public static int getHivePort() {
//		return hivePort;
//	}
//
//	public static void setHivePort(int hivePort) {
//		ServerConfiguration.hivePort = hivePort;
//	}
//
//	public static void setHiveHost(String hiveHost) {
//		ServerConfiguration.hiveHost = hiveHost;
//	}
//
//	public static String getHiveHost() {
//		return hiveHost;
//	}
//
//	public static String getRedisHost() {
//		return redisHost;
//	}
//
//	public static void setRedisHost(String redisHost) {
//		ServerConfiguration.redisHost = redisHost;
//	}
//
//	public static int getRedisPort() {
//		return redisPort;
//	}
//
//	public static void setRedisPort(int redisPort) {
//		ServerConfiguration.redisPort = redisPort;
//	}
//
//	public static int getRedisTimeout() {
//		return redisTimeout;
//	}
//
//	public static void setRedisTimeout(int redisTimeout) {
//		ServerConfiguration.redisTimeout = redisTimeout;
//	}
//
//	public static String getRedisPass() {
//		return redisPass;
//	}
//
//	public static void setRedisPass(String redisPass) {
//		ServerConfiguration.redisPass = redisPass;
//	}
//
//	public static String getClusterName() {
//		return clusterName;
//	}
//
//	public static void setClusterName(String clusterName) {
//		ServerConfiguration.clusterName = clusterName;
//	}
//
//	static {
//		logMemCacheMaxSize   = Integer.parseInt(prop.getProperty(UtilData.Configuration.K_LOG_MEM_CACHE_MAX_SIZE));
//		logMemCacheQueueSize = Integer.parseInt(prop.getProperty(UtilData.Configuration.K_LOG_MEM_CACHE_QUEUE_SIZE));
//		logStoreMaxFailTimes = Integer.parseInt(prop.getProperty(UtilData.Configuration.K_LOG_STORE_MAX_FAIL_TIMES));
//		
//		tsdbHost 						= prop.getProperty(UtilData.Configuration.K_TSDB_HOST);
//		tsdbPort   						= Integer.parseInt(prop.getProperty(UtilData.Configuration.K_TSDB_PORT));
//		tsdbMaxClients 					= Integer.parseInt(prop.getProperty(UtilData.Configuration.K_TSDB_MAX_CLIENTS));
//		tsdbMaxSingleClientQueueSize 	= Integer.parseInt(prop.getProperty(UtilData.Configuration.K_TSDB_MAX_SINGLE_CLIENT_QUEUE_SIZE));
//		
//		serverInstanceId	= prop.getProperty(UtilData.Configuration.K_SERVER_INSTANCE_ID);
//		hadoopStoragePath   = prop.getProperty(UtilData.Configuration.K_HADOOP_STORAGE_PATH);
//		packageStoragePath   = prop.getProperty(UtilData.Configuration.K_PACKAGE_STORAGE_PATH);
//		repairStoragePath   = prop.getProperty(UtilData.Configuration.K_REPAIR_STORAGE_PATH);
//		
//		bfdcHost				= prop.getProperty(UtilData.Configuration.K_BFDC_HOST);
//		bfdcPort				= Integer.parseInt(prop.getProperty(UtilData.Configuration.K_BFDC_PORT));
//		bfdcConnPoolSize		= Integer.parseInt(prop.getProperty(UtilData.Configuration.K_BFDC_CONN_POOL_SIZE));
//		
//		jpaCacheEnabled			= Boolean.valueOf(prop.getProperty(UtilData.Configuration.K_JPA_CACHE_ENABLED));
//		
//		activeMQURL = prop.getProperty(UtilData.Configuration.K_ACTIVATEMQ_URL);
//		kafkaURL = prop.getProperty(UtilData.Configuration.K_KAFKA_URL);
//		zookeeperURL = prop.getProperty(UtilData.Configuration.K_ZOOKEEPER_URL);
//		esURL = prop.getProperty(UtilData.Configuration.K_ES_URL);
//
//		useHiveCheckDays		= Integer.valueOf(prop.getProperty(UtilData.Configuration.K_USE_HIVE_CHECK_DAYS, "0"));
//		hiveHost				= prop.getProperty(UtilData.Configuration.K_HIVE_HOST);
//		hivePort				= Integer.valueOf(prop.getProperty(UtilData.Configuration.K_HIVE_PORT, "0"));
//		
//		redisHost				= prop.getProperty(UtilData.Configuration.K_REDIS_HOST);
//		redisPort				= Integer.parseInt(prop.getProperty(UtilData.Configuration.K_REDIS_PORT, "0"));
//		redisTimeout			= Integer.parseInt(prop.getProperty(UtilData.Configuration.K_REDIS_TIMEOUT, "0"));
//		redisPass				= prop.getProperty(UtilData.Configuration.K_REDIS_PASS);
//		
//		hbaseZookeeperQuorum	= prop.getProperty(UtilData.Configuration.K_HBASE_ZOOKEEPER_QUORUM);
//		clusterName	= prop.getProperty(UtilData.Configuration.K_CLUSTER_NAME, "bfrd");
//	}
//
//
//
//}
