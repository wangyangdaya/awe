package com.awe.core.storage;

/**
 * description FastDFS 文件存储
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/26.</p>
 */
public class FastDFSStorage implements FileStorage {
    @Override
    public String upload() {
        return "FastDFS storage upload.";
    }
}
