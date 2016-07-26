package net.busonline.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.busonline.api.service.IBusApiService;
import net.busonline.core.base.BaseController;

@RestController
@RequestMapping("/busapi")
public class BusApiController extends BaseController {
	@Autowired
	private IBusApiService busApiService;

	/**接口，通过调取城市名字，进而返回北京跟百度合作的线路调取出来
	 *
	 *这里主要是业务逻辑实现部分
	 * @param city 
	 * @return
	 */
	@RequestMapping(value = "city", method = { RequestMethod.POST, RequestMethod.GET })
	public String city(String city) {
//		System.out.println(city);
//		busApiService.city(city);
		return this.jsonSuccess(busApiService.getLine(city));
	}
/**
 * 通过城市和线路id查询每台设备的单位数据
 * @param city
 * @param data
 * @return
 */
	@RequestMapping(value = "linecity", method = { RequestMethod.POST, RequestMethod.GET })
	public String lineCity(String city, String data) {
		System.out.println(city);
		return this.jsonSuccess(busApiService.lineCity(city, data));
	}

	/**
	 * 7.城市&线路站点查询接口
	 * @param city
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "linestop", method = { RequestMethod.POST, RequestMethod.GET })
	public String lineStop(String city, String data) {
		System.out.println(city);
		return this.jsonSuccess(busApiService.lineStop(city, data));
	}
}
