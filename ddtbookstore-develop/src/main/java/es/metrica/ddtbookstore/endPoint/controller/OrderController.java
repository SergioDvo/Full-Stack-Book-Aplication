package es.metrica.ddtbookstore.endPoint.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.metrica.ddtbookstore.model.OrderDTO;
import es.metrica.ddtbookstore.services.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/history/{id}")
	public List<OrderDTO> getHistory(@PathVariable("id") Long id) {
		return orderService.getHistorical(id);
	}

	// TODO poner lazy en el atributo history del modelo?
	@GetMapping("/orders")
	public List<OrderDTO> getOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping("/order/{id}")
	public OrderDTO geOrderById(@PathVariable("id") Long id) {
		return orderService.getOrder(id);
	}

	@DeleteMapping("/delOrder{order}")
	public void deleteOrder(@PathVariable OrderDTO order) {
		orderService.deleteOrder(order);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDTO buyOrder(@RequestBody OrderDTO order) {
		return orderService.buy(order);
	}

}
