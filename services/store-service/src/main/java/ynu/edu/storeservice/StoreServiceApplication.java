package ynu.edu.storeservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("ynu.edu.storeservice.mapper")
public class StoreServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreServiceApplication.class, args);
    }
}
