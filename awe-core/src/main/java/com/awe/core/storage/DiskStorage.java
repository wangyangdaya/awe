package com.awe.core.storage;

/**
 * description 硬盘文件存储
 *
 * @author wangy QQ 837195190
 * <p>Created by DELL-5490 on 2018/6/26.</p>
 */
public class DiskStorage implements FileStorage{

    /**
     * 文件上传
     * @return 描述信息
     */
    @Override
    public String upload() {
        return "disk storage upload.";
    }
}
