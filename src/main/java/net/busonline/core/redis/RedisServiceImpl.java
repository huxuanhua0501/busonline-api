package net.busonline.core.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSON;

public class RedisServiceImpl{

	private static Logger logger = Logger.getLogger(RedisServiceImpl.class);

	@Autowired
	private StringRedisTemplate redisTemplate;
//	@Autowired
//	private RedisConstant redisConstant;
	
	 
	public void put(String cacheName, String key, String value) {
//		boolean bool = redisTemplate.hasKey(cacheName);
		if (cacheName==null || "".equals(cacheName) || key==null || "".equals(key)) {
			return;
		}
		//放入redis
		redisTemplate.opsForHash().put(cacheName, key, value);
		
//		long expireTime = redisConstant.getExpireTime(cacheName);
//		//如果不等于-1，则该cacheName配置有过期时间
//		if(expireTime != -1){
//			redisTemplate.expire(cacheName, expireTime, TimeUnit.SECONDS);
//		}
	}

	
	public void put(String cacheName, String key, Object value) {
		if (cacheName==null || "".equals(cacheName) || key==null || "".equals(key)) {
			return;
		}
		//放入redis
		redisTemplate.opsForHash().put(cacheName, key, JSON.toJSONString(value));
		
//		long expireTime = redisConstant.getExpireTime(cacheName);
//		//如果不等于-1，则该cacheName配置有过期时间
//		if(expireTime != -1){
//			redisTemplate.expire(cacheName, expireTime, TimeUnit.SECONDS);
//		}
	}

	
	public <T> T get(String cacheName, String key, Class<T> className) {
		Object obj = redisTemplate.opsForHash().get(cacheName, key);
		if(obj == null){
			return null;
		}
		return JSON.parseObject(""+obj, className);
	}

	
	public boolean getByName(String cacheName) {
		if(cacheName == null || "".equals(cacheName)){
			return false;
		}
		return redisTemplate.hasKey(cacheName);
	}

	
	public boolean getByKey(String cacheName, String key) {
		if(cacheName == null || "".equals(cacheName) || key == null || "".equals(key)){
			return false;
		}
		return redisTemplate.opsForHash().hasKey(cacheName, key);
	}

	
	public <T> List<T> getAll(String cacheName, Class<T> className) {
		
		if(cacheName==null || "".equals(cacheName)){
			return null;
		}
		
		Set<Object> keys = redisTemplate.opsForHash().keys(cacheName);
		
		List<T> datas = new ArrayList<T>();
		
		for (Object obj : keys) {
			datas.add(get(cacheName, obj+"", className));
		}
		if(datas.size() == 0){
			return null;
		}
		
		return datas;
	}

	
	public void remove(String cacheName, String key) {
		if(cacheName == null || "".equals(cacheName) || key == null || "".equals(key)){
			return;
		}
		redisTemplate.opsForHash().delete(cacheName, key);
	}

	
	public void remove(String cacheName, List<String> keys) {
		if(cacheName == null || "".equals(cacheName) || keys == null || keys.size()==0){
			return;
		}
		for (String key : keys) {
			redisTemplate.opsForHash().delete(cacheName, key);
		}
	}

	
	public void removeAll(String cacheName) {
		if(cacheName==null || "".equals(cacheName)){
			return ;
		}
		redisTemplate.delete(cacheName);

	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	public String[] findAllName() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List getValueByKey(String cacheName, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long getKeyList(String cacheName) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String onlyValue(String cacheName, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List getAllKeyAndValue(String cacheName, int numPerPage, int pageNum, Long totalCount) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void lpush(String key, Object obj) {
		// TODO Auto-generated method stub
		if (key==null || "".equals(key)) {
			return;
		}
		//放入redis
		redisTemplate.opsForList().leftPush(key, JSON.toJSONString(obj));
//		long expireTime = redisConstant.getExpireTime("smsRecordCache");
//		//如果不等于-1，则该cacheName配置有过期时间
//		if(expireTime != -1){
//			redisTemplate.expire(key, expireTime, TimeUnit.DAYS);
//		}
	}

	
	public <T> List<T> lpop(String key, Class<T> className) {
		// TODO Auto-generated method stub
		List<T> datas = new ArrayList<T>();
		if(key==null || "".equals(key)){
			return datas;
		}
		List<String> list = redisTemplate.opsForList().range(key, 0, -1);
		for (String str : list) {
			datas.add(JSON.parseObject(str, className));
		}
		if(datas.size() == 0){
			return datas;
		}
		
		return datas;
	}

}
