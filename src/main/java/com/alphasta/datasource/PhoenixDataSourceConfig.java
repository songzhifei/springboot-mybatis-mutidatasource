package com.alphasta.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by summer on 2016/11/25.
 */
@Configuration
@MapperScan(basePackages = "com.alphasta.mapper.phoenix", sqlSessionTemplateRef  = "phoenixSqlSessionTemplate")
public class PhoenixDataSourceConfig {

    @Bean(name = "phoenixDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.phoenix")
    public DataSource phoenixDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "phoenixSqlSessionFactory")
    @Primary
    public SqlSessionFactory phoenixSqlSessionFactory(@Qualifier("phoenixDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "phoenixTransactionManager")
    @Primary
    public DataSourceTransactionManager phoenixTransactionManager(@Qualifier("phoenixDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "phoenixSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate phoenixSqlSessionTemplate(@Qualifier("phoenixSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
