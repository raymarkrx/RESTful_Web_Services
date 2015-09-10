//package com.chh.utils;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.PrintStream;
//import java.net.URI;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FSDataInputStream;
//import org.apache.hadoop.fs.FSDataOutputStream;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IOUtils;
//
//public class HDFSUtil
//{
//  static Configuration conf = new Configuration();
//  static{
//	  conf.addResource("config/hadoop/core-site.xml");
//  }
//
//  public static boolean sendFile(String path, String localfile) throws Exception
//  {
//    File file = new File(localfile);
//    if (!file.isFile()) {
//      System.out.println(file.getName());
//      return false;
//    }
//
//    FSDataOutputStream fsOut = null;
//    FSDataInputStream fsIn = null;
//    try
//    {
//      FileSystem localFS = FileSystem.getLocal(conf);
//      FileSystem hadoopFS = FileSystem.get(conf);
//      fsOut = hadoopFS.create(new Path(path + "/" + file.getName()));
//      fsIn = localFS.open(new Path(localfile));
//      byte[] buf = new byte[1024];
//      int readbytes = 0;
//      while ((readbytes = fsIn.read(buf)) > 0) {
//        fsOut.write(buf, 0, readbytes);
//      }
//      fsIn.close();
//      fsOut.close();
//      return true;
//    } catch (IOException e) {
//      e.printStackTrace();
//      throw new Exception(e);
//    } finally {
//      try {
//        if (fsIn != null) {
//          fsIn.close();
//        }
//        if (fsOut != null)
//          fsOut.close();
//      }
//      catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//  }
//
//  public static boolean delFile(String hadfile) {
//    try {
//      FileSystem hadoopFS = FileSystem.get(conf);
//      Path hadPath = new Path(hadfile);
//      return hadoopFS.delete(hadPath, true);
//    }
//    catch (IOException e) {
//      e.printStackTrace();
//    }
//    return false;
//  }
//
//  public static boolean downloadFile(String hdfsFile, String localFile) throws Exception
//  {
//    FSDataOutputStream fsOut = null;
//    FSDataInputStream fsIn = null;
//    try
//    {
//      FileSystem localFS = FileSystem.getLocal(conf);
//      FileSystem hadoopFS = FileSystem.get(conf);
//      Path hadPath = new Path(hdfsFile);
//      fsOut = localFS.create(new Path(localFile));
//      fsIn = hadoopFS.open(hadPath);
//      byte[] buf = new byte[1024];
//      int readbytes = 0;
//      while ((readbytes = fsIn.read(buf)) > 0) {
//        fsOut.write(buf, 0, readbytes);
//      }
//      fsIn.close();
//      fsOut.close();
//      return true;
//    } catch (IOException e) {
//      e.printStackTrace();
//      throw new Exception(e);
//    } finally {
//      try {
//        if (fsIn != null) {
//          fsIn.close();
//        }
//        if (fsOut != null)
//          fsOut.close();
//      }
//      catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//  }
//
//  public static void copyLocal2HDFS(String hdfsFile, String localFile) throws Exception
//  {
//    OutputStream fsOut = null;
//    FileInputStream fsIn = null;
//    try
//    {
//      fsIn = new FileInputStream(new File(localFile));
//      FileSystem fs = FileSystem.get(URI.create(hdfsFile), conf);
//      fsOut = fs.create(new Path(hdfsFile));
//
//      IOUtils.copyBytes(fsIn, fsOut, 4096, true);
//    } catch (IOException e) {
//      e.printStackTrace();
//      throw new Exception(e);
//    } finally {
//      try {
//        if (fsIn != null) {
//          fsIn.close();
//        }
//        if (fsOut != null)
//          fsOut.close();
//      }
//      catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//  }
//
//  public static void copyHDFS2Local(String hdfsFile, String localFile) throws Exception
//  {
//    OutputStream fsOut = null;
//    FSDataInputStream fsIn = null;
//    try
//    {
//      FileSystem fs = FileSystem.get(URI.create(hdfsFile), conf);
//      fsIn = fs.open(new Path(hdfsFile));
//      fsOut = new FileOutputStream(localFile);
//      IOUtils.copyBytes(fsIn, fsOut, 4096, true);
//    } catch (IOException e) {
//      e.printStackTrace();
//      throw new Exception(e);
//    } finally {
//      try {
//        if (fsIn != null) {
//          fsIn.close();
//        }
//        if (fsOut != null)
//          fsOut.close();
//      }
//      catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//  }
//}