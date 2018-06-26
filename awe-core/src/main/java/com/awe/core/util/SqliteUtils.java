package com.awe.core.util;

import com.awe.core.throwable.GeneralException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.*;

/**
 * description sqlLite 数据库 demo
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/23.</p>
 */
public abstract class SqliteUtils {

    private static final Logger logger = LoggerFactory.getLogger(SqliteUtils.class);

    private static final int DEFAULT_CHUNKS = -1;

    private SqliteUtils() {
    }

    /**
     * 创建表
     */
    public static void create() throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:upload.db");
             Statement statement = connection.createStatement();) {
            // set timeout to 30 sec.
            statement.setQueryTimeout(30);
            // md5 fileMd5, chunks all chunks, complete complete chunks.
            if (!exists(statement)) {
                statement.executeUpdate("create table upload " +
                        "(id integer primary key autoincrement," +
                        " md5 CHAR(32) NOT NULL," +
                        " chunks INT NOT NULL," +
                        " complete VARCHAR(200))");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断表是否存在
     *
     * @param statement
     * @return
     */
    private static boolean exists(Statement statement) {
        String existsSql = "SELECT COUNT(1) as CNT FROM sqlite_master where type='table' and name='upload'";
        boolean exists = false;
        try (ResultSet resultSet = statement.executeQuery(existsSql)) {

            while (resultSet.next()) {
                return !Objects.isNull(resultSet.getObject("CNT"));
            }
        } catch (SQLException e) {
            logger.error("query error.", e);
            throw new GeneralException("query error.");
        }
        return exists;
    }


    public static synchronized int update(String md5, int chunks, int complete) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String query = String.format("select complete from upload where md5 = '%s'", md5);
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:upload.db");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            String sql;
            if (resultSet.next()) {
                if (resultSet.getRow() == 1) {
                    String completes = resultSet.getString("complete");
                    Set<String> c = new HashSet<>(Arrays.asList(completes.split(",")));
                    if (!c.contains(complete)) {
                        c.add(String.valueOf(complete));
                    }
                    sql = String.format("update upload set complete = '%s' where md5 = '%s'",
                            String.join(",", c), md5);
                } else {
                    logger.error("cannot uniquely locate the file.");
                    throw new GeneralException("cannot uniquely locate the file.");
                }
            } else {
                sql = String.format("insert into upload ('md5', 'chunks', 'complete') values('%s', %d, '%s')", md5, chunks, complete);
            }
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error("update upload error.", e);
            throw new GeneralException("update upload error.", e);
        }
    }

    public static Map<String, Object> query(String md5) throws ClassNotFoundException {
        Map<String, Object> result = new HashMap<>();
        Class.forName("org.sqlite.JDBC");
        List<String> completes = new ArrayList<>();
        int chunks = DEFAULT_CHUNKS;
        String sql = String.format("select chunks, complete from upload where md5 = '%s'", md5);
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:upload.db");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next() && resultSet.getRow() == 1) {
                String complete = resultSet.getString("complete");
                chunks = resultSet.getInt("chunks");
                if (!StringUtils.isEmpty(complete)) {
                    completes = Arrays.asList(complete.split(","));
                }
            }
            result.put("chunks", chunks);
            result.put("completes", completes);
        } catch (SQLException e) {
            logger.error("query upload file.", e);
            throw new GeneralException("query upload file.", e);
        }
        return result;
    }

}
