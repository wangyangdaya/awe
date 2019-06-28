package com.awe.core.util;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/9/11.</p>
 */
public abstract class VacationUtils {

    private static ConcurrentHashMap<String, List<String>> vacations = new ConcurrentHashMap<>();

    private VacationUtils() {
    }

    private void init() {
        vacations.put("国庆节", Arrays.asList(""));
    }
}
