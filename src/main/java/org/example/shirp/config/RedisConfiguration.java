package org.example.shirp.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/12
 **/
@Configuration
public class RedisConfiguration {

  @Bean
  public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());

    // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
    ObjectMapper objectMapper = new ObjectMapper();
    // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
    objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

    //us加了这句话会 出UnrecognizedPropertyException: Unrecognized field "valid"
    //redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    redisTemplate.setConnectionFactory(lettuceConnectionFactory);
    return redisTemplate;
  }

}
