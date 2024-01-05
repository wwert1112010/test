package com.qj.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

@SpringBootApplication(scanBasePackages = {"com.qj.websocket"})
public class WebsocketApplication {



    public static void main(String[] args) {
        SpringApplication.run(WebsocketApplication.class, args);
    }

}
