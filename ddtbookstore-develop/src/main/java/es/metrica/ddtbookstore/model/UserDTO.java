package es.metrica.ddtbookstore.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private Boolean vip;
	private Boolean activated;

	@Enumerated(EnumType.STRING)
	private Rol rol;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<OrderDTO> history;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_favorites", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
	private List<BookDTO> favorites;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<RatingDTO> ratings;

	public enum Rol {
		ADMIN, USER
	}

	public UserDTO() {

	}

	public UserDTO(String userName, String firstName, String lastName, String password, String email, Boolean vip,
			Boolean activated, Rol rol, List<OrderDTO> history, List<BookDTO> favorites, List<RatingDTO> ratings) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.vip = vip;
		this.activated = activated;
		this.rol = rol;
		this.history = history;
		this.favorites = favorites;
		this.ratings = ratings;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public List<BookDTO> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<BookDTO> favorites) {
		this.favorites = favorites;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<OrderDTO> getHistory() {
		return history;
	}

	public void setHistory(List<OrderDTO> orders) {
		this.history = orders;
	}

	public List<BookDTO> getFavourites() {
		return favorites;
	}

	public void setFavourites(List<BookDTO> favourites) {
		this.favorites = favourites;
	}

	public List<RatingDTO> getRatings() {
		return ratings;
	}

	public void setRatings(List<RatingDTO> ratings) {
		this.ratings = ratings;
	}
}
