package kang.tableorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TableOrderApplication {
    
    public static void main(String[] args) {
        
        SpringApplication.run(TableOrderApplication.class, args);
    }
    
}
