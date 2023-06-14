package es.metrica.ddtbookstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "incidences")
public class IncidenceDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long incidenceId;

	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_order_id", nullable = false)
	private OrderDTO order;
	
	public IncidenceDTO() {

	}

	public IncidenceDTO(String description, OrderDTO order) {
		this.description = description;
		this.order = order;
	}

	public Long getIncidenceID() {
		return incidenceId;
	}

	public void setIncidenceID(Long incidenceID) {
		this.incidenceId = incidenceID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public OrderDTO getOrder() {
		return order;
	}

	public void setOrder(OrderDTO order) {
		this.order = order;
	}
}
