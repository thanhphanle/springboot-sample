package com.thanhpl.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thanhpl.redis.model.RedisUser;
import com.thanhpl.redis.repository.RedisUserRepository;
import com.thanhpl.redis.request.GetUserRequest;
import com.thanhpl.redis.request.SaveUserRequest;

@RestController
@RequestMapping({ "/redis" })
public class RedisController {
	
	@Autowired
	private RedisUserRepository redisUserRepo;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody SaveUserRequest request) {
		RedisUser redisUser = new RedisUser();
		redisUser.setId(request.getId());
		redisUser.setUsername(request.getUsername());
		redisUser.setAccessToken(request.getAccessToken());
		
		redisUserRepo.add(redisUser);
		
		return ResponseEntity.ok("Success");
	}
	
	@PostMapping("/get")
	public ResponseEntity<String> get(@RequestBody GetUserRequest request) {
		RedisUser redisUser = redisUserRepo.findOne(request.getId());
		if (redisUser == null) {
			return ResponseEntity.ok("Not found");
		}
		return ResponseEntity.ok(redisUser.toString());
	}
}
