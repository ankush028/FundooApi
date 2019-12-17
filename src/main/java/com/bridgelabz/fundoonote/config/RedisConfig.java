package com.bridgelabz.fundoonote.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;


@EnableRedisRepositories
@Configuration
public class RedisConfig {


	 @Value("${spring.redis.host}")    
	   private String REDIS_HOSTNAME;    
	   @Value("${spring.redis.port}")
	   private int REDIS_PORT;

	   @Bean
		public JedisConnectionFactory jedisConnectionFactory() {
			RedisProperties properties = redisProperties();
			RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
			configuration.setHostName(properties.getHost());
			configuration.setPort(properties.getPort());
		
			JedisConnectionFactory factoryObject = new JedisConnectionFactory(configuration);
			factoryObject.getPoolConfig().setMaxIdle(30);
			factoryObject.getPoolConfig().setMinIdle(10);
			return factoryObject;
		}

	
		@Bean
		public RedisTemplate<String, Object> redisTemplate() {
			final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
			template.setConnectionFactory(jedisConnectionFactory());
			template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
			return template;
		}

		@Bean
		@Primary
		public RedisProperties redisProperties() {
			return new RedisProperties();
		}


	
}
