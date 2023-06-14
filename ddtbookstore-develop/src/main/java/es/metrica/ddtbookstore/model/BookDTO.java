package es.metrica.ddtbookstore.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;

	private String title;

	private String isbn;

	private Double price;
	
	private String sinopsis;
	
	private Boolean available;

	@OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
	private List<RatingDTO> ratingList;

	@OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
	private List<OrderDetailDTO> orderDetails;

	public BookDTO(){

	}

	public BookDTO(String title, String isbn, Double price, String sinopsis, Boolean available, List<RatingDTO> ratingList) {
		this.title = title;
		this.isbn = isbn;
		this.price = price;
		this.sinopsis = sinopsis;
		this.available = available;
		this.ratingList = ratingList;
	}

	public Long getId() {
		return bookId;
	}

	public void setId(Long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public List<RatingDTO> getRatingList() {
		return ratingList;
	}

	public void setRatingList(List<RatingDTO> ratingList) {
		this.ratingList = ratingList;
	}
}
