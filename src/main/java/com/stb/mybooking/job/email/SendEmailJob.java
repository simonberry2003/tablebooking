package com.stb.mybooking.job.email;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.common.base.Preconditions;
import com.stb.mybooking.job.DataJob;

public class SendEmailJob implements DataJob {

	private String recipient;
	
	public void setRecipient(String recipient) {
		this.recipient = Preconditions.checkNotNull(recipient);
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String s = recipient;
	}

	@Override
	public JobDataMap getData() {
		JobDataMap map = new JobDataMap();
		map.put("recipient", Preconditions.checkNotNull(recipient));
		return map;
	}
}
