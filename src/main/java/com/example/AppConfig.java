package com.example;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

  @Autowired
  DataSourceProperties properties;
  DataSource dataSource;

  @ConfigurationProperties(prefix = DataSourceAutoConfiguration.CONFIGURATION_PREFIX)
  @Bean(destroyMethod = "close")
  DataSource realDataSource() {
    this.dataSource = DataSourceBuilder
                        .create(this.properties.getClassLoader())
                        .url(this.properties.getUrl())
                        .username(this.properties.getUsername())
                        .password(this.properties.getPassword())
                        .build();
    return this.dataSource;
  }

  @Bean
  DataSource dataSource() {
    return new Log4jdbcProxyDataSource(this.dataSource);
  }

}