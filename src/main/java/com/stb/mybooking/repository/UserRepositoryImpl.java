package com.stb.mybooking.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import com.stb.mybooking.domain.UserDomainObject;

@Repository
public class UserRepositoryImpl extends SimpleMongoRepository<UserDomainObject, String> implements UserRepository {

	public UserRepositoryImpl(MongoEntityInformation<UserDomainObject, String> metadata, MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
	}
	
	@Autowired
    public UserRepositoryImpl(MongoRepositoryFactory factory, MongoOperations mongoOperations) {
        this(factory.<UserDomainObject, String>getEntityInformation(UserDomainObject.class), mongoOperations);
    }


	@Override
	public UserDomainObject create(UserDomainObject user) {
		try {
			return insert(user);
		} catch (DuplicateKeyException e) {
			return null;
		}
	}

	@Override
	public UserDomainObject getByEmail(String emailAddress) {
		return findOne(emailAddress);
	}

	@Override
	public UserDomainObject update(UserDomainObject user) {
		return save(user);
	}
}
