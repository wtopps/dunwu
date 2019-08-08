package io.github.dunwu.quickstart;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc
@SpringBootApplication(scanBasePackages = "io.github.dunwu")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
