package de.eldecker.spring.linkbaum.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Konfiguration für Redis-Client/Treiber, nach
 * <a href="https://www.baeldung.com/spring-data-redis-tutorial#1-java-configuration">diesem Tutorial</a>.
 */
@Configuration
public class RedisKonfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        
        return new JedisConnectionFactory();
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory( jedisConnectionFactory() );
        
        return template;
    }
    
}
