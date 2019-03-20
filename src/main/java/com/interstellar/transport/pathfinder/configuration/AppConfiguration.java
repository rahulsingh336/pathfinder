package com.interstellar.transport.pathfinder.configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Created by E076103 on 12-03-2019.
 */
@Configuration
public class AppConfiguration {

  @Bean
  public DataSource dataSource() {

    // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    EmbeddedDatabase db = builder
        .setType(EmbeddedDatabaseType.DERBY) //.H2 or .DERBY
        .build();
    return db;
  }

}
