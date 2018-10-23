package com.thanhpl.quartz.scheduler;

import java.io.IOException;
import java.util.Properties;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.thanhpl.quartz.scheduler.job.DummyJob;
import com.thanhpl.quartz.scheduler.job.HelloJob;

@Configuration
public class SchedulerConfig {
	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		SpringJobFactory jobFactory = new SpringJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory,
			@Qualifier("simpleJobTrigger") Trigger simpleJobTrigger,
			@Qualifier("dummyJobTrigger") Trigger dummyJobTrigger) throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setJobFactory(jobFactory);
		factory.setQuartzProperties(quartzProperties());
		factory.setTriggers(simpleJobTrigger, dummyJobTrigger);
		System.out.println("Starting jobs....");
		return factory;
	}

	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	@Bean
	public SimpleTriggerFactoryBean simpleJobTrigger(@Qualifier("helloJobDetail") JobDetail jobDetail,
			@Value("${job.hello.frequency}") Long frequency) {
		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setStartDelay(0L);
		factoryBean.setRepeatInterval(frequency);
		System.out.println("job.hello.frequency=" + frequency);
		factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		return factoryBean;
	}

	@Bean
	public SimpleTriggerFactoryBean dummyJobTrigger(@Qualifier("dummyJobDetail") JobDetail jobDetail,
			@Value("${job.dummy.frequency}") long frequency) {
		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setStartDelay(0L);
		factoryBean.setRepeatInterval(frequency);
		System.out.println("job.dummy.frequency=" + frequency);
		factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		return factoryBean;
	}

	@Bean
	public JobDetailFactoryBean helloJobDetail() {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(HelloJob.class);
		factoryBean.setDurability(true);
		return factoryBean;
	}

	@Bean
	public JobDetailFactoryBean dummyJobDetail() {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(DummyJob.class);
		factoryBean.setDurability(true);
		return factoryBean;
	}
}
