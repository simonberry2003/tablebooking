package com.stb.mybooking.job;

import org.quartz.Job;
import org.quartz.JobDataMap;

public interface DataJob extends Job {
	public JobDataMap getData();
}
