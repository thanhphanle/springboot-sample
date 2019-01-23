package com.thanhpl.redis.request;

public class SaveUserRequest {

	private String id;
	private String username;
	private String accessToken;
	public String getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
}
