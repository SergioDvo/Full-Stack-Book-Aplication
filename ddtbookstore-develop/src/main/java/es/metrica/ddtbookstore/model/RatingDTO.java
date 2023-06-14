package es.metrica.ddtbookstore.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


@Entity
@Table(name = "ratings")
public class RatingDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ratingId;
	private Double starValue;
	
	@ManyToOne
	@JoinColumn(name = "fk_book_id", nullable = false)
	@JsonIgnore
	private BookDTO book;
	
	@ManyToOne
	@JoinColumn(name = "fk_user_id", nullable = false)
	@JsonIgnore
	private UserDTO user;

	public RatingDTO() {

	}

	public RatingDTO(Double starValue, BookDTO book, UserDTO user) {
		this.starValue = starValue;
		this.book = book;
		this.user = user;
	}

	public Long getRatingID() {
		return ratingId;
	}

	public void setRatingID(Long ratingID) {
		this.ratingId = ratingID;
	}

	public Double getStarValue() {
		return starValue;
	}

	public void setStarValue(Double starValue) {
		this.starValue = starValue;
	}

	public BookDTO getBook() {
		return book;
	}

	public void setBook(BookDTO book) {
		this.book = book;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}

