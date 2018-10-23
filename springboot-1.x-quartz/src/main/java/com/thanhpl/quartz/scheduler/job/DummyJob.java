package com.thanhpl.quartz.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.thanhpl.quartz.service.DummyService;

public class DummyJob implements Job {

	@Autowired
	private DummyService dummyService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// Test autowired object in Quartz job
		dummyService.process();
	}

}
