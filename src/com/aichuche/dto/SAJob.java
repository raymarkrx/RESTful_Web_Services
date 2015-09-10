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
// * @date 2013-6-14
// *
// *
// */
//@Entity
//
//@Table( 
//    name = "sa_job"
//)
//@NamedQueries({
//
//    @NamedQuery(name="SAJobDAO.existsJobName",
//            query="SELECT 1 FROM SAJob saj WHERE saj.jobName = :jobName AND saj.disabled = 0"),
//    @NamedQuery(name="SAJobDAO.disableJob",
//      		query="UPDATE SAJob saj SET saj.disabled = 1 WHERE saj.id = :jobId"),
//	@NamedQuery(name = "SAJobDAO.getByDataName", 
//			query="SELECT saj FROM SAJob saj WHERE saj.dataName like :dataName AND saj.disabled = 0"),
//	@NamedQuery(name = "SAJobDAO.listByJobType", 
//			query="SELECT saj FROM SAJob saj WHERE saj.jobType = :jobType AND saj.disabled = 0"),
//})
//public class SAJob implements Serializable {
//	
//	private static final long serialVersionUID = 6531587223965041361L;
//
//	public SAJob() {
//		
//	}
//	
//	@Id
//	@GeneratedValue( strategy = GenerationType.AUTO )
//    @Column( name = "id")
//	private long id;
//	
//	@Column( name = "job_name", length = 64)
//	private String jobName;
//	
//	@Column( name = "group_id")
//	private long groupId;
//	
//	/**
//	 * refer to interface def: UtilData.SASource.Type
//	 */
//	@Column( name = "job_type")
//	private int jobType;
//	
//	@Column( name = "owner", length = 64)
//	private String owner;
//	
//	@Column( name = "data_name", length = 64)
//	private String dataName;
//	
//	/**
//	 * while the schedule_id eq 0, means the sajob depends on other sajob
//	 */
//	@Column( name = "schedule_id")
//	private long scheduleId;
//	
//	@Column( name = "source_id")
//	private long sourceId;
//	
//	@Column( name = "storage_id")
//	private long storageId;
//	
//	@Column( name = "data_fetch_sql", length = 204800)
//	private String dataFetchSql;
//	
//	@Column( name = "source_schema_id")
//	private long sourceSchemaId;
//	
//	/**
//	 * record the sajob last starting time(min unit: minute)
//	 */
//	@Column( name = "timeline")
//	private long timeline;
//	
//	@Column( name = "latest_end_date")
//	private Date latestEndDate;
//	
//	@Column( name = "last_finished_date")
//	private Date lastFinishedDate;
//	
//	@Column( name = "start_time")
//	private Date startTime;
//	
//	@Column( name = "end_time")
//	private Date endTime;
//	
//	@Column( name = "sa_interval")
//	private int interval;
//
//	@Column( name = "schedule_type")
//	private int scheduleType;
//	
//	/**
//	 * refer to UtilData.SASchema.Type
//	 */
//	@Column( name = "storage_type")
//	private int storageType;
//	
//	@Column( name = "storage_schema_id")
//	private long storageSchemaId;
//	
//	@Column( name = "data_store_sql", length = 204800)
//	private String dataStoreSql;
//	
//	@Column( name = "date_created")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date dateCreated;
//	
//	@Column( name = "last_modified")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date lastModified;
//	
//	@Column( name = "disabled")
//	private boolean disabled;
//	
//	@Column( name = "remark", length = 1024)
//	private String remark;
//
//	@Column( name = "regular_exec_duration")
//	private int regularExecDuration;
//	
//	@Column( name = "data_fetch_order_by")
//	private String dataFetchOrderBy;
//
//	@Column( name = "data_fetch_offset_Time")
//	private long dataFetchOffsetTime;
//
//	@Column( name = "dis_allow_empty_fetch")
//	private boolean disAllowEmptyFetch;
//
//	@Column( name = "base_time")
//	private Date baseTime;
//	
//	@Transient
//	private SASchema sourceSchema;
//
//	@Transient
//	private SASchema storageSchema;
//
//	@Transient
//	private List<SAJobPreprocessor> jobPreprocessors;
//
//	@Column( name = "meta_type")
//	private String metaType;
//
//	public String getMetaType() {
//		return metaType;
//	}
//
//	public void setMetaType(String metaType) {
//		this.metaType = metaType;
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
//	public String getJobName() {
//		return jobName;
//	}
//
//	public void setJobName(String jobName) {
//		this.jobName = jobName;
//	}
//
//	public long getGroupId() {
//		return groupId;
//	}
//
//	public void setGroupId(long groupId) {
//		this.groupId = groupId;
//	}
//
//	public int getJobType() {
//		return jobType;
//	}
//
//	public void setJobType(int jobType) {
//		this.jobType = jobType;
//	}
//
//	public String getOwner() {
//		return owner;
//	}
//
//	public void setOwner(String owner) {
//		this.owner = owner;
//	}
//
//	public String getDataName() {
//		return dataName;
//	}
//
//	public void setDataName(String dataName) {
//		this.dataName = dataName;
//	}
//
//	public long getScheduleId() {
//		return scheduleId;
//	}
//
//	public void setScheduleId(long scheduleId) {
//		this.scheduleId = scheduleId;
//	}
//
//	public long getSourceId() {
//		return sourceId;
//	}
//
//	public void setSourceId(long sourceId) {
//		this.sourceId = sourceId;
//	}
//
//	public long getStorageId() {
//		return storageId;
//	}
//
//	public void setStorageId(long storageId) {
//		this.storageId = storageId;
//	}
//
//	public String getDataFetchSql() {
//		return dataFetchSql;
//	}
//
//	public void setDataFetchSql(String dataFetchSql) {
//		this.dataFetchSql = dataFetchSql;
//	}
//
//	public long getSourceSchemaId() {
//		return sourceSchemaId;
//	}
//
//	public void setSourceSchemaId(long sourceSchemaId) {
//		this.sourceSchemaId = sourceSchemaId;
//	}
//
//	public long getTimeline() {
//		return timeline;
//	}
//
//	public void setTimeline(long timeline) {
//		this.timeline = timeline;
//	}
//
//	public Date getLastFinishedDate() {
//		return lastFinishedDate;
//	}
//
//	public void setLastFinishedDate(Date lastFinishedDate) {
//		this.lastFinishedDate = lastFinishedDate;
//	}
//
//	public Date getLatestEndDate() {
//		return latestEndDate;
//	}
//
//	public void setLatestEndDate(Date latestEndDate) {
//		this.latestEndDate = latestEndDate;
//	}
//
//	public int getStorageType() {
//		return storageType;
//	}
//
//	public void setStorageType(int storageType) {
//		this.storageType = storageType;
//	}
//
//	public long getStorageSchemaId() {
//		return storageSchemaId;
//	}
//
//	public void setStorageSchemaId(long storageSchemaId) {
//		this.storageSchemaId = storageSchemaId;
//	}
//
//	public String getDataStoreSql() {
//		return dataStoreSql;
//	}
//
//	public void setDataStoreSql(String dataStoreSql) {
//		this.dataStoreSql = dataStoreSql;
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
//	public boolean isDisabled() {
//		return disabled;
//	}
//
//	public void setDisabled(boolean disabled) {
//		this.disabled = disabled;
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
//	public int getRegularExecDuration() {
//		return regularExecDuration;
//	}
//
//	public void setRegularExecDuration(int regularExecDuration) {
//		this.regularExecDuration = regularExecDuration;
//	}
//	
//	public Date getStartTime() {
//		return startTime;
//	}
//
//	public void setStartTime(Date startTime) {
//		this.startTime = startTime;
//	}
//
//	public Date getEndTime() {
//		return endTime;
//	}
//
//	public void setEndTime(Date endTime) {
//		this.endTime = endTime;
//	}
//
//	public int getInterval() {
//		return interval;
//	}
//
//	public void setInterval(int interval) {
//		this.interval = interval;
//	}
//
//	public String getDataFetchOrderBy() {
//		return dataFetchOrderBy;
//	}
//
//	public void setDataFetchOrderBy(String dataFetchOrderBy) {
//		this.dataFetchOrderBy = dataFetchOrderBy;
//	}
//
//	public int getScheduleType() {
//		return scheduleType;
//	}
//
//	public void setScheduleType(int scheduleType) {
//		this.scheduleType = scheduleType;
//	}
//
//	
//	public long getDataFetchOffsetTime() {
//		return dataFetchOffsetTime;
//	}
//
//	public void setDataFetchOffsetTime(long dataFetchOffsetTime) {
//		this.dataFetchOffsetTime = dataFetchOffsetTime;
//	}
//
//
//	public boolean isDisAllowEmptyFetch() {
//		return disAllowEmptyFetch;
//	}
//
//	public void setDisAllowEmptyFetch(boolean disAllowEmptyFetch) {
//		this.disAllowEmptyFetch = disAllowEmptyFetch;
//	}
//
//	public Date getBaseTime() {
//		return baseTime;
//	}
//
//	public void setBaseTime(Date baseTime) {
//		this.baseTime = baseTime;
//	}
//
//	public SASchema getSourceSchema() {
//		return sourceSchema;
//	}
//
//	public void setSourceSchema(SASchema sourceSchema) {
//		this.sourceSchema = sourceSchema;
//	}
//
//	public SASchema getStorageSchema() {
//		return storageSchema;
//	}
//
//	public void setStorageSchema(SASchema storageSchema) {
//		this.storageSchema = storageSchema;
//	}
//
//	public List<SAJobPreprocessor> getJobPreprocessors() {
//		return jobPreprocessors;
//	}
//
//	public void setJobPreprocessors(List<SAJobPreprocessor> jobPreprocessors) {
//		this.jobPreprocessors = jobPreprocessors;
//	}
//
//	//removed the sql info
//	@Override
//	public String toString() {
//		return "SAJob [id=" + id + ", jobName=" + jobName + ", groupId="
//				+ groupId + ", jobType=" + jobType + ", owner=" + owner
//				+ ", dataName=" + dataName + ", scheduleId=" + scheduleId
//				+ ", sourceId=" + sourceId + ", storageId=" + storageId
//				+ ", sourceSchemaId="
//				+ sourceSchemaId + ", timeline=" + timeline
//				+ ", latestEndDate=" + latestEndDate + ", lastFinishedDate="
//				+ lastFinishedDate + ", startTime=" + startTime + ", endTime="
//				+ endTime + ", interval=" + interval + ", scheduleType="
//				+ scheduleType + ", storageType=" + storageType
//				+ ", storageSchemaId=" + storageSchemaId 
//				+ ", dateCreated=" + dateCreated
//				+ ", lastModified=" + lastModified + ", disabled=" + disabled
//				+ ", remark=" + remark + ", regularExecDuration="
//				+ regularExecDuration + ", dataFetchOrderBy="
//				+ dataFetchOrderBy + ", dataFetchOffsetTime="
//				+ dataFetchOffsetTime + ", disAllowEmptyFetch="
//				+ disAllowEmptyFetch + ", baseTime=" + baseTime + "]";
//	}
//
//}
