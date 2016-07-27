package net.busonline.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.busonline.api.service.IBusApiService;
import net.busonline.core.base.BaseController;
import net.busonline.core.cache.EhcacheListener;
/**
 * 百度对接的三个接口，详细busonline-api\word，百度提供的文档
 * @author xuanhua.hu
 *
 */
@RestController
@RequestMapping("/busapi")
public class BusApiController{
	public static   Logger logger = LoggerFactory.getLogger(BusApiController.class);
	@Autowired
	private IBusApiService busApiService;

	/**接口，通过调取城市名字，进而返回跟百度合作的线路调取出来(频率一天一次)
	 *
	 *这里主要是业务逻辑实现部分
	 * @param city 百度过来的参数，如city=beijing
	 * @return
	 * 返回格式如下：
	 * ｛
		"state": "0"，
		"sendTime": "2015-8-27 17:42:55"，
		"data": [
		        {
		                "dplLine": "133333",
						"name": "M343"
		        },
		        {
		                "dplLine": "B669",
						"name": " B669"
		
		        },
		{
		                "dplLine": "353",
						"name": "353"
		
		        }
		]
		｝
	 */
	@RequestMapping(value = "city", method = { RequestMethod.POST, RequestMethod.GET })
	public String city(String city,String sign) {
		logger.info("通过调取城市名字，进而返回跟百度合作的线路调取出来，接口进入city="+city);
		return  busApiService.city(city,sign);
	}
/**
 * 通过城市和线路id查询每台设备的单位数据,这频率较大初定10秒一次，
 * 这是终端的数据，直接终端讲数据封装后，放进redis，接口实现redis调取。
 * 存储方式为hset的方式
 * @param city=beijing
 * @param data=915|807
 * 返回格式
 * ｛
			"state": "0"，
			"sendTime": "2015-8-27 17:42:55"，
			"data": [
			              {
					          "dataType": "GPS",
								"dplBus": "粤BD0372",
								"dplLine": "B669",
								"date": "2015-07-30",
			                  "time": "15:25:36",
			                  "longitude": "113943483",
			                  "latitude": "22490331",
			                  "velocity": "54",
			                  "direction": "222",
			                  "lineDirection": "上行"
			              },
			              {
								"dataType": "GPS",
								"dplBus": "粤BD0968",
								"dplLine": "B669",
								"date": "2015-07-30",        
								"time": "15:25:36",
			                  "longitude": "114055667",
			                  "latitude": "22567965",
			                  "velocity": "42",
			                  "direction": "157",
			                  "lineDirection": "上行"
			            }
			]
			}
 * @return
 */
	@RequestMapping(value = "linecity", method = { RequestMethod.POST, RequestMethod.GET })
	public String lineCity(String city, String data,String sign) {
		logger.info("通过城市和线路id查询每台设备的单位数据,这频率较大初定10秒一次,接口进入参数：city="+city+",data="+data);
		return  busApiService.linecity(city, data,sign);
	}

	/**
	 * 城市&线路站点查询接口,接口初定也是一天某个时间段调取一批次，调取频率较低
	 * @param city
	 * @param data
	 * 返回格式如下
					 * {
					"state": "0",
					"sendTime": "2015-8-27 17:42:55",
					"data": [
						"line":[
							{
								"lineid":"80310",
								"linename":"31路",
								"linedir": "0"
								"linegeo":[
									{
										"lon":"123009883",
										"lat":"28098764"
									},
									{
										"lon":"123009883",
										"lat":"28098764"
									}
								]
							},
							{
								"lineid": "80311",
								"linename": "31路",
								"linedir": "1",
								"linegeo": [
									{
										"lon":"123009883",
										"lat":"28098764"
									},
									{
										"lon":"123009883",
										"lat":"28098764"
									}
								]
							}
						],
						"stop":[
							{
								"stopid":"80310109",	
								"stopname":"上地五街",
								"lon":"123987345",
								"lat":"30345872", 
								"lineid":"80310"
								"linedir":"0",	
								"stopseq":"1", 
							},
							{
								"stopid":"80310101",
								"stopname":"上地七街",
								"lon":"123987345",
								"lat":"30345872",
								"lineid":"80311"
								"linedir":"1",
								"stopseq":"2",
							}
						]
					]
				}
	 * @return
	 */
	@RequestMapping(value = "linestop", method = { RequestMethod.POST, RequestMethod.GET })
	public String lineStop(String city, String data,String sign) {
		logger.info(" 城市&线路站点查询接口,接口进入参数：city="+city+",data="+data);
		return  busApiService.linestop(city, data,sign);
	}
}
