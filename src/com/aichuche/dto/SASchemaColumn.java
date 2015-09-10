package com.aichuche.dto;
///**
// * Copyright (c) 2013, BFAS-BFAD, Bianfeng. All rights reserved.
// */
//package com.bianfeng.bfas.bfrd.dto;
//
//import java.io.Serializable;
//import java.util.Date;
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
//    name = "sa_schema_column"
//)
//@NamedQueries({
//
//    @NamedQuery(name="SASchemaColumnDAO.removeBySchemaId",
//            query="DELETE FROM SASchemaColumn sasc WHERE sasc.schemaId = :schemaId"),
//    @NamedQuery(name="SASchemaColumnDAO.listBySchemaId",
//            query="SELECT sasc FROM SASchemaColumn sasc WHERE sasc.schemaId = :schemaId ORDER BY columnIndex"),
//    @NamedQuery(name="SASchemaColumnDAO.listTagsBySchemaId",
//            query="SELECT sasc.columnName FROM SASchemaColumn sasc WHERE sasc.schemaId = :schemaId AND sasc.columnType = :columnType GROUP BY columnIndex"),
//	@NamedQuery(name = "SASchemaColumnDAO.existsOtherColumns", 
//			query = "SELECT 1 FROM SASchemaColumn sasc WHERE sasc.schemaId = :schemaId AND sasc.id NOT IN (:ids)"),
//	@NamedQuery(name = "SASchemaColumnDAO.getMaxSchemaColumnIndex", 
//			query = "SELECT MAX(columnIndex) FROM SASchemaColumn sasc WHERE sasc.schemaId = :schemaId"),
//})
//public class SASchemaColumn implements Serializable {
//	
//	private static final long serialVersionUID = -7586740685060536332L;
//	
//	public SASchemaColumn() {
//		
//	}
//	
//	@Id
//	@GeneratedValue( strategy = GenerationType.AUTO )
//    @Column( name = "id")
//	private long id;
//	
//	@Column( name = "job_id")
//	private long jobId;
//	
//	@Column( name = "schema_id")
//	private long schemaId;
//	
//	@Column( name = "column_name", length = 64)
//	private String columnName;
//	
//	@Column( name = "column_type")
//	private int columnType;
//	
//	@Column( name = "column_def_type", length = 64)
//	private String columnDefType;
//	
//	// for hbase/hive integration table only.
//	@Column( name = "column_family", length = 64)
//	private String columnFamily;
//	
//	@Column( name = "column_index")
//	private int columnIndex;
//	
//	@Column( name = "date_created")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date dateCreated;
//	
//	@Column( name = "last_modified")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date lastModified;
//	
//	@Column( name = "remark", length = 128)
//	private String remark;
//
//	@Column( name = "json_value", length = 128)
//	private String jsonValue;
//
//	@Column( name = "msgpack_value", length = 128)
//	private String msgpackValue;
//
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
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
//	public long getSchemaId() {
//		return schemaId;
//	}
//
//	public void setSchemaId(long schemaId) {
//		this.schemaId = schemaId;
//	}
//
//	public String getColumnName() {
//		return columnName;
//	}
//
//	public void setColumnName(String columnName) {
//		this.columnName = columnName;
//	}
//
//	public int getColumnType() {
//		return columnType;
//	}
//
//	public void setColumnType(int columnType) {
//		this.columnType = columnType;
//	}
//
//	public String getColumnDefType() {
//		return columnDefType;
//	}
//
//	public void setColumnDefType(String columnDefType) {
//		this.columnDefType = columnDefType;
//	}
//
//	public String getColumnFamily() {
//		return columnFamily;
//	}
//
//	public void setColumnFamily(String columnFamily) {
//		this.columnFamily = columnFamily;
//	}
//
//	public int getColumnIndex() {
//		return columnIndex;
//	}
//
//	public void setColumnIndex(int columnIndex) {
//		this.columnIndex = columnIndex;
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
//	public String getRemark() {
//		return remark;
//	}
//
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
//
//	public String getJsonValue() {
//		return jsonValue;
//	}
//
//	public void setJsonValue(String jsonValue) {
//		this.jsonValue = jsonValue;
//	}
//
//	public String getMsgpackValue() {
//		return msgpackValue;
//	}
//
//	public void setMsgpackValue(String msgpackValue) {
//		this.msgpackValue = msgpackValue;
//	}
//
//	@Override
//	public String toString() {
//		return "SASchemaColumn [id=" + id + ", schemaId=" + schemaId
//				+ ", columnName=" + columnName + ", columnType=" + columnType
//				+ ", columnDefType=" + columnDefType + ", columnFamily="
//				+ columnFamily + ", columnIndex=" + columnIndex
//				+ ", dateCreated=" + dateCreated + ", lastModified="
//				+ lastModified + ", remark=" + remark + "]";
//	}
//}
