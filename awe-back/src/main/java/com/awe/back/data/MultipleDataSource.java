package com.awe.back.data;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/7/26.</p>
 */
public class MultipleDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dateType = DataSourceContextHolder.getDataSource();
        if (StringUtils.isEmpty(dateType) || dateType.equals(DataSourceEnum.WRITE.getValue())) {
            return DataSourceEnum.WRITE.getValue();
        }
        return DataSourceEnum.READ.getValue();
    }
}
