package com.awe.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * description
 *
 * @author wangy QQ 837195190
 * <p>Created by admin on 2018/7/19.</p>
 */
@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
@MapperScan("com.awe.web.mapper")
public class MybatisPlusConfig {

    private static final Logger logger = LoggerFactory.getLogger(MybatisProperties.class);

    @Autowired
    private Environment environment;

    @Autowired
    private MybatisProperties properties;

    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Autowired(required = false)
    private Interceptor[] interceptors;

    @Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;

    @Autowired
    private DataSource dataSource;

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * 配置DataSource
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        try (DruidDataSource druidDataSource = new DruidDataSource()) {
            druidDataSource.setUrl(environment.getProperty("spring.datasource.url"));
            druidDataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
            druidDataSource.setUsername(environment.getProperty("spring.datasource.username"));
            druidDataSource.setPassword(environment.getProperty("spring.datasource.password"));
            druidDataSource.setInitialSize(Integer.valueOf(environment.getProperty("spring.datasource.initial-size")));
            druidDataSource.setMinIdle(Integer.valueOf(environment.getProperty("spring.datasource.min-idle")));
            druidDataSource.setMaxWait(Long.valueOf(environment.getProperty("spring.datasource.max-wait")));
            druidDataSource.setMaxActive(Integer.valueOf(environment.getProperty("spring.datasource.max-active")));
            druidDataSource.setMinEvictableIdleTimeMillis(Long.valueOf(environment.getProperty("spring.datasource.min-evictable-idle-time-millis")));
            try {
                druidDataSource.setFilters(environment.getProperty("spring.datasource.filters"));
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
            return druidDataSource;
        }
    }

    /**
     * 配置事物管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }

    /**
     * 这里全部使用mybatis-autoconfigure 已经自动加载的资源。不手动指定
     * 配置文件和mybatis-boot的配置文件同步
     */
    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        mybatisPlus.setDataSource(dataSource);
        mybatisPlus.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(this.properties.getConfigLocation())) {
            mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
        }
        mybatisPlus.setConfiguration(properties.getConfiguration());
        if (!ObjectUtils.isEmpty(this.interceptors)) {
            mybatisPlus.setPlugins(this.interceptors);
        }
        // MP 全局配置，更多内容进入类看注释
        GlobalConfiguration globalConfig = new GlobalConfiguration();
        globalConfig.setDbType(DBType.MYSQL.name());
        // ID 策略 AUTO->`0`("数据库ID自增") INPUT->`1`(用户输入ID") ID_WORKER->`2`("全局唯一ID") UUID->`3`("全局唯一ID")
        globalConfig.setIdType(2);
        mybatisPlus.setGlobalConfig(globalConfig);
        MybatisConfiguration mc = new MybatisConfiguration();
        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        mybatisPlus.setConfiguration(mc);
        if (this.databaseIdProvider != null) {
            mybatisPlus.setDatabaseIdProvider(this.databaseIdProvider);
        }
        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
            mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
        }
        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
            mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
            mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
        }
        return mybatisPlus;
    }


    /**
     * 注册一个StatViewServlet
     */
//    @Bean
//    public ServletRegistrationBean DruidStatViewServlet(){
//        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
//        //添加初始化参数：initParams
//        //白名单：
//        // servletRegistrationBean.addInitParameter("allow","127.0.0.1");
//        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
//        // servletRegistrationBean.addInitParameter("deny","192.168.1.73");
//        //登录查看信息的账号密码.
//        servletRegistrationBean.addInitParameter("loginUsername","root");
//        servletRegistrationBean.addInitParameter("loginPassword","root");
//        //是否能够重置数据.
//        servletRegistrationBean.addInitParameter("resetEnable","false");
//        return servletRegistrationBean;
//    }


    /**
     * 注册一个：filterRegistrationBean
     */
//    @Bean
//    public FilterRegistrationBean druidStatFilter() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//        //添加过滤规则.
//        filterRegistrationBean.addUrlPatterns("/*");
//        //添加不需要忽略的格式信息.
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        return filterRegistrationBean;
//    }

    @WebServlet(urlPatterns = "/druid/*", initParams = {
            @WebInitParam(name = "loginUsername", value = "root"),
            @WebInitParam(name = "loginPassword", value = "root"),
            @WebInitParam(name = "resetEnable", value = "false")
    })
    public class DruidStatViewServlet extends StatViewServlet {
    }

    @WebFilter(filterName = "druidStatFilter", urlPatterns = "/*",
            initParams = {@WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*")})
    public class DruidStatFilter extends WebStatFilter {
    }
}
