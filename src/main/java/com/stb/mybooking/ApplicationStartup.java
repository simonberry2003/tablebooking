package com.stb.mybooking;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	/**
     * This method is called during Spring's startup.
     * @param event Event raised when an ApplicationContext gets initialized or refreshed.
     */
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		Scheduler scheduler;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
}
