package com.thanhpl.quartz.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.thanhpl.service.DummyService;


public class DummyJobBean extends QuartzJobBean  {
	
	private String name;

	// Invoked if a Job data map entry with that name
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Autowired
	private DummyService dummyService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		dummyService.process("DummyJobBean");
	}


}
