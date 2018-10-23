package com.thanhpl.service.impl;

import org.springframework.stereotype.Service;

import com.thanhpl.service.DummyService;

@Service
public class DummyServiceImpl implements DummyService {

	@Override
	public void process(String source) {
		System.out.println("Process dummy service from " + source);
	}

}
