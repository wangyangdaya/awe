package com.awe.web.service;

import com.awe.core.throwable.GeneralException;
import com.awe.core.util.SqliteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * description 文件上传
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/22.</p>
 */
@Service
public class FileService {

    /**
     * 定长线程池
     */
    private ExecutorService fixedThreadPool;

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Value("${file.upload.chunkSize}")
    private long CHUNK_SIZE;

    @Value("${file.upload.path}")
    private String fileBasePath;

    public FileService() {
        this.fixedThreadPool = Executors.newFixedThreadPool(5);
    }

    /**
     * 端点判断
     *
     * @param md5 文件md5
     * @return 丢失片
     */
    public Map<String, Object> checkFile(String md5) {
        Map<String, Object> data = new HashMap<>();
        try {
            data = SqliteUtils.query(md5);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     * 文件上传
     *
     * @param file  文件
     * @param md5   文件md5
     * @param chunk 文件片
     * @return
     */
    public String upload(MultipartFile file, String md5, int chunks, int chunk ,long size) {
        // 创建文件md5 文件夹
//        String filePath = fileBasePath + md5;
//        File uploadDir = new File(filePath);
//        File upload = new File(filePath, file.getOriginalFilename());
//        if (!uploadDir.exists() && !uploadDir.isDirectory()) {
//            uploadDir.mkdirs();
//        }
        File upload = new File(fileBasePath, file.getOriginalFilename());

        // 随机流
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(upload, "rw")) {

            // 创建固定大小文件 第一次创建文件大小，占用磁盘
            if (randomAccessFile.length() == 0) {
                randomAccessFile.setLength(size);
            }

            FileChannel fileChannel = randomAccessFile.getChannel();
            //写入该分片数据
            long offset = CHUNK_SIZE * chunk;
            byte[] fileData = file.getBytes();
            // 内存映射
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
            mappedByteBuffer.put(fileData);
            // clean mappedByteBuffer
            cleanMappedByteBuffer(mappedByteBuffer);
            fileChannel.close();

            /**
             * 数据库读写压力小于磁盘IO, 大文件上传高并发情况, 文件信息写入, 采用定长线程池
             *
             * update 方法 synchronized 定长线程池, 减少锁的膨胀
             *
             * 文件控制信息录入,默认脱离文件实际写入
             */
//            fixedThreadPool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        logger.info("update file message.");
//                        SqliteUtils.update(md5, chunks, chunk);
//                    } catch (ClassNotFoundException e) {
//                        throw new GeneralException(e.getMessage());
//                    }
//                }
//            });

            final List<Integer> result = new ArrayList<>();

            FutureTask<List<Integer>> task = new FutureTask<List<Integer>>(new Runnable() {
                @Override
                public void run() {
                    try {
                        logger.info("update file message.");
                        int i = SqliteUtils.update(md5, chunks, chunk);
                        result.add(i);
                    } catch (ClassNotFoundException e) {
                        throw new GeneralException(e.getMessage());
                    }
                }
            }, result);

            fixedThreadPool.execute(task);

            if (!task.get().isEmpty() && task.get().get(0) > 0) {
                logger.info(task.get().toString());
                logger.info("文件上传成功");
            } else {
                logger.info(task.get().toString());
                throw new GeneralException("上传失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "上传成功";
    }

    /**
     * clean mappedByteBuffer
     *
     * @param mappedByteBuffer buffer
     */
    public static void cleanMappedByteBuffer(final MappedByteBuffer mappedByteBuffer) {
        try {
            if (mappedByteBuffer == null) {
                return;
            }

            mappedByteBuffer.force();
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    try {
                        Method getCleanerMethod = mappedByteBuffer.getClass().getMethod("cleaner", new Class[0]);
                        getCleanerMethod.setAccessible(true);
                        sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(mappedByteBuffer,
                                new Object[0]);
                        cleaner.clean();
                    } catch (Exception e) {
                        logger.error("clean MappedByteBuffer error!!!", e);
                    }
                    logger.info("clean MappedByteBuffer completed!!!");
                    return null;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
