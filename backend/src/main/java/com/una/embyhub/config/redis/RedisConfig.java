package com.una.embyhub.config.redis;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
   @Primary
   @Bean
   public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
      RedisTemplate<String, Object> template = new RedisTemplate<>();
      template.setConnectionFactory(factory);
      ObjectMapper om = new ObjectMapper();
      om.findAndRegisterModules();
      om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
      om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, DefaultTyping.NON_FINAL, As.PROPERTY);
      Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(om, Object.class);
      template.setKeySerializer(new StringRedisSerializer());
      template.setHashKeySerializer(new StringRedisSerializer());
      template.setValueSerializer(serializer);
      template.setHashValueSerializer(serializer);
      template.afterPropertiesSet();
      return template;
   }

   @Bean
   public RedisTemplate<String, byte[]> binaryRedisTemplate(RedisConnectionFactory factory) {
      RedisTemplate<String, byte[]> template = new RedisTemplate<>();
      template.setConnectionFactory(factory);
      StringRedisSerializer keySerializer = new StringRedisSerializer();
      RedisSerializer<byte[]> valueSerializer = RedisSerializer.byteArray();
      template.setKeySerializer(keySerializer);
      template.setHashKeySerializer(keySerializer);
      template.setValueSerializer(valueSerializer);
      template.setHashValueSerializer(valueSerializer);
      template.afterPropertiesSet();
      return template;
   }
}
