package net.busonline.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.busonline.api.dao.SignMapper;
import net.busonline.api.service.InitCacheService;
import net.busonline.core.redis.RedisServiceImpl;
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
	public static   Logger logger = LoggerFactory.getLogger(InitCacheServiceImpl.class);
	@Autowired
	private SignMapper signMapper;
	@Autowired
	private JedisPool jedisPool; 
	
	@Override
	public void getAllSign() {
		try{
			
		// TODO Auto-generated method stub
	List<Map<String, String>>list =signMapper.getAllSign();
	 logger.info("数据库中签名信息的list集合===="+list);
	 if(net.busonline.core.util.PubMethod.isEmpty(list)){
			logger.info("数据为空，sql或者数据数据有问题");
		}else{
			logger.info("开始循环遍历数据，准备往redis中扔数据,结构为map集合");
			for(int i = 0 ;i<list.size();i++){
				Map<String,String>map = list.get(i);
				//存放的格式
				logger.info("存放的数据"+map);
//				String s = hvals(0, "beijing1111_1");
//				System.out.println(s);
//				Map<String,String>map2 = new HashMap<String,String>();
//				map2.put("fdsf", "sdfsd");
				this.set(2, "map2", map);
			System.out.println(this.get(2, "map2"));
			}
		 
			logger.info("扔数据结束");
		}
 
		}catch(Exception e){
			logger.debug("异常开始"+InitCacheServiceImpl.class+"====>"+e);
		}
	}
	
	public String hvals(int dbIndex, String key) {
		String value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(dbIndex);
			List<String> d = jedis.hvals(key);
			value = d.toString();
//			value = jedis.get(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	public String hset(int dbIndex, String key, String field, String value) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(dbIndex);
			jedis.hset(key, field, value);
		} finally {
			jedisPool.returnResource(jedis);

		}
		return value;
	}
	
	public void set(int dbIndex, String key, Map<String,String>hash) {
	List<String> vals = null;
	Jedis jedis = jedisPool.getResource();
	try {
		jedis.select(dbIndex);
		 jedis.hmset(key, hash);
	} finally {
		jedisPool.returnResource(jedis);
	}
	 
}
	
	public String get(int dbIndex, String key) {
		List<String> vals = null;
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(dbIndex);
		 
			vals = jedis.hvals(key);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return vals.toString();
	}
}
