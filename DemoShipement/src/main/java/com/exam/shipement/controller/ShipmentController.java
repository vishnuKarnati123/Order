package com.exam.shipement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exam.shipement.dto.OrderDetailsDto;
import com.exam.shipement.dto.ProductDto;
import com.exam.shipement.service.ShipmentService;


@RestController
@RequestMapping("/api")
public class ShipmentController {
	
	@Autowired
	ShipmentService shipmentService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/shipmentstatus/{orderid}")
	public String  getShipmentstatus(@PathVariable int orderid)
	{
		
		
		return shipmentService.getShipmentStatus(orderid);
		
	}

	@PostMapping("/status")
	public String addShipmentStatus(@RequestBody OrderDetailsDto order) 
	{ 
		return shipmentService.addShipmentStatus(order);
	
	}
	
	@PutMapping("/status")
	public String updateShipmentStatus(@RequestBody OrderDetailsDto order) 
	{ 
		return shipmentService.updateShipmentStatus(order);
	
	}
	
	@DeleteMapping("status/{orderid}") 
	public String deleteStatus(@PathVariable int orderid) 
	{
		return shipmentService.deleteStatus(orderid);
	}
	
	@GetMapping("/getproduct")
	public ProductDto getProductsFromShipment(@RequestParam String productName)
	{
		
		return shipmentService.getProductsFromShipment(productName);
	}
	/*
	 * @GetMapping("/status") public List<OrderDetailsDto> getShipementDetails() {
	 * return shipementService.getShipementDetails() ;
	 * 
	 * }
	 * 
	 * @PostMapping("/status") public String addShipementStatus(@RequestBody
	 * OrderDetailsDto order) { return shipementService.addShipementStatus(order);
	 * 
	 * 
	 * }
	 * 
	 * @DeleteMapping("status/{orderid}") public String deleteStatus(@PathVariable
	 * int orderid) { return shipementService.deleteStatus(orderid);
	 * 
	 * 
	 * }
	 */
	/*
	 * @GetMapping("/status") public OrderDetailsDto
	 * getShipementDetails(@RequestBody OrderDetailsDto order) { return
	 * shipmentService.getShipementDetails(order) ;
	 * 
	 * }
	 */
	
	@PostMapping("/addProduct")
	public ProductDto addProduct(@RequestBody ProductDto product)
	{
		return shipmentService.addproduct(product);
	}
	

}
