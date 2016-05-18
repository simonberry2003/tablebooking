package com.stb.mybooking.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
public class Slf4jLogFactory implements LogFactory {

	@Override
	public Logger create(Object instance) {
		Preconditions.checkNotNull(instance);
		return LoggerFactory.getLogger(instance.getClass());
	}
}
