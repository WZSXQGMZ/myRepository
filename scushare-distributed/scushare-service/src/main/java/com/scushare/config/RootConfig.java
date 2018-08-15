package com.scushare.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableTransactionManagement
@MapperScan("com.scushare.mapper")
@PropertySource("classpath:application.properties")
public class RootConfig {
	@Value("${spring.datasource.driverClassName}")    
    private String driverClass;    

    @Value("${spring.datasource.url}")    
    private String url;    

    @Value("${spring.datasource.username}")    
    private String username;    

    @Value("${spring.datasource.password}")    
    private String password;    

    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    //注册druid数据源
    @Bean(name = "dataSource")
    public DataSource dataSource() {    
    	DruidDataSource dataSource = new DruidDataSource();    
        dataSource.setDriverClassName(driverClass);    
        dataSource.setUrl(url);    
        dataSource.setUsername(username);    
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        return dataSource;    
    }
    
    //注册session工厂
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
    	SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    	sessionFactory.setDataSource(dataSource());
    	return sessionFactory.getObject();
    }

    //注册事务管理器
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() throws Exception{
    	DataSourceTransactionManager manager = new DataSourceTransactionManager();
    	manager.setDataSource(dataSource());
    	return manager;
    }
    
}
