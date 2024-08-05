package kang.tableorder.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final StringRedisTemplate stringRedisTemplate;
  private final RedisTemplate<String, Object> objectRedisTemplate;

  public void save(String email, String code) {

    stringRedisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);
  }

  public void save(String email, Object object) {

    objectRedisTemplate.opsForValue().set(email, object, 30, TimeUnit.MINUTES);
  }

  public String getString(String key) {

    return stringRedisTemplate.opsForValue().get(key);
  }

  public Object getObject(String key) {

    return objectRedisTemplate.opsForValue().get(key);
  }
}