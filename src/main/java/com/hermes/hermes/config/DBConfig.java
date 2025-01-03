package com.hermes.hermes.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/config.properties")
public class DBConfig {
    @Autowired
    private ApplicationContext applicationContext; // 현재 프로젝트

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        // 필수 설정값들을 직접 지정
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/hermesDB");
        config.setUsername("hermesadmin");
        config.setPassword("1234");
        return config;
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**.xml"));
        sessionFactoryBean.setTypeAliasesPackage("com.hermes.hermes.dto");
        sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));

        return sessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) {
        return new SqlSessionTemplate(factory);
    }

    @Bean
    public DataSourceTransactionManager
    dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}