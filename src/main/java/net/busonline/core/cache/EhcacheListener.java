package net.busonline.core.cache;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.busonline.api.service.InitCacheService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * 预加载签名信息,将信息存放在redis中
 * @version 1.0.0
 * @2016-7-23
 * @author xuanhua.hu
 *
 */
public class EhcacheListener implements ServletContextListener {
	//cache层
//	private RedisServiceImpl redisService;
	@Autowired
	private JedisPool jedisPool;
	// 获取spring注入的bean对象
	private WebApplicationContext springContext;
	public static   Logger logger = LoggerFactory.getLogger(EhcacheListener.class);
	
	/**
	 * 预加载签名信息，为企业调取接口时需要先验证这些签名信息
	 *  需要提供sign(32,md5加密),目前只有百度
	 * @version 1.0.0
	 * @2016-7-23
	 * @author xuanhua.hu
	 *
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		try{
			
	
		logger.info("缓存企业签名信息方法开始");
		springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		//redisService = (RedisServiceImpl) springContext.getBean("redisService");
 
//		List<Map> List = (List<Map>) redisService.getAll("beijing_4", Map.class);
//		 Map<String,Object> map1 = (Map<String,Object>) redisService.get
//		jedisManager.get
		//String str = jedisManager.get(0,"beijing_4");
		//System.out.println(str);
		
		List<Map<String,Object>> list = ((InitCacheService) springContext.getBean("initCacheService")).getAllSign();
		logger.info("数据库中签名信息的list集合===="+list);
		if(net.busonline.core.util.PubMethod.isEmpty(list)){
			logger.info("数据为空，sql或者数据数据有问题");
		}else{
			logger.info("开始循环遍历数据，准备往redis中扔数据,结构为map集合");
			for(int i = 0 ;i<list.size();i++){
				Map<String,Object>map = list.get(i);
				//存放的格式
				logger.info("存放的数据"+map);
				//redisService.put("sign", map.get("sign").toString(), map);
			 
			}
			logger.info("扔数据结束");
		}
	}catch(Exception e){
			logger.debug("异常开始====", e);
		}
		
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void set(int dbIndex, String key,String field, String value) {
	 
	Jedis jedis = jedisPool.getResource();
	try {
		jedis.select(dbIndex);
		 jedis.set("1231", "qeq");
//		jedis.hmset("", hash)
	} finally {
		jedisPool.returnResource(jedis);
	}
	 
}
}
