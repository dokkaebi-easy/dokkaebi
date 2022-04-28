package com.ssafy.dockerby.config;


import com.ssafy.dockerby.util.FileManager;
import java.io.IOException;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfig {

  @Bean
  public DataSource getDataSource() throws IOException {
    DataSourceBuilder builder = DataSourceBuilder.create();
    builder.driverClassName("org.mariadb.jdbc.Driver");
    builder.username("root");
    builder.password(FileManager.loadFile("/home/conf","AuthKey"));
    builder.url("jdbc:mariadb://localhost:3306/dockerby?useSSL=false&serverTimezone=UTC&autoReconnect=true&allowPublicKeyRetrieval=true");
    return builder.build();
  }

}
