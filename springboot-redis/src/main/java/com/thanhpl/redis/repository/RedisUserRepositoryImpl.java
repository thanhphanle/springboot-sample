package com.thanhpl.redis.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.thanhpl.redis.model.RedisUser;

@Repository
public class RedisUserRepositoryImpl implements RedisUserRepository {
private static final String KEY = "RedisUser";
	
	private RedisTemplate<String, Object> redisTemplate;
	
    private HashOperations<String, String, Object> hashOperations;
    
    @Autowired
    public RedisUserRepositoryImpl(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }
    
	@Override
	public void add(RedisUser user) {
		hashOperations.put(KEY, user.getId(), user);
	}

	@Override
	public void delete(String id) {
		 hashOperations.delete(KEY, id);
	}

	@Override
	public RedisUser findOne(String id) {
		return (RedisUser) hashOperations.get(KEY, id);
	}
}
