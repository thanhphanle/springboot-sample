package com.thanhpl.quartz.service.impl;

import org.springframework.stereotype.Service;

import com.thanhpl.quartz.service.DummyService;

@Service
public class DummyServiceImpl implements DummyService {

	@Override
	public void process() {
		System.out.println("Process dummy service...");
	}
}
