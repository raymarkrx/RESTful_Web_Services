//package com.aichuche.sequence;
//
//import java.sql.*;
//
//import com.chh.utils.DbUtils; 
//
//
///** 
//* Created by IntelliJ IDEA.<br> 
//* <b>User</b>: leizhimin<br> 
//* <b>Date</b>: 2008-4-2 15:24:52<br> 
//* <b>Note</b>: Sequence载体 
//*/ 
//
//public class KeyInfo { 
//    private long maxKey;        //当前Sequence载体的最大值 
//    private long minKey;        //当前Sequence载体的最小值 
//    private long nextKey;       //下一个Sequence值 
//    private int poolSize;       //Sequence值缓存大小 
//    private String keyName;     //Sequence的名称 
//    private static final String sql_update = "UPDATE KEYTABLE SET KEYVALUE = KEYVALUE + ? WHERE KEYNAME = ?"; 
//    private static final String sql_query = "SELECT KEYVALUE FROM KEYTABLE WHERE KEYNAME = ?"; 
//
//    public KeyInfo(String keyName, int poolSize) throws SQLException { 
//        this.poolSize = poolSize; 
//        this.keyName = keyName; 
//        retrieveFromDB(); 
//    } 
//
//    public String getKeyName() { 
//        return keyName; 
//    } 
//
//    public long getMaxKey() { 
//        return maxKey; 
//    } 
//
//    public long getMinKey() { 
//        return minKey; 
//    } 
//
//    public int getPoolSize() { 
//        return poolSize; 
//    } 
//
//    /** 
//     * 获取下一个Sequence值 
//     * 
//     * @return 下一个Sequence值 
//     * @throws SQLException 
//     */ 
//    public synchronized long getNextKey() throws SQLException { 
//        if (nextKey > maxKey) { 
//            retrieveFromDB(); 
//        } 
//        return nextKey++; 
//    } 
//
//    /** 
//     * 执行Sequence表信息初始化和更新工作 
//     * 
//     */ 
//    private void retrieveFromDB() throws SQLException { 
//        Connection conn = DbUtils.getConn(); 
//        //查询数据库 
//        PreparedStatement pstmt_query = conn.prepareStatement(sql_query); 
//        pstmt_query.setString(1, keyName); 
//        ResultSet rs = pstmt_query.executeQuery(); 
//        if (rs.next()) { 
//            maxKey = rs.getLong(1) + poolSize; 
//            minKey = maxKey - poolSize + 1; 
//            nextKey = minKey; 
//            rs.close(); 
//            pstmt_query.close(); 
//        } else { 
//            System.out.println("执行Sequence数据库初始化工作！"); 
//            String init_sql = "INSERT INTO KEYTABLE(KEYNAME,KEYVALUE) VALUES('" + keyName + "',10000 + " + poolSize + ")"; 
//            Statement stmt = conn.createStatement(); 
//            stmt.executeUpdate(init_sql); 
//            maxKey = 10000 + poolSize; 
//            minKey = maxKey - poolSize + 1; 
//            nextKey = minKey; 
//            stmt.close(); 
//            return; 
//        } 
//
//        //更新数据库 
//        conn.setAutoCommit(false); 
//        System.out.println("更新Sequence最大值！"); 
//        PreparedStatement pstmt_up = conn.prepareStatement(sql_update); 
//        pstmt_up.setLong(1, poolSize); 
//        pstmt_up.setString(2, keyName); 
//        pstmt_up.executeUpdate(); 
//        pstmt_up.close(); 
//        conn.commit(); 
//
//        rs.close(); 
//        pstmt_query.close(); 
//        conn.close(); 
//    } 
//}