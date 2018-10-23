package com.thanhpl.quartz.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.thanhpl.quartz.scheduler.job.DummyJob;
import com.thanhpl.quartz.scheduler.job.DummyJobBean;

@Configuration
@ComponentScan("com.thanhpl.service")
public class SchedulerConfig {

	@Value("${job.dummy.interval}")
	private int dummyInterval;

	@Bean
	public JobDetail sampleJobDetail() {
		return JobBuilder.newJob(DummyJobBean.class).withIdentity("sampleJob").usingJobData("name", "DummyJobBean")
				.storeDurably().build();
	}

	@Bean
	public Trigger sampleJobTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(dummyInterval).repeatForever();
		return TriggerBuilder.newTrigger().forJob(sampleJobDetail()).withIdentity("sampleTrigger")
				.withSchedule(scheduleBuilder).build();
	}

	@Bean
	public JobDetail dummyJobDetail() {
		return JobBuilder.newJob(DummyJob.class).withIdentity("dummyJob").storeDurably().build();
	}

	@Bean
	public Trigger dummyJobTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(dummyInterval).repeatForever();
		return TriggerBuilder.newTrigger().forJob(dummyJobDetail()).withIdentity("dummyTrigger")
				.withSchedule(scheduleBuilder).build();
	}
}
