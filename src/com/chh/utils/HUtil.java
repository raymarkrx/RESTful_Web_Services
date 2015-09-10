//package com.chh.utils;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Map.Entry;
//import java.util.TimeZone;
//
////import java.io.IOException;
////import org.apache.commons.lang.exception.ExceptionUtils;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FSDataInputStream;
//import org.apache.hadoop.fs.FSDataOutputStream;
//import org.apache.hadoop.fs.FileStatus;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
////import org.apache.hadoop.hdfs.protocol.DatanodeInfo; 
//
//
//public class HUtil { 
//	//private static Logger log=Logger.getLogger(HUtil.getClass());
//        public synchronized static FileSystem getFileSystem()         {
//                FileSystem fs = null;
//                
//                //String url = "hdfs://" + ip + ":" + String.valueOf(port);
//                Configuration config = new Configuration();
//        		//config.addResource("com/hadoop/core-site.xml");
//        		config.addResource("config/hadoop/core-site.xml");
//        		//config.addResource("classpath:/hadoop/hdfs-site.xml");
//        		//config.addResource("classpath:/hadoop/mapred-site.xml");
//        		//config.addResource(new Path("D://Java//workspace//yunfs//WebContent//WEB-INF//classes//com//hadoop//core-site.xml"));   
//                 
//        	    //System.out.println(config.toString());  
//                
//                //config.set("fs.default.name", url);
//                //System.out.println(config.getRaw("fs.default.name"));  
//                //System.out.println(config.get("fs.default.name"));
//        		
//        		
//                try {
//                        fs = FileSystem.get(config);
//                } catch (Exception e) {
//                	e.printStackTrace();
//                }
//                return fs;
//         }
////        public synchronized static void listNode(FileSystem fs) {
////                DistributedFileSystem dfs = (DistributedFileSystem) fs;
////                try {
////                        DatanodeInfo[] infos = dfs.getDataNodeStats();
////                        for (DatanodeInfo node : infos) {
////                                System.out.println("HostName: " + node.getHostName() + "/n"
////                                                + node.getDatanodeReport());
////                                System.out.println("--------------------------------");
////                        }
////                } catch (Exception e) {
////                	e.printStackTrace();
////                }
////        }
//        /**
//         * 
//         * 
//         * @param fs
//         */
//        public synchronized static void listConfig(FileSystem fs) {
//                Iterator<Entry<String, String>> entrys = fs.getConf().iterator();
//                while (entrys.hasNext()) {
////                        Entry<String, String> item = entrys.next();
//                }
//        }
//        /**
//         * 
//         * 
//         * @param fs
//         * @param dirName
//         * Note:"dirName" = "dirName/"
//         */
//        public synchronized static boolean mkdirs(FileSystem fs, String dirName) {
//                Path workDir = fs.getWorkingDirectory();
//                String dir = workDir + "/" + dirName;
//                Path src = new Path(dir);
//                boolean succ = false;
//                try {
//                        succ = fs.mkdirs(src);
//                        
//                        if (succ) {
//                        	System.out.println("create dir SUCCESS!");
//                        } else {
//                        	System.out.println("create dir Fail!!");
//                        }
//                } catch (Exception e) {
//                	e.printStackTrace();
//                }
//                return succ;
//        }
//        
//        /**
//         * 
//         * 
//         * @param fs
//         * @param dirName
//         * Note:"dirName" = "dirName/"
//         */
//        public synchronized static boolean mkdirs2(FileSystem fs, String dirName) {
//                
//                String dir = dirName;
//                Path src = new Path(dir);
//                boolean succ = false;
//                try {
//                        succ = fs.mkdirs(src);
//                        if (succ) {
//                        	System.out.println("create dir SUCCESS!");
//                        } else {
//                        	System.out.println("create dir Fail!!");
//                        }
//                } catch (Exception e) {
//                	e.printStackTrace();
//                }
//                return succ;
//        }
//        
//        /**
//         * 
//         * 
//         * @param fs
//         * @param dirName
//         */
//        public synchronized static boolean rmdirs(FileSystem fs, String dirName) {
//                // Path home = fs.getHomeDirectory();
//                Path workDir = fs.getWorkingDirectory();
//                String dir = workDir + "/" + dirName;
//                Path src = new Path(dir);
//                boolean succ = false;
//                try {
//                        succ = fs.delete(src, true);
//                        if (succ) {
//                                //log.info("remove directory " + dir + " successed. ");
//                        } else {
//                                //log.info("remove directory " + dir + " failed. ");
//                        }
//                } catch (Exception e) {
//                }
//                return succ;
//        }
//        /**
//         * 
//         * 
//         * @param fs
//         * @param local
//         * @param remote
//         * Note: local should not be unexisted.
//         * Note: Parent path is allowed, but both local and remote file path should exist.
//         * upload path below the /home/hdfs  (workDir)
//         */
//        public synchronized static void upload(FileSystem fs, String local,
//                        String remote) {
//                // Path home = fs.getHomeDirectory();
//                Path workDir = fs.getWorkingDirectory();
//                Path dst = new Path(workDir + "/" + remote);
//                Path src = new Path(local);
//                try {
//                        fs.copyFromLocalFile(false, true, src, dst);
//                        //log.info("upload " + local + " to  " + remote + " successed. ");
//                } catch (Exception e) {
//                		e.printStackTrace();
//                }
//        }
//        
//        public synchronized static void upload2(FileSystem fs, String local,
//                String remote) {
//	        // Path home = fs.getHomeDirectory();
//	        //Path workDir = fs.getWorkingDirectory();
//	        Path dst = new Path(remote);
//	        Path src = new Path(local);
//	        try {
//	                fs.copyFromLocalFile(false, true, src, dst);
//	                //System.out.println("upload " + local + " to  " + remote + " successed. ");
//	        } catch (Exception e) {
//	        	e.printStackTrace();
//	        }
//        }
//        
//        public synchronized static void upload3(FileSystem fs, String local,
//                String remote) {
//		        // Path home = fs.getHomeDirectory();
//		        Path workDir = fs.getWorkingDirectory();
//		        Path dst = new Path(workDir + "/" + remote);
//		        Path src = new Path(local);
//		        try {
//		                fs.copyFromLocalFile(false, true, src, dst);
//		                //log.info("upload " + local + " to  " + remote + " successed. ");
//		        } catch (Exception e) {
//		        		e.printStackTrace();
//		        }
//        }
//        
//        
//        /**
//         * 
//         * 
//         * @param fs
//         * @param local
//         * @param remote
//         * Note: Directories can also be downloaded.
//         */
//        public synchronized static void download(FileSystem fs, String local,
//                        String remote) {
//                Path dst = new Path(remote);
//                Path src = new Path(local);
//                try {
//                        fs.copyToLocalFile(false, dst, src);
//                } catch (Exception e) {
//                	e.printStackTrace();
//                }
//        }
//        
//        public synchronized static void download2(FileSystem fs, String local,
//                String remote) {
//        Path workDir = fs.getWorkingDirectory();
//        Path dst = new Path(workDir + "/" + remote);
////        System.out.println("dst:" + dst);
//        Path src = new Path(local);
//        try {
//                fs.copyToLocalFile(false, dst, src);
//        } catch (Exception e) {
//        	e.printStackTrace();
//        }
//}
//        
//        /**
//         *
//         * 
//         * @param size
//         * @return
//         */
//        public synchronized static String convertSize(long size) {
//                String result = String.valueOf(size);
//                if (size < 1024 * 1024) {
//                        result = String.valueOf(size / 1024) + " KB";
//                } else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
//                        result = String.valueOf(size / 1024 / 1024) + " MB";
//                } else if (size >= 1024 * 1024 * 1024) {
//                        result = String.valueOf(size / 1024 / 1024 / 1024) + " GB";
//                } else {
//                        result = result + " B";
//                }
//                return result;
//        }
//
//        /**
//         * 
//         * 
//         * @param fs
//         * @param path
//         */
//        public synchronized static String[] listFile(FileSystem fs, String path) {
//                Path dst = new Path(path);
//                String[] fileNames = null;
//                try {
//                        String relativePath = "";
//                        FileStatus[] fList = fs.listStatus(dst);
//                        fileNames = new String[fList.length];
//                        int i = 0;
//                        for (FileStatus f : fList) {
//                                if (null != f) {
//                                        relativePath = new StringBuffer().append(f.getPath().getName()).toString();
//                                        //System.out.println(relativePath);
//                                        fileNames[i] = relativePath;
//                                        i++;
//                                }
//                        }
//                } catch (Exception e) {
//                	e.printStackTrace();
//                } finally {
//                }
//                return fileNames;
//        }
//        
//        
//        public synchronized static String[] listFile2(FileSystem fs, String path) {
//            Path workDir = fs.getWorkingDirectory();
//            Path dst;
//            String[] fileNames = null;
//            if (null == path || "".equals(path)) {
//                    dst = new Path(workDir + "/" + path);
//            } else {
//                    dst = new Path(path);
//            }
//            try {
//                    String relativePath = "";
//                    FileStatus[] fList = fs.listStatus(dst);
//                    fileNames = new String[fList.length];
//                    int i = 0;
//                    for (FileStatus f : fList) {
//                            if (null != f) {
//                                    relativePath = new StringBuffer().append(f.getPath().getName()).toString();
//                                    System.out.println(relativePath);
//                                    fileNames[i] = relativePath;
//                                    i++;
//                            }
//                    }
//            } catch (Exception e) {
//            	e.printStackTrace();
//            } finally {
//            }
//            return fileNames;
//    }
//        
//        
//        public static String TimeStamp2Date(long timestamp){
//        	SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//        	sd.setTimeZone(TimeZone.getTimeZone("GMT+8")); 
//        	String strDate = sd.format(new Date(timestamp));
//        	return strDate;
//        }
//        
//
//        /**
//         * 
//         * 
//         * @param fs
//         * @para path
//         * @para data
//         * @void
//         */
//        public synchronized static void write(FileSystem fs, String path,
//                        String data) { 
//                Path workDir = fs.getWorkingDirectory();
//                Path dst = new Path(workDir + "/" + path);
//                try {
//                        FSDataOutputStream dos = fs.create(dst);
//                        dos.writeUTF(data);
//                        dos.close();
//                } catch (Exception e) {
//                	e.printStackTrace();
//                }
//        }
//        /**
//         *
//         * 
//         * @param fs
//         * @para path
//         * @para data
//         * @void
//         */
//        public synchronized static void append(FileSystem fs, String path,
//                        String data) {
//                Path workDir = fs.getWorkingDirectory();
//                Path dst = new Path(workDir + "/" + path);
//                try {
//                        FSDataOutputStream dos = fs.append(dst);
//                        dos.writeUTF(data);
//                        dos.close();
//                } catch (Exception e) {
//                }
//        }
//        /**
//         *
//         * 
//         * @param fs
//         * @para path
//         * @return String
//         */
//        public synchronized static String read(FileSystem fs, String path) {
//                String content = null;
//                Path workDir = fs.getWorkingDirectory();
//                Path dst = new Path(workDir + "/" + path);
//                try {
//                        FSDataInputStream dis = fs.open(dst);
//                        content = dis.readUTF();
//                        dis.close();
//                } catch (Exception e) {
//                	e.printStackTrace();
//                }
//                return content;
//        }
//        /**
//         *
//         * 
//         * @param fs
//         * @para path
//         * @return boolean
//         */
//        public synchronized static boolean exists(FileSystem fs, String path){
//                Path workDir = fs.getWorkingDirectory();
//                Path dst = new Path(workDir + "/" + path);
//                boolean ret = false;
//                try {
//                        ret = fs.exists(dst);
//                } catch (Exception e) {
//                	e.printStackTrace();
//                }
//                return ret;
//        }
//        
//        public synchronized static boolean exists2(FileSystem fs, String path){
//            //Path workDir = fs.getWorkingDirectory();
//            Path dst = new Path( path);
//            boolean ret = false;
//            try {
//                    ret = fs.exists(dst);
//            } catch (Exception e) {
//            	e.printStackTrace();
//            }
//            return ret;
//    }
//        
//        /**
//         *
//         * 
//         * @param fs
//         * @para path
//         * @return boolean
//         */
//        //@SuppressWarnings("deprecation")
//        public synchronized static boolean delete(FileSystem fs, String path){
//                //Path workDir = fs.getWorkingDirectory();
//                Path dst = new Path(path);
//                boolean ret = false;
//                try {
//                        ret = fs.delete(dst, true);
//                } catch (Exception e) {
//                	e.printStackTrace();
//                }
//                return ret;
//        }
//        
//        
//        public synchronized static boolean rename(FileSystem fs, String src, String dst){
//        	Path srcPath = new Path(src);
//        	Path dstPath = new Path(dst);
//        	 boolean ret = false;
//             try {
//                     ret = fs.rename(srcPath, dstPath);
//             } catch (Exception e) {
//             	e.printStackTrace();
//             }
//             return ret;
//        	
//        }
//        //remove������������������rename���������
//        public synchronized static boolean remove(FileSystem fs, String src, String dst){
//        	Path srcPath = new Path(src);
//        	Path dstPath = new Path(dst);
//        	 boolean ret = false;
//             try {
//                     ret = fs.rename(srcPath, dstPath);
//             } catch (Exception e) {
//             	e.printStackTrace();
//             }
//             return ret;
//        	
//        }
//        
//        /**
//         *
//         * 
//         * @param fs
//         * @param path
//         * @return boolean
//         * Note As hadoop runs under supergroup, just judge by the first three letters of its fspermission:(rw-)r--r--
//         */
//        public synchronized static boolean canWrite(FileSystem fs, String path){
//                Path workDir = fs.getWorkingDirectory();
//                Path dst = new Path(workDir + "/" + path);
//                boolean ret = false;
//                try {
//                        FileStatus fStatus = fs.getFileStatus(dst);
//                        byte[] per = fStatus.getPermission().toString().getBytes();
////                        System.out.println("Permission: " + (char)per[0]);
//                        if ((char)per[1] == 'w')
//                                ret = true;
//                        else
//                                ret = false;
//                } catch (Exception e) {
//                	e.printStackTrace();
//                }
//                return ret;
//        }
//        /**
//         *
//         * 
//         * @param fs
//         * @param path
//         * @return boolean
//         */
//        public synchronized static boolean canRead(FileSystem fs, String path){
//                Path workDir = fs.getWorkingDirectory();
//                Path dst = new Path(workDir + "/" + path);
//                boolean ret = false;
//                try {
//                        FileStatus fStatus = fs.getFileStatus(dst);
//                        byte[] per = fStatus.getPermission().toString().getBytes();
////                        System.out.println("Permission: " + (char)per[0]);
//                        if ((char)per[0] == 'r')
//                                ret = true;
//                        else
//                                ret = false;
//                } catch (Exception e) {
//                	e.printStackTrace();
//                }
//                return ret;
//        }
//        /**
//         *
//         * 
//         * @param fs
//         * @param path
//         * @return boolean
//         */
//        public synchronized static boolean isDir(FileSystem fs, String path){
//                Path workDir = fs.getWorkingDirectory();
//                Path dst = new Path(workDir + "/" + path);
//                boolean ret = false;
//                try {
//                        FileStatus fStatus = fs.getFileStatus(dst);
//                        ret = fStatus.isDir();
////                        System.out.println("Permission: " + (char)per[0]);
//                        
//                } catch (Exception e) {
//                	e.printStackTrace();
//                }
//                return ret;
//        }
//        /**
//         *
//         * 
//         * @param fs
//         * @return String
//         */
//        public synchronized static String getWorkingDir(FileSystem fs){
//                Path workDir = fs.getWorkingDirectory();
//                return workDir.toString();
//        }
//        
//        public static void main(String[] args){
//        	FileSystem fs=HUtil.getFileSystem();
//        	String user="chh";
//        	String dir="chh/aaa";
//        	fs.setWorkingDirectory(new Path("/user/hdfs/chh/aaa"));
//        	System.out.println("getWorkingDirectory:"+fs.getWorkingDirectory().toString());
//        	System.out.println("getHomeDirectory:"+fs.getHomeDirectory().toString());
////        	if(HUtil.exists(fs,user)){
////        		System.out.println("the path exists!! now delete it");
////        		HUtil.delete(fs,user);
////        		System.out.println("delete OK!");
////        	}
//        	//HUtil.mkdirs(fs,dir);
//        	//HUtil.canWrite(fs,dir);
//        	//HUtil.upload(fs, "C:\\Users\\ffkwt\\Desktop\\temp\\add_table.txt", dir);
//        	
//        }
//        
//}