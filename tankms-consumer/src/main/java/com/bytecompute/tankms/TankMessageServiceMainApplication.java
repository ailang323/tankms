package com.bytecompute.tankms;

import com.bytecompute.tankms.annotation.EnableTankMessageServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Longer on 2016/11/17.
 */
@EnableTankMessageServiceConfig
@SpringBootApplication
@ImportResource({"classpath*:META-INF/applicationContext.xml"})
public class TankMessageServiceMainApplication {

    /**
     * mms consumer main application
     *
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TankMessageServiceMainApplication.class, args);
    }
}
