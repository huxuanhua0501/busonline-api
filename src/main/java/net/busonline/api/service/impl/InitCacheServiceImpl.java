package net.busonline.api.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.busonline.api.dao.SignMapper;
import net.busonline.api.service.InitCacheService;
import net.busonline.core.cache.EhcacheListener;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("initCacheService")
public class InitCacheServiceImpl implements InitCacheService {
//	public static   Logger logger = LoggerFactory.getLogger(EhcacheListener.class);
	@Autowired
	private SignMapper signMapper;
	@Autowired
	private JedisPool jedisPool; 

	@Override
	public List<Map<String, Object>> getAllSign() {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		try {
		 
//				jedis.select(0);
//				jedis.set("dddd", "nimeidefadfa");
			jedis.select(0);
			String value = jedis.get("dddd");
			System.out.println(value);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return signMapper.getAllSign();
	}

}
