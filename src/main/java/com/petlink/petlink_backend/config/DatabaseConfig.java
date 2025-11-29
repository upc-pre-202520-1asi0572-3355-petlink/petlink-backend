package com.petlink.petlink_backend.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        // Si DATABASE_URL existe y comienza con "postgresql://", convertirla a "jdbc:postgresql://"
        if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            databaseUrl = "jdbc:" + databaseUrl;
        } else if (databaseUrl == null) {
            // Fallback a la configuración local
            databaseUrl = "jdbc:postgresql://localhost:5432/petlinkdb";
        }
        
        return DataSourceBuilder
                .create()
                .url(databaseUrl)
                .build();
    }
}
