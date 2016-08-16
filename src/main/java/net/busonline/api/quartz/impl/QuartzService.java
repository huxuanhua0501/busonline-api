package net.busonline.api.quartz.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.busonline.api.dao.SignMapper;
import net.busonline.api.quartz.IQuartzService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class QuartzService implements IQuartzService {
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private SignMapper signMapper;

	//@Scheduled(cron = "0/1 * *  * * ?") // 每5秒执行一次
	
	@Scheduled(cron = "0 40 14 * * ?")
	public void stoptask() {
		List<Map<String,String>> list =signMapper.startingStop();
		//this.lpush(0, "GPS", "Y4KLByH0kXgjANdD-tXetg.. 0 39.94272772845409 116.43647494497634 0.0 1470623976000");
		for(int i = 0 ;i<list.size();i++){
			//JSON.toJSONString(list.get(i));
			//System.out.println(JSON.toJSONString(list.get(i)));
			this.seth(2, "stoptask", list.get(i).get("lineid")+"_"+list.get(i).get("linedir"), JSON.toJSONString(list.get(i)));
		}
		//System.out.println("test executed ...");
		List<String> lists = this.hget(2, "stoptask", "668_0");
		for(int i  = 0 ;i<lists.size() ;i++){
			//System.out.println(lists.get(i));
		}
	}

	public void seth(int dbIndex, String key, String field, String value) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(dbIndex);
			jedis.hset(key, field, value);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	public void lpush(int dbIndex, String key,  String value) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.select(dbIndex);
			jedis.lpush(key, value);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	public List<String> hget(int dbIndex, String key, String sign) {

		Jedis jedis = jedisPool.getResource();
		List<String> d = null;
		try {
			jedis.select(dbIndex);
			d = jedis.hmget(key, sign);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return d;
	}
}
