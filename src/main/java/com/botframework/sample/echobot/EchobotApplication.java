package com.botframework.sample.echobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 간단한 에코 봇.
 * <p>
 * 스프링 부트로 생성.
 * </p>
 */
@SpringBootApplication
public class EchobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchobotApplication.class, args);
    }
}
