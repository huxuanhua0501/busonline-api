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
/**
 * 预加载所有sign(签名线路)线路
 * 
 * @author xuanhua.hu
 *
 */
@Service("initCacheService")
public class InitCacheServiceImpl implements InitCacheService {
//	public static   Logger logger = LoggerFactory.getLogger(EhcacheListener.class);
	@Autowired
	private SignMapper signMapper;
//	@Autowired
//	private JedisPool jedisPool; 

	@Override
	public List<Map<String, Object>> getAllSign() {
		// TODO Auto-generated method stub
 
		return signMapper.getAllSign();
	}

}
