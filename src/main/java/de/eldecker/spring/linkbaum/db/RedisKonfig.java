package de.eldecker.spring.linkbaum.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * Konfiguration für Redis-Client/Treiber, nach
 * <a href="https://www.baeldung.com/spring-data-redis-tutorial#1-java-configuration">diesem Tutorial</a>.
 */
@Configuration
@EnableRedisRepositories
public class RedisKonfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        
        return new JedisConnectionFactory();
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory( jedisConnectionFactory() );                
        template.setDefaultSerializer( StringRedisSerializer.UTF_8 );

        return template;
    }
    
}
