package icu.pymiliblog.teachingmanagementplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("icu.pymiliblog.teachingmanagementplatform")
public class TeachingManagementPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachingManagementPlatformApplication.class, args);
    }

}
