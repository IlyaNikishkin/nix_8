package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class WebJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebJpaApplication.class, args);
    }

}