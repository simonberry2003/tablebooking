package com.stb.mybooking.job;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

@Component
public class JobSchedulerImpl implements JobScheduler {

	private final Scheduler scheduler;

	public JobSchedulerImpl() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void enqueue(DataJob job) {
		
		Trigger trigger = TriggerBuilder
			.newTrigger()
		    .withIdentity("trigger1", "group1")
		    .startNow()
		    .build();
		
		JobDetail jobDetail = JobBuilder
			.newJob(job.getClass())
			.usingJobData(job.getData())
			.build();
		
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
}
