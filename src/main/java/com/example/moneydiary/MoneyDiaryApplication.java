package com.example.moneydiary;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.Collections;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@RestController
public class MoneyDiaryApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(MoneyDiaryApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${money-diary.version}") String appVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(
                        new Info()
                                .title("Money diary API")
                                .version(appVersion)
                                .description("Money diary that allows you to track expenses.")
                );
    }
}
