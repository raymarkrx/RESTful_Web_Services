/**
 * Copyright (c) 2013, BFAS-BFAD, Bianfeng. All rights reserved.
 */
package com.aichuche.util;

/**
 * 
 * @author Peyton Peng
 * @date 2013-5-22
 *
 * Utility data list for the whole system.
 *
 */
public interface UtilData {

	interface Value {

		int DEFAULT_PAGE_INDEX		= 0;
		int DEFAULT_PAGE_SIZE		= 20;
		int MAX_PAGE_SIZE 			= 100;
		
		String HADOOP_SEPARATOR = "\t";
		
		String UNIX_LINEBREAK = "\n";
		
		String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
		
		String DATE_FORMAT_DAY_SHORT = "yyyyMMdd";

		String DATE_FORMAT_STORAGE_DAY = "yyyy/yyyyMMdd/";
		
		String DATE_FORMAT_STORAGE_HOUR = "yyyy/yyyyMMdd/HH/";
		
		String DATE_FORMAT_STORAGE_SIMPLE_DAY = "yyyyMMdd/";
	}
	
	interface Request{
		public interface Type{
			String JSON = "1";
			String MSG_PACK = "2";
			String SYSLOG = "3";
			String ERROR = "4";
		}
	}

	interface ErrorCode {
		
		int NO_ERROR				= 0;   // request no error
		int INVALID_REQUEST			= 400; // request is invalid, e.g. parameter error
		int NO_PRIVILEGE          	= 403; // the request is require login or no privilege to access the api
		int RESOURCE_NOT_EXIST    	= 404; // resource not exist
		int NOT_ACCEPTABLE        	= 406; // the method is not able to access
		int SYSTEM_INTERNAL_ERROR 	= 500; // system internal error
		
		int INVALID_USERNAME    	= 1001; // username does not exist.
		int PASSWORD_ERROR        	= 1002; // password does not match the specified username
		int USER_BANNED        		= 1003; // user is banned
		
		int DATA_EXISTS  			= 2001; // the new detecting data exists in system

		int DEVICE_EXISTS  			= DATA_EXISTS; // the new detecting data exists in system
	}
	
	interface Service {
		String EVENT_PACKAGE_SERVICE_BEAN_NAME 	= "eventPackageService";
		
		String LOG_STORE_SERVICE_BEAN_NAME 		= "logStoreService";

		String SCHEMA_SERVICE_BEAN_NAME 		= "schemaService";
		
		String DEVICE_SERVICE_BEAN_NAME 		= "deviceService";

		String ES_DAO_BEAN_NAME 				= "esDao";
	}
	
	interface Configuration {

		String K_LOG_MEM_CACHE_MAX_SIZE   = "log_mem_cache_max_size";

		String K_LOG_MEM_CACHE_QUEUE_SIZE = "log_mem_cache_queue_size";

		String K_LOG_STORE_MAX_FAIL_TIMES = "log_store_max_fail_times";
		
		String K_SERVER_INSTANCE_ID = "server_instance_id";

		String K_HADOOP_STORAGE_PATH = "hadoop_storage_path";

		String K_PACKAGE_STORAGE_PATH = "package_storage_path";
		
		String K_REPAIR_STORAGE_PATH = "repair_storage_path";

		//tsdb
		String K_TSDB_HOST 					= "tsdb_host";

		String K_TSDB_PORT 					= "tsdb_port";

		String K_TSDB_MAX_CLIENTS 			= "tsdb_max_clients";

		String K_TSDB_MAX_SINGLE_CLIENT_QUEUE_SIZE = "tsdb_max_single_client_queue_size";
		
		//bfdc
		String K_BFDC_HOST 					= "bfdc_host";

		String K_BFDC_PORT 					= "bfdc_port";
		
		String K_BFDC_CONN_POOL_SIZE 		= "bfdc_conn_pool_size";

		String K_ACTIVATEMQ_URL 			= "activatemq_url";
		String K_KAFKA_URL 					= "kafka_url";
		String K_ZOOKEEPER_URL 				= "zookeeper_url";
		String K_ES_URL 					= "es_url";

		String K_JPA_CACHE_ENABLED 			= "jpa_cache_enabled";
		String K_USE_HIVE_CHECK_DAYS 		= "use_hive_check_days";
		String K_HIVE_HOST 					= "hive_host";
		String K_HIVE_PORT 					= "hive_port";
		

		String K_REDIS_HOST				= "device_redis_host";
		String K_REDIS_PORT				= "device_redis_port";
		String K_REDIS_TIMEOUT			= "device_redis_timeout";
		String K_REDIS_PASS				= "device_redis_pass";

		String K_HBASE_ZOOKEEPER_QUORUM = "hbase_zookeeper_quorum";
		String K_CLUSTER_NAME			= "cluster_name";
		
	}
	
	interface SARole {
		int NO_ROLE					= 0;
		int ROLE_USER				= 1;
		int ROLE_ADMIN				= 100;
		
		int ROLE_USER_ID			= 2;
		int ROLE_ADMIN_ID			= 1;
	}

	interface SASchema {
		
		interface StorageCycle {
			int DAY					= 1;
			int HOUR				= 2;
		}
		

		interface Type {
			int RC_STORE				= 1;
			int HBASE_HIVE_STORE		= 2;
			int RDB_MYSQL_STORE			= 3;
			int RDB_MSSQL_STORE			= 4;
			int RDB_ORACLE_STORE		= 5;
			int FILE					= 6;
			
			int BFRD_HDFS				= 101;
			int BFRD_REALTIME			= 102;
		}
		
		interface RelationType {
			int SOURCE					= 1;
			int STORAGE					= 2;
			int PREP_STORAGE			= 3;
		}
		
		
		interface Status {
			int INITIAL					= 1;
			int REQUEST_UPDATE			= 2;
			int CREATED					= 3;
		}
		
	}

	
	interface SASchemaColumn {
		interface Type {
			int	NORMAL					= 0;
			int HBASE_ROWKEY			= 1;
			int HBASE_TIMESTAMP 		= 2;

			int OPENTSDB_TS		 		= 3;
			int OPENTSDB_KEY_TAG 		= 4;
			int OPENTSDB_TAG 			= 5;
			int OPENTSDB_VALUE			= 7;
			int OPENTSDB_RECEIVETIME	= 8;
			
			
			int SKIP			 		= 9;
		}
	}
	
	interface SAJob {
		interface Type {
			int ETL						= 0;
			int CALCULATE				= 1;
			int EXPORT					= 2;
			int REALTIME				= 3;
		}
	}
	
	interface Message{
		interface Type {
			int INIT			= 1;
			int SESSION			= 2;
			int EXCEPTION		= 3;
		}
		
	}

	interface LogData {
		interface MsgPack {
			String K_MESSAGES			= "messages";
			String K_SESSION			= "session";
			String K_EVENTS				= "appEvents";
			String K_ACTIVATIES			= "activities";
		}
	}
	
	
	
}