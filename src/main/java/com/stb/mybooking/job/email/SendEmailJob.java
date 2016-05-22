package com.stb.mybooking.job.email;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.google.common.base.Preconditions;
import com.stb.mybooking.job.DataJob;

public class SendEmailJob implements DataJob {

	private JavaMailSender javaMailSender;
	private SimpleMailMessage message;
	
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = Preconditions.checkNotNull(javaMailSender);
	}

	public void setMessage(SimpleMailMessage message) {
		this.message = Preconditions.checkNotNull(message);
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        javaMailSender.send(message);
	}

	@Override
	public JobDataMap getData() {
		JobDataMap map = new JobDataMap();
		map.put("javaMailSender", Preconditions.checkNotNull(javaMailSender));
		map.put("message", Preconditions.checkNotNull(message));
		return map;
	}
}
