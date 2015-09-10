package com.aichuche.dto;
///**
// * Copyright (c) 2013, BFAS-BFAD, Bianfeng. All rights reserved.
// */
//package com.bianfeng.bfas.bfrd.dto;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.persistence.Transient;
//
///**
// * @author Peng Peng
// * @date 2013-6-13
// *
// *
// */
//@Entity
//
//@Table( 
//    name = "sa_schema"
//)
//@NamedQueries({
//
//    @NamedQuery(name="SASchemaDAO.listByJobId",
//            query="SELECT sas FROM SASchema sas WHERE sas.jobId = :jobId"),
//    @NamedQuery(name="SASchemaDAO.existsSchemaName",
//            query="SELECT 1 FROM SASchema sas WHERE sas.schemaName = :schemaName AND schemaType = 101"),
//    @NamedQuery(name="SASchemaDAO.listSchemaByType",
//            query="SELECT sas FROM SASchema sas WHERE sas.schemaType = :schemaType AND relation_type = 1"),
//    @NamedQuery(name="SASchemaDAO.listByJobIdAndType",
//            query="SELECT sas FROM SASchema sas WHERE sas.jobId = :jobId AND relationType = :relationType"),
//})
//public class SASchema implements Serializable {
//	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -1633648294073063714L;
//
//	public SASchema() {
//		
//	}
//	
//	@Id
//	@GeneratedValue( strategy = GenerationType.AUTO )
//    @Column( name = "id")
//	private long id;
//	
//	@Column( name = "schema_name", length = 64)
//	private String schemaName;
//	
//	/**
//	 * refer to interface def: UtilData.SASchema.Type
//	 */
//	@Column( name = "schema_type")
//	private int schemaType;
//	
//	/**
//	 * refer to interface def: UtilData.SASchema.RelationType
//	 */
//	@Column( name = "relation_type")
//	private int relationType;
//	
//	/**
//	 * schema job owner, the schema must to related with SAJob
//	 */
//	@Column( name = "job_id")
//	private long jobId;
//	
//	/**
//	 * schema preprocess owner, while any preprocess result need to be persistent, can choose the schema and the schema will be created. 
//	 */
//	@Column( name = "preprocess_id")
//	private long preprocessId;
//	
//	@Column( name = "date_created")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date dateCreated;
//	
//	@Column( name = "last_modified")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date lastModified;
//	
//	@Column( name = "last_status_modified")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date lastStatusModified;
//	
//	@Column( name = "schema_status")
//	private int status;
//	
//	@Column( name = "remark", length = 128)
//	private String remark;
//
//	@Column( name = "json_condition_expression", length = 128)
//	private String jsonConditionExpression;
//	
//	@Column( name = "msgpack_condition_expression", length = 128)
//	private String msgpackConditionExpression;
//	
//	@Transient
//	private List<SASchemaColumn> schemaColumns;
//
//	public List<SASchemaColumn> getSchemaColumns() {
//		return schemaColumns;
//	}
//
//	public void setSchemaColumns(List<SASchemaColumn> schemaColumns) {
//		this.schemaColumns = schemaColumns;
//	}
//
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	public String getSchemaName() {
//		return schemaName;
//	}
//
//	public void setSchemaName(String schemaName) {
//		this.schemaName = schemaName;
//	}
//
//	public int getSchemaType() {
//		return schemaType;
//	}
//
//	public void setSchemaType(int schemaType) {
//		this.schemaType = schemaType;
//	}
//
//	public int getRelationType() {
//		return relationType;
//	}
//
//	public void setRelationType(int relationType) {
//		this.relationType = relationType;
//	}
//
//	public long getJobId() {
//		return jobId;
//	}
//
//	public void setJobId(long jobId) {
//		this.jobId = jobId;
//	}
//
//	public long getPreprocessId() {
//		return preprocessId;
//	}
//
//	public void setPreprocessId(long preprocessId) {
//		this.preprocessId = preprocessId;
//	}
//
//	public Date getDateCreated() {
//		return dateCreated;
//	}
//
//	public void setDateCreated(Date dateCreated) {
//		this.dateCreated = dateCreated;
//	}
//
//	public Date getLastModified() {
//		return lastModified;
//	}
//
//	public void setLastModified(Date lastModified) {
//		this.lastModified = lastModified;
//	}
//
//	public Date getLastStatusModified() {
//		return lastStatusModified;
//	}
//
//	public void setLastStatusModified(Date lastStatusModified) {
//		this.lastStatusModified = lastStatusModified;
//	}
//
//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}
//
//	public String getRemark() {
//		return remark;
//	}
//
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
//	
//	public String getJsonConditionExpression() {
//		return jsonConditionExpression;
//	}
//
//	public void setJsonConditionExpression(String jsonConditionExpression) {
//		this.jsonConditionExpression = jsonConditionExpression;
//	}
//
//	public String getMsgpackConditionExpression() {
//		return msgpackConditionExpression;
//	}
//
//	public void setMsgpackConditionExpression(String msgpackConditionExpression) {
//		this.msgpackConditionExpression = msgpackConditionExpression;
//	}
//
//	@Override
//	public String toString() {
//		return "SASchema [id=" + id + ", schemaName=" + schemaName
//				+ ", schemaType=" + schemaType + ", relationType="
//				+ relationType + ", jobId=" + jobId + ", preprocessId="
//				+ preprocessId + ", dateCreated=" + dateCreated
//				+ ", lastModified=" + lastModified + ", status=" + status
//				+ ", remark=" + remark + "]";
//	}
//}
