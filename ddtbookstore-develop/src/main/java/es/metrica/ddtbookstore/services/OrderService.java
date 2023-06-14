package es.metrica.ddtbookstore.services;

import es.metrica.ddtbookstore.model.OrderDTO;
import es.metrica.ddtbookstore.repository.OrderRepository;
import es.metrica.ddtbookstore.repository.UserRepository;
import es.metrica.ddtbookstore.services.exceptions.NullDataException;
import es.metrica.ddtbookstore.services.exceptions.CustomWrongArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

	@Autowired
	private final OrderRepository orderRepository;
	@Autowired
	private final UserRepository userRepository;

	public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
	}

	public OrderDTO buy(OrderDTO order) {
		if (order == null)
			throw new NullDataException("The order is null");
		if (order.getOrderDetails() == null)
			throw new NullDataException("The order details is null");
		if (order.getOrderDetails().isEmpty())
			throw new CustomWrongArgumentException("The list of orderDetail is empty");
		if (order.getIncidences() == null)
			throw new NullDataException("The incidences is null");

		if (!checkIfNumberOfPhoneIsValid(order.getLocation().getTelephone()))
			throw new CustomWrongArgumentException("The phone number is not valid");
		return orderRepository.save(order);
	}

	public List<OrderDTO> getHistorical(Long id) {
		if (id == null)
			throw new NullDataException("The id is null");
		return userRepository.findById(id).get().getHistory();
	}

	private boolean checkIfNumberOfPhoneIsValid(String number) {
		if (number.length() != 9)
			return false;
		for (int i = 0; i < number.length(); i++) {
			if (!Character.isDigit(number.charAt(i)))
				return false;
		}
		return true;
	}

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderDTO getOrder(long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteOrder(OrderDTO order) {
        orderRepository.deleteById(order.getOrderId());
    }
}
