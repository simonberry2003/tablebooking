package com.stb.mybooking.logging;

import org.slf4j.Logger;

public interface LogFactory {
	Logger create(Object instance);
}
