package es.metrica.ddtbookstore.endPoint.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.metrica.ddtbookstore.model.BookDTO;
import es.metrica.ddtbookstore.model.UserDTO;
import es.metrica.ddtbookstore.services.UserService;
import jakarta.websocket.server.PathParam;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/all")
	public List<UserDTO> getUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public Optional<UserDTO> getUserById(@PathVariable("id") Long id) {
		return userService.getUserById(id);
	}

	@GetMapping("username/{userName}")
	public UserDTO getUserByUsername(@PathVariable("userName") final String userName) {
		return userService.getByUsername(userName);
	}

	@GetMapping("/favorites/{id}")
	public List<BookDTO> getFavoriteBooks(@PathVariable("id") Long id) {
		return userService.getUserFavoriteBooks(id);
	}

	@PostMapping("{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO addToFavorites(@PathVariable("id") Long idUser, @RequestBody BookDTO book) {
		return userService.addToFavorites(idUser, book);
	}

	@DeleteMapping("/{idUser}/{idBook}")
	public void removeFromFavorites(@PathVariable ("idUser") Long idUser, @PathVariable ("idBook") Long bookId) {
	    userService.removeFromFavorites(idUser, bookId);
	}


}
