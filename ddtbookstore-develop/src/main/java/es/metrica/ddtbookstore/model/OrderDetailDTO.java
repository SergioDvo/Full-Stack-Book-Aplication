package es.metrica.ddtbookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "orderdetails")
public class OrderDetailDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderDetailId;

	private Integer quantity;

	@ManyToOne
	@JoinColumn(name = "fk_book_id", nullable = false)
	private BookDTO book;

	@ManyToOne
	@JoinColumn(name = "fk_order_id", nullable = false)
	private OrderDTO order;

	public OrderDetailDTO() {

	}

	public OrderDetailDTO(Integer quantity, BookDTO book) {
		super();
		this.quantity = quantity;
		this.book = book;
	}

	public Long getId() {
		return orderDetailId;
	}

	public void setId(Long orderdetail_id) {
		this.orderDetailId = orderdetail_id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BookDTO getBook() {
		return book;
	}

	public void setBook(BookDTO book) {
		this.book = book;
	}

}
