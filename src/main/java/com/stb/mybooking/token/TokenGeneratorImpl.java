package com.stb.mybooking.token;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class TokenGeneratorImpl implements TokenGenerator {

	@Override
	public String Generate() {
		return new ObjectId().toString();
	}
}
