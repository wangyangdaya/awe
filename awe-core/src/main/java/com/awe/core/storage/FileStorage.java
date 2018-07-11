package com.awe.core.storage;

/**
 * description 文件存储
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/26.</p>
 */
public interface FileStorage {

    default String upload() {
        return "file upload.";
    }

    default String download() {
        return "file download";
    }
}
