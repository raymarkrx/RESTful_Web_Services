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
// * @date 2013-6-14
// *
// *
// */
//@Entity
//
//@Table( 
//    name = "sa_job_preprocessor"
//)
//
//@NamedQueries({
//	
//    @NamedQuery(name="SAJobPreprocessorDAO.listByJobId",
//            query="SELECT sajp FROM SAJobPreprocessor sajp WHERE sajp.jobId = :jobId ORDER BY step"),
//           
//})
//public class SAJobPreprocessor implements Serializable {
//	
//	private static final long serialVersionUID = 6531587223965041361L;
//
//	public SAJobPreprocessor() {
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
//	@Column( name = "preprocessor_name", length = 64)
//	private String preprocessorName;
//	
//	@Column( name = "param_value", length = 2048)
//	private String paramValue;
//	
//	@Column( name = "step")
//	private int step;
//	
//	/**
//	 * while persist eq true, need to specify schema 
//	 */
//	@Column( name = "persist")
//	private boolean persist;
//	
//	@Column( name = "storage_sql", length = 1024)
//	private String storageSql;
//	
//	@Column( name = "schema_id")
//	private long schemaId;
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
//	public String getPreprocessorName() {
//		return preprocessorName;
//	}
//
//	public void setPreprocessorName(String preprocessorName) {
//		this.preprocessorName = preprocessorName;
//	}
//
//	public String getParamValue() {
//		return paramValue;
//	}
//
//	public void setParamValue(String paramValue) {
//		this.paramValue = paramValue;
//	}
//
//	public int getStep() {
//		return step;
//	}
//
//	public void setStep(int step) {
//		this.step = step;
//	}
//
//	public boolean isPersist() {
//		return persist;
//	}
//
//	public void setPersist(boolean persist) {
//		this.persist = persist;
//	}
//	
//	public String getStorageSql() {
//		return storageSql;
//	}
//
//	public void setStorageSql(String storageSql) {
//		this.storageSql = storageSql;
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
//	@Override
//	public String toString() {
//		return "SAJobPreprocessor [id=" + id + ", jobId=" + jobId
//				+ ", preprocessorName=" + preprocessorName + ", paramValue="
//				+ paramValue + ", step=" + step + ", persist=" + persist
//				+ ", storageSql=" + storageSql + ", schemaId=" + schemaId
//				+ ", dateCreated=" + dateCreated + ", lastModified="
//				+ lastModified + ", remark=" + remark + "]";
//	}
//
//}
