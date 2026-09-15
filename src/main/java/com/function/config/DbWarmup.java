package com.function.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DbWarmup {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {

        try (Connection con = dataSource.getConnection()) {

            System.out.println(
                    "Database Warmup Successful");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
