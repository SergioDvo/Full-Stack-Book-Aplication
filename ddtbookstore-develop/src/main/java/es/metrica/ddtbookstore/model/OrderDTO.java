package es.metrica.ddtbookstore.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "fk_user_id", nullable = false)
	@JsonIgnore
	private UserDTO user;

	@OneToMany(mappedBy = "order")
	private List<OrderDetailDTO> orderDetails;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<IncidenceDTO> incidences;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_location_id", referencedColumnName = "locationId", nullable = false)
	private LocationDTO location;

	public OrderDTO() {

	}

	public OrderDTO(LocalDate date, UserDTO user, List<OrderDetailDTO> orderDetails, List<IncidenceDTO> incidences,
			LocationDTO location) {
		this.date = date;
		this.user = user;
		this.orderDetails = orderDetails;
		this.incidences = incidences;
		this.location = location;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public LocalDate getCurrentDate() {
		return date;
	}

	public void setCurrentDate(LocalDate currentDate) {
		this.date = currentDate;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<OrderDetailDTO> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public List<IncidenceDTO> getIncidences() {
		return incidences;
	}

	public void setIncidences(List<IncidenceDTO> incidences) {
		this.incidences = incidences;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}
}
