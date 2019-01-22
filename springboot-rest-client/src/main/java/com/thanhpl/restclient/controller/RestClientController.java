package com.thanhpl.restclient.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.thanhpl.restclient.dto.Dummy;
import com.thanhpl.restclient.dto.User;

@RestController
@RequestMapping({ "/restclient" })
public class RestClientController {

	/*
	 * GET Content-Type: application/json
	 */
	@GetMapping("/get")
	public ResponseEntity<String> get() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/example";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		return response;
	}

	/*
	 * GET Content-Type: application/json
	 */
	@GetMapping("/getForObject")
	public ResponseEntity<String> getForObject() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/example";
		Dummy dummy = restTemplate.getForObject(url, Dummy.class);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/*
	 * POST application/x-www-form-urlencoded
	 * 
	 * { "username": "thanhpl", "password": "abcd" }
	 */
	@PostMapping("/postform")
	public ResponseEntity<String> postFormUrlencoded(@RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("username", user.getUsername());
		map.add("password", user.getPassword());
		map.add("client_id", "demo-client");
		map.add("client_secret", "54a8c52e-1b9a-4ccf-9b4f-98eb086b9196");
		map.add("grant_type", "password");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		String url = "http://localhost:8080/auth/realms/master/protocol/openid-connect/token";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		return response;
	}
}
