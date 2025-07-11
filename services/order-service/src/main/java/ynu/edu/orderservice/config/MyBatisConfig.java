package ynu.edu.orderservice.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 原生MyBatis配置类
 * 用于替代MyBatis-Plus以解决Bean定义错误
 */
@Configuration
public class MyBatisConfig {
    
    @Autowired
    private DataSource dataSource;
    
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        
        // 设置实体类别名
        sessionFactory.setTypeAliasesPackage("ynu.edu.orderservice.entity");
        
        // 设置mapper xml文件位置（如果有的话）
        // 使用try-catch来避免找不到XML文件时的错误
        try {
            org.springframework.core.io.Resource[] resources = 
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml");
            if (resources != null && resources.length > 0) {
                sessionFactory.setMapperLocations(resources);
            }
        } catch (Exception e) {
            // 如果没有XML文件，忽略错误，使用注解方式
            System.out.println("No XML mapper files found, using annotation-based mappers only");
        }
        
        return sessionFactory.getObject();
    }
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
