package com.aichuche.service;
//package com.bianfeng.bfas.bfrd.service;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bianfeng.bfas.bfrd.dto.SASchema;
//import com.bianfeng.bfas.bfrd.dto.SASchemaColumn;
//
///**
// * 
// * @author ShaoHongLiang
// * @date 2013-8-12
// */
//@Transactional
//public interface SchemaService {
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	int exists(String schemaName);
//
//	int create(SASchema saSchema, List<SASchemaColumn> schemaColumns);
//
//	int delete(long schemaId);
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	List<SASchema> list(Map<String, Object> conditions,
//			Map<String, String> sortBy, int pageIndex, int pageSize);
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	int count(Map<String, Object> conditions);
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	SASchema get(long schemaId, boolean withColumns);
//	
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	List<SASchema> listAll(boolean withColumns);
//	
//	@Transactional(propagation=Propagation.NOT_SUPPORTED)
//	List<SASchema> listAllJobSchemas(boolean withColumns);
//
//	int update(Map<String, Object> paramMap, SASchema saSchema,
//			List<SASchemaColumn> addSchemaColumns,
//			List<SASchemaColumn> modifySchemaColumns,
//			List<Long> removeSchemaColumnIds);
//
//
//}
