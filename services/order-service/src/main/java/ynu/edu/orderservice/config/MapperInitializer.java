package ynu.edu.orderservice.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Mapper初始化器
 */
@Component
public class MapperInitializer implements CommandLineRunner {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    
    @Override
    public void run(String... args) throws Exception {
        // 手动注册Mapper接口
        try {
            // 获取所有标记了@Mapper的接口
            String[] mapperBeans = applicationContext.getBeanNamesForAnnotation(Mapper.class);
            System.out.println("Found " + mapperBeans.length + " mapper beans:");
            for (String beanName : mapperBeans) {
                System.out.println("  - " + beanName);
            }
        } catch (Exception e) {
            System.err.println("Error initializing mappers: " + e.getMessage());
        }
    }
}
