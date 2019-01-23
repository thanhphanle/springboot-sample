package com.thanhpl.redis.repository;

import com.thanhpl.redis.model.RedisUser;

public interface RedisUserRepository {

	void add(RedisUser user);

	void delete(String id);

	RedisUser findOne(String id);

}
