//package com.aichuche.sequence;
//
//import java.util.HashMap; 
//import java.util.Map; 
//import java.sql.SQLException; 
//
//public class SequenceUtils { 
//    private static SequenceUtils _instance = new SequenceUtils(); 
//    private Map<String, KeyInfo> keyMap = new HashMap<String, KeyInfo>(20); //Sequence载体容器 
//    private static final int POOL_SIZE = 10;      //Sequence值缓存大小 
//
//    /** 
//     * 禁止外部实例化 
//     */ 
//    private SequenceUtils() {
//    } 
//
//    /** 
//     * 获取SequenceUtils的单例对象 
//     * @return SequenceUtils的单例对象 
//     */ 
//    public static SequenceUtils getInstance() {
//        return _instance; 
//    } 
//
//    /** 
//     * 获取下一个Sequence键值 
//     * @param keyName Sequence名称 
//     * @return 下一个Sequence键值 
//     */ 
//    public synchronized long getNextKeyValue(String keyName) { 
//        KeyInfo keyInfo = null; 
//        Long keyObject = null; 
//        try { 
//            if (keyMap.containsKey(keyName)) { 
//                keyInfo = keyMap.get(keyName); 
//            } else { 
//                keyInfo = new KeyInfo(keyName, POOL_SIZE); 
//                keyMap.put(keyName, keyInfo); 
//            }
//            keyObject = keyInfo.getNextKey(); 
//        } catch (SQLException e) { 
//            e.printStackTrace(); 
//        } 
//        return keyObject; 
//    } 
//    
//    public static void main(String args[]) { 
//        test(); 
//    } 
//
//    /** 
//     * 测试Sequence方法 
//     */ 
//    public static void test() {
//        System.out.println("----------test()----------"); 
//        for (int i = 0; i < 2000; i++) { 
//            long x = SequenceUtils.getInstance().getNextKeyValue("sdaf");
//            System.out.println(x); 
//        }
//        
//       // System.out.println();
//    }
//    
////    DDL:
////    DROP TABLE KEYTABLE;
////    CREATE TABLE KEYTABLE (
////      KEYNAME varchar(24) NOT NULL COMMENT 'Sequence名称', 
////      KEYVALUE bigint(20) DEFAULT '10000' COMMENT 'Sequence最大值', 
////      PRIMARY KEY (KEYNAME) 
////    )
//    
//    
//}