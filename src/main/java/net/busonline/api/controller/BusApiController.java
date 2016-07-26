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

	/**
	 * 
	 * @param city
	 * @return
	 */
	@RequestMapping(value = "city", method = { RequestMethod.POST, RequestMethod.GET })
	public String city(String city) {
//		System.out.println(city);
//		busApiService.city(city);
		return this.jsonSuccess(busApiService.city(city));
	}

	@RequestMapping(value = "linecity", method = { RequestMethod.POST, RequestMethod.GET })
	public String lineCity(String city, String data) {
		System.out.println(city);
		return this.jsonSuccess("");
	}

	@RequestMapping(value = "linestop", method = { RequestMethod.POST, RequestMethod.GET })
	public String lineStop(String city, String data) {
		System.out.println(city);
		return this.jsonSuccess("");
	}
}
