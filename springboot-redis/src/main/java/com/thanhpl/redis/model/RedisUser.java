package com.thanhpl.redis.model;

import java.io.Serializable;

public class RedisUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5947239638691787387L;
	private String id;
	private String username;
	private String accessToken;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getId() {
		return id;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@Override
	public String toString() {
		return "id=" + id + ",username=" + username + ",accessToken=" + accessToken;
	}
}
