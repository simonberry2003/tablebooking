package com.stb.mybooking.job;

public interface JobScheduler {
	void enqueue(DataJob job);
}
