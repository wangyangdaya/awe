package com.awe.back.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.awe.back.data.DataSourceEnum;
import com.awe.back.data.DataSourceInterceptor;
import com.awe.back.data.MultipleDataSource;
import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/7/26.</p>
 */
@Configuration
@MapperScan("com.awe.back.mapper")
public class MybatisPlusConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.read")
    public DataSource readDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.write")
    public DataSource writeDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 动态数据源配置
     */
    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("readDataSource") DataSource readDataSource,
                                         @Qualifier("writeDataSource") DataSource writeDataSource) {
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceEnum.WRITE.getValue(), writeDataSource);
        targetDataSources.put(DataSourceEnum.READ.getValue(), readDataSource);
        // 添加数据源
        multipleDataSource.setTargetDataSources(targetDataSources);
        // 设置默认数据源
        multipleDataSource.setDefaultTargetDataSource(writeDataSource);
        return multipleDataSource;
    }

    /**
     * mybatis-plus SQL执行效率插件[生产环境可以关闭]
     * 设置 dev test 环境开启
     */
    @Bean
    @Profile({"dev", "test"})
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor().setFormat(true).setMaxTime(100);
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor().setDialectType(DBType.MYSQL.getDb()).setLocalPage(true);
    }

    /**
     * 乐观锁
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory(PaginationInterceptor paginationInterceptor,
                                                          PerformanceInterceptor performanceInterceptor,
                                                          OptimisticLockerInterceptor optimisticLockerInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource(readDataSource(), writeDataSource()));
//        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*/*Mapper.xml"));

        MybatisConfiguration configuration = new MybatisConfiguration();
        //configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setPlugins(
                new Interceptor[]{
                        paginationInterceptor,
                        optimisticLockerInterceptor,
                        performanceInterceptor,
                        new DataSourceInterceptor()}
        );
        return sqlSessionFactory;
    }


}
