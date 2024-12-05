package com.lyc.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * @author lyc
 */
public class SnowUtil {
    private static long workerId = 1L;
    private static long dataCenterId = 1L;

    public static long getSnowflakeNextId() {
        return IdUtil.getSnowflake(workerId, dataCenterId).nextId();
    }

    public static String getSnowflakeNextIdStr() {
        return IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
    }
}
