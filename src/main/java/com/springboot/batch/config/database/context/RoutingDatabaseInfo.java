package com.springboot.batch.config.database.context;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Data
@Getter
@Component
public class RoutingDatabaseInfo {

    private String jdbcUrl;
    private String driverClassName;
    private String username;
    private String password;

    public void RoutingDatabaseInfo(String jdbcUrl, String driverClassName
            , String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.driverClassName = driverClassName;
        this.username = username;
        this.password = password;
    }
}
