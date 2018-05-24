package com.xzj.g01.base.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ebo.storage.redis.JRedis;
import com.xzj.g01.base.dao.redis.base.RedisKeyUtil;
import com.xzj.g01.base.dao.redis.base.StorageKey;

@Repository
public class UserRepo {
	
	@Autowired
	private JRedis jRedis;
	
}
