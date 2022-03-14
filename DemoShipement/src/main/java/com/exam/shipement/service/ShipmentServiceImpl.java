package com.exam.shipement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exam.shipement.dto.OrderDetailsDto;
import com.exam.shipement.dto.ProductDto;
import com.exam.shipement.entities.OrderDetails;
import com.exam.shipement.entities.Product;
import com.exam.shipement.repository.OrderRepository;
import com.exam.shipement.repository.ProductRepository;


@Service
public class ShipmentServiceImpl implements ShipmentService{

	@Autowired
	OrderRepository orderRepository;
	
	
	@Autowired 
	ProductRepository productRepository;
	
	@Autowired
	ModelMapper mapper;
	
	
	public String getShipmentStatus(int orderid) {
		
		Optional<OrderDetails> orderDetails=orderRepository.findById(orderid);
		if(orderDetails.isPresent())
		{
			int todayDate=LocalDate.now().getDayOfMonth();
			int deliveryDate=orderDetails.get().getDeliverydate().getDayOfMonth();
			int remainingDays=deliveryDate-todayDate;
			String status=null;
			if(remainingDays==0) 
			{
				  
				status="Hii your orderid: "+orderDetails.get().getOrderid() +" \n will  be delivered today: "+orderDetails.get().getDeliverydate();
				orderDetails.get().setShipmentstatus("By Today");
				orderDetails.get().setShipmentDate(orderDetails.get().getDeliverydate());
				orderRepository.save(orderDetails.get());
				 return status;
				 
			} 
			else if(remainingDays>=1)
			{
				orderDetails.get().setShipmentDate(orderDetails.get().getDeliverydate().plusDays(2));
				orderDetails.get().setShipmentstatus("In Progress");
				orderRepository.save(orderDetails.get());
				status="Hii your orderid: "+orderDetails.get().getOrderid() +" \n will  be delivered on: "+orderDetails.get().getShipmentDate();
				 return status;
			}
			else
			{
				orderDetails.get().setShipmentstatus("Done");
				orderRepository.save(orderDetails.get());
				status="Hii your orderid: "+orderDetails.get().getOrderid() +" \n delivery done on: "+orderDetails.get().getDeliverydate();
				 return status;
				
			}
			
		}
		else
		{
			return "Check your order id";
		}
		
	}

    public String addShipmentStatus(OrderDetailsDto orderDetailsDto)	
    {
    

		OrderDetails order = mapper.map(orderDetailsDto,OrderDetails.class);
		/*
		 * order.setStatus(o.getStatus()); order.setOrderdate(o.getOrderdate());
		 * order.setDeliverydate(o.getDeliverydate());
		 */
		order=orderRepository.save(order);
		
		List<Product> products = order.getProducts();
		for(Product product:products)
		{
			Product p=new Product();
			p.setProductid(product.getProductid());
			p.setProductname(product.getProductname());
			p.setPrice(product.getPrice());
			p.setCategory(product.getCategory());
			p.setOrderDetails(order); 
			System.out.println(productRepository.save(p));		
			
			
		}
		return "Order Added";
    }
    
    
	public String updateShipmentStatus(OrderDetailsDto order) {
		
		OrderDetails orderDetails=mapper.map(order,OrderDetails.class);
		int orderid=orderDetails.getOrderid();
		Optional<OrderDetails> optionalorderDetails=orderRepository.findById(orderid);
		if(optionalorderDetails.isPresent())
		{
			optionalorderDetails.get().setOrderdate(orderDetails.getOrderdate());
			optionalorderDetails.get().setDeliverydate(orderDetails.getDeliverydate());
			optionalorderDetails.get().setShipmentstatus(orderDetails.getShipmentstatus());
			optionalorderDetails.get().setShipmentDate(orderDetails.getShipmentDate());
			optionalorderDetails.get().setStatus(orderDetails.getStatus());
			orderRepository.save(optionalorderDetails.get());
			 for (Product prod : orderDetails.getProducts())
			 {
				 Optional<Product> prd = Optional.ofNullable(productRepository.findByProductname(prod.getProductname())); 
				 if (prd.isPresent()) 
				 { 
					 System.out.println(prd.get());
				     prd.get().setProductname(prod.getProductname());
				    prd.get().setCategory(prod.getCategory());
				    prd.get().setPrice(prod.getPrice()); 	
				    prd.get().setOrderDetails(orderDetails);
				   System.out.println(productRepository.save(prd.get()));
				    
				  }
				 else
				 {
					 prod.setOrderDetails(orderDetails);
					 
					System.out.println(productRepository.save(prod));  
				 }
			}
		     return "Shipment Updated";
		 }
		else
		{
			
		
		return "order id not present";
		}
	}

	@Override
	public ProductDto getProductsFromShipment(String productName) {
		return mapper.map(productRepository.findByProductname(productName),ProductDto.class);
		
	}

	@Override
	public String deleteStatus(int orderid)
	{
		Optional<OrderDetails> optional = orderRepository.findById(orderid);
		if (optional.isPresent()) {
			OrderDetails orderDetails = optional.get();
			List<Product> product = orderDetails.getProducts();
			for (Product p : product) {
				if (p.getPrice() == 1000) {
					orderDetails.setStatus("Deleted");
					orderRepository.save(orderDetails);
					break;
				}
			}

			return "order status deleted";
		} else {
			return "orderid is not present";
		}
	}

	@Override
	public ProductDto addproduct(ProductDto product) {
		// TODO Auto-generated method stub
		Product product1=productRepository.save(mapper.map(product,Product.class));
		return mapper.map(product1,ProductDto.class);
	}

	


	/*
	 * @Override public OrderDetailsDto getShipementDetails(OrderDetailsDto order) {
	 * // TODO Auto-generated method stub int
	 * todayDate=LocalDate.now().getDayOfMonth(); int
	 * deliveryDate=order.getDeliverydate().getDayOfMonth(); int
	 * remainingDays=deliveryDate-todayDate; String status=null;
	 * if(remainingDays==0) {
	 * 
	 * status="Hii your orderid: "+order.getOrderid()
	 * +" \n will  be delivered today: "+order.getDeliverydate();
	 * order.setShipmentstatus("By Today");
	 * order.setShipmentDate(order.getDeliverydate());
	 * //orderRepository.save(order); return order;
	 * 
	 * } else if(remainingDays>=1) {
	 * order.setShipmentDate(order.getDeliverydate().plusDays(2));
	 * order.setShipmentstatus("In Progress");
	 * //orderRepository.save(orderDetails.get());
	 * status="Hii your orderid: "+order.getOrderid()
	 * +" \n will  be delivered on: "+order.getShipmentDate(); return order; } else
	 * { orderDetails.get().setShipmentstatus("Done");
	 * orderRepository.save(orderDetails.get());
	 * status="Hii your orderid: "+orderDetails.get().getOrderid()
	 * +" \n delivery done on: "+orderDetails.get().getDeliverydate(); return
	 * status;
	 * 
	 * }
	 * 
	 * 
	 * return null; }
	 */
	
	/*  @Autowired ShipementRepository shipementRepository;
	  
	  @Autowired private ModelMapper mapper;
	  
	  @Override public String getShipementStatus(int orderid) { //Shipement
	  shipement=mapper.map(order,Shipement.class);
	  
	  Optional<Shipement> optionshipement=shipementRepository.findById(orderid);
	  if(optionshipement.isPresent()) { String status=null; int
	  todayDate=LocalDate.now().getDayOfMonth(); int
	  deliveryDate=optionshipement.get().getDeliverydate().getDayOfMonth(); int
	  remainingDays=deliveryDate-todayDate; if(remainingDays==0) {
	  status="Hii "+optionshipement.get().getCustomerName()+",\n your orderid:"
	  +optionshipement.get().getOrderid()
	  +" will  be delivered today "+optionshipement.get().getDeliverydate();
	  optionshipement.get().setShipementStatus("Delivered Today");
	  shipementRepository.save(optionshipement.get()); return status; } else
	  if(remainingDays>=1) {
	  status="Hii "+optionshipement.get().getCustomerName()+",\n your orderid: "
	  +optionshipement.get().getOrderid()+
	  "will  delivered by "+optionshipement.get().getDeliverydate();
	  optionshipement.get().setShipementStatus("will Deliver");
	  shipementRepository.save(optionshipement.get()); return status; } else {
	  status="Hii "+optionshipement.get().getCustomerName()+",\n your orderid:"
	  +optionshipement.get().getOrderid()+
	  "delivered on"+optionshipement.get().getDeliverydate();
	  
	  optionshipement.get().setShipementStatus("Delivered");
	  shipementRepository.save(optionshipement.get()); return status; }
	  
	  } else { return "Order is not correct please chech again"; }
	  
	  
	  }
	  
	  @Override public List<OrderDetailsDto> getShipementDetails() { // TODO
	  Auto-generated method stub List<Shipement>
	  shipement=shipementRepository.findAll(); List<OrderDetailsDto>
	  orderdetails=Arrays.asList(mapper.map(shipement,OrderDetailsDto[].class));
	  return orderdetails; }
	  
	  @Override public String addShipementStatus(OrderDetailsDto order) { Shipement
	  shipement=mapper.map(order,Shipement.class);
	  shipement.setCustomerName("vardhan"); shipement.setAddress("Kurnool"); int
	  todayDate=LocalDate.now().getDayOfMonth(); int
	  deliveryDate=shipement.getDeliverydate().getDayOfMonth(); int
	  remainingDays=deliveryDate-todayDate; if(remainingDays==0) {
	  shipement.setShipementStatus("Delivered Today"); } else if(remainingDays>1) {
	  shipement.setShipementStatus("Delivered on "+shipement.getDeliverydate()); }
	  else { shipement.setShipementStatus("Delivery Done "); }
	  shipementRepository.save(shipement); return "Shipement status added";
	  
	  }
	  
	  @Override public String deleteStatus(int orderid) { // TODO Auto-generated
	  method stub shipementRepository.deleteById(orderid); return
	  "Shipement deleted"; }*/
	 
	
}
