package com.aichuche.service;
//package com.bianfeng.bfas.bfrd.service;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bianfeng.bfas.bfrd.dto.LayerIndex;
//import com.bianfeng.bfas.bfrd.dto.SAJob;
//import com.bianfeng.bfas.bfrd.dto.SAJobPreprocessor;
//import com.bianfeng.bfas.bfrd.dto.SASchema;
//import com.bianfeng.bfas.bfrd.dto.SASchemaColumn;
//
//@Transactional
//public interface SAJobService {
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	List<String> listTagsByDataName(String dataName);
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	List<SAJob> listAll();
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	Map<String, List<LayerIndex>> groupByConditionLayer(String requestType);
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	int exists(String jobName);
//
//	int delete(long jobId);
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	List<SAJob> list(Map<String, Object> conditions, Map<String, String> sortBy,
//			int pageIndex, int pageSize);
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	int count(Map<String, Object> conditions);
//
//	int create(SAJob saJob, SASchema sourceSASchema,
//			List<SASchemaColumn> sourceSASchemaColumns,
//			SASchema storageSASchema,
//			List<SASchemaColumn> storageSASchemaColumns,
//			List<SAJobPreprocessor> jobPreprocessors);
//
//	int update(Map<String, Object> paramMap, SAJob saJob,
//			SASchema sourceSASchema,
//			List<SASchemaColumn> sourceAddSASchemaColumns,
//			List<SASchemaColumn> sourceModifySASchemaColumns,
//			List<Long> sourceRemoveSASchemaColumns, SASchema storageSASchema,
//			List<SASchemaColumn> storageAddSASchemaColumns,
//			List<SASchemaColumn> storageModifySASchemaColumns,
//			List<Long> storageRemoveSASchemaColumns,
//			List<SAJobPreprocessor> addJobPreprocessors,
//			List<SAJobPreprocessor> modifyJobPreprocessors,
//			List<Long> removePreprocessors);
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	List<SASchema> listSASchemasById(long jobId);
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	SAJob get(long jobId);
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	List<SAJobPreprocessor> listJobPreprocessorsByJobId(long jobId);
//
//}
