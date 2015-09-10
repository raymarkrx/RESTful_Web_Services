/*     */ package com.chh.utils;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class BaseBean
/*     */ {
/*  12 */   static Properties prop = new Properties();
/*     */ 
/*     */   static {
/*     */     try {
/*  16 */       String filePath = System.getProperty("user.dir") + 
/*  17 */         "/sys.properties";
/*  18 */       InputStream in = new BufferedInputStream(new FileInputStream(
/*  19 */         filePath));
/*     */ 
/*  21 */       prop.load(in);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  27 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getGeocoderUrl() {
/*  32 */     return prop.getProperty("baidu.lbs.api.geocoder.url");
/*     */   }
/*     */ 
/*     */   public String getAk() {
/*  36 */     return prop.getProperty("baidu.lbs.api.geocoder.param.ak");
/*     */   }
/*     */ 
/*     */   public String getOutput() {
/*  40 */     return prop.getProperty("baidu.lbs.api.geocoder.param.output");
/*     */   }
/*     */ 
/*     */   public String getPois() {
/*  44 */     return prop.getProperty("baidu.lbs.api.geocoder.param.pois");
/*     */   }
/*     */ 
/*     */   public String getCoordtype() {
/*  48 */     return prop.getProperty("baidu.lbs.api.geocoder.param.coordtype");
/*     */   }
/*     */ 
/*     */   public String getMailHost() {
/*  52 */     return prop.getProperty("mail.host");
/*     */   }
/*     */ 
/*     */   public String getMailUserName() {
/*  56 */     return prop.getProperty("mail.username");
/*     */   }
/*     */ 
/*     */   public String getMailPwd() {
/*  60 */     return prop.getProperty("mail.password");
/*     */   }
/*     */ 
/*     */   public String getMailFrom() {
/*  64 */     return prop.getProperty("mail.from");
/*     */   }
/*     */ 
/*     */   public String getDefaultTo() {
/*  68 */     return prop.getProperty("default.to");
/*     */   }
/*     */ 
/*     */   public String getDefaultCc() {
/*  72 */     return prop.getProperty("default.cc");
/*     */   }
/*     */ 
/*     */   public String getDefaultBcc() {
/*  76 */     return prop.getProperty("default.bcc");
/*     */   }
/*     */ 
/*     */   public String getFailurePoiFile() {
/*  80 */     return prop.getProperty("failure.poi.file");
/*     */   }
/*     */ 
/*     */   public String getReSuccessPoiFile() {
/*  84 */     return prop.getProperty("re.success.poi.file");
/*     */   }
/*     */ 
/*     */   public String getErrorFile() {
/*  88 */     return prop.getProperty("error.file");
/*     */   }
/*     */ 
/*     */   public String getSuccessPoiFile() {
/*  92 */     return prop.getProperty("success.poi.file");
/*     */   }
/*     */ 
/*     */   public String getEmailFile() {
/*  96 */     return prop.getProperty("email.file");
/*     */   }
/*     */ 
/*     */   public String getMonitorFile() {
/* 100 */     return prop.getProperty("monitor.file");
/*     */   }
/*     */ 
/*     */   public String getLocalFileDir() {
/* 104 */     return prop.getProperty("local.file.dir");
/*     */   }
/*     */ 
/*     */   public String getLatlngFile() {
/* 108 */     return prop.getProperty("lat.lng.file");
/*     */   }
/*     */ 
/*     */   public String getHDFSLatLngFileDir() {
/* 112 */     return prop.getProperty("hdfs.lat.lng.file.dir");
/*     */   }
/*     */ 
/*     */   public String getHttpProxyHost() {
/* 116 */     return prop.getProperty("http.proxyHost");
/*     */   }
/*     */ 
/*     */   public String getHttpProxyPort() {
/* 120 */     return prop.getProperty("http.proxyPort");
/*     */   }
/*     */ 
/*     */   public String getHttpNonProxyHosts() {
/* 124 */     return prop.getProperty("http.nonProxyHosts");
/*     */   }
/*     */ 
/*     */   public String getFtpProxyHost() {
/* 128 */     return prop.getProperty("ftp.proxyHost");
/*     */   }
/*     */ 
/*     */   public String getFtpProxyPort() {
/* 132 */     return prop.getProperty("ftp.proxyPort");
/*     */   }
/*     */ 
/*     */   public String getFtpNonProxyHosts() {
/* 136 */     return prop.getProperty("ftp.nonProxyHosts");
/*     */   }
/*     */ 
/*     */   public String getHttpsProxyHost() {
/* 140 */     return prop.getProperty("https.proxyHost");
/*     */   }
/*     */ 
/*     */   public String getHttpsProxyPort() {
/* 144 */     return prop.getProperty("https.proxyPort");
/*     */   }
/*     */ 
/*     */   public String getSocksProxyHost() {
/* 148 */     return prop.getProperty("socks.proxyHost");
/*     */   }
/*     */ 
/*     */   public String getSocksProxyPort() {
/* 152 */     return prop.getProperty("socks.proxyPort");
/*     */   }
/*     */ 
/*     */   public String getProxyHostUsername() {
/* 156 */     return prop.getProperty("proxy.host.username");
/*     */   }
/*     */ 
/*     */   public String getProxyHostPassword() {
/* 160 */     return prop.getProperty("proxy.host.password");
/*     */   }
/*     */ 
/*     */   public String getHDFSPoiFileDir() {
/* 164 */     return prop.getProperty("hdfs.poi.file.dir");
/*     */   }
/*     */ 
/*     */   public boolean isUseHttpProxy() {
/* 168 */     boolean isUseHttpProxy = false;
/* 169 */     String useHttpProxy = prop.getProperty("use.http.proxy");
/* 170 */     if ("1".equals(useHttpProxy)) {
/* 171 */       isUseHttpProxy = true;
/*     */     }
/* 173 */     return isUseHttpProxy;
/*     */   }
/*     */ }

/* Location:           D:\saic_days_2015\MR程序\lbs2poi\2poi\LBSPoiJobService.jar
 * Qualified Name:     com.lbs.poi.vcar.utils.BaseBean
 * JD-Core Version:    0.6.2
 */