package net.busonline.core.cache;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.busonline.api.service.InitCacheService;
import net.busonline.core.redis.RedisServiceImpl;

public class EhcacheListener implements ServletContextListener {
	 
	private RedisServiceImpl redisService;
	// 获取spring注入的bean对象
	private WebApplicationContext springContext;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		redisService = (RedisServiceImpl) springContext.getBean("redisService");
		List<Map<String,Object>> list = ((InitCacheService) springContext.getBean("initCacheService")).getAllSign();
		for(int i = 0 ;i<list.size();i++){
			Map<String,Object>map = list.get(i);
			redisService.put("sign", map.get("sign").toString(), map);
		}

	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}


}
