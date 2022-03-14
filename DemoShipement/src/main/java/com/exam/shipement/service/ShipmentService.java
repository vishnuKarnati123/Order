package com.exam.shipement.service;

import java.util.List;


import org.springframework.http.ResponseEntity;

import com.exam.shipement.dto.OrderDetailsDto;
import com.exam.shipement.dto.ProductDto;



public interface ShipmentService {

	/*
	 * public String getShipementStatus(int orderid);
	 * 
	 * public List<OrderDetailsDto> getShipementDetails();
	 * 
	 * public String addShipementStatus(OrderDetailsDto order);
	 */

	 public String getShipmentStatus(int orderid);

	public String addShipmentStatus(OrderDetailsDto order);
	
	public String updateShipmentStatus(OrderDetailsDto order);
	//public OrderDetailsDto getShipementDetails(OrderDetailsDto order);

	public ProductDto getProductsFromShipment(String productName);

	public String deleteStatus(int orderid);

	public ProductDto addproduct(ProductDto product);



}
