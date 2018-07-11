package com.awe.web.service;

import org.junit.Assert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/15.</p>
 */
public class MainTest {
    public static void main(String[] args) {
//        String[] s = new String[]{null, "1"};
//        Assert.assertTrue("contain", !Arrays.asList(s).contains(null));
//        Arrays.sort(s);

//        Connection c = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:upload.db");
//            Statement stmt = null;
//            c.setAutoCommit(false);
//
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM upload;");
//            System.out.println(rs.getRow());
//            while (rs.next()) {
//                String md5 = rs.getString("md5");
//                String chunks = rs.getString("chunks");
//                String complete = rs.getString("complete");
//
//                System.out.println("md5 = " + md5);
//                System.out.println("chunks = " + chunks);
//                System.out.println("complete = " + complete);
//                System.out.println();
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//        System.out.println("Opened database successfully");

        Set<String> strings = new HashSet<>(Arrays.asList("0", "1"));
        System.out.println(String.join(",", strings));
    }
}
