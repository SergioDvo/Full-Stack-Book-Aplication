package es.metrica.ddtbookstore.endPoint.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.metrica.ddtbookstore.model.BookDTO;
import es.metrica.ddtbookstore.services.BookService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/book")
public class BookController {

	@Autowired
	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/all")
	public List<BookDTO> getAllBooks() {
		return bookService.findAll();
	}

	@GetMapping("/available")
	public List<BookDTO> getAllAvailableBooks() {
		return bookService.findAllAvailable(true);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO createBook(@RequestBody BookDTO book) {
		return bookService.addBook(book);
	}

	@GetMapping("/id/{id}")
	public Optional<BookDTO> getBookbyId(@PathVariable Long id) {
		return bookService.getBookDTObyId(id);
	}

	@GetMapping("/title/{name}")
	public List<BookDTO> getBookByName(@PathVariable String name) {
		return bookService.getBookDTObyName(name);
	}

	@DeleteMapping("/del/{book}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBook(@PathVariable BookDTO book) {
		bookService.deleteBookDTO(book);
	}

	@PutMapping("/update/{book}")
	@ResponseStatus(HttpStatus.OK)
	public BookDTO updateBook(@PathVariable BookDTO book) {
		return bookService.updateBook(book);
	}

	@GetMapping("/price/{minPrice},{maxPrice}")
	public List<BookDTO> filterByPrice(@PathVariable double minPrice, @PathVariable double maxPrice) {
		return bookService.filterBooksByPrice(minPrice, maxPrice);
	}

	// filterBooksBySales
	@GetMapping("/rating/{minRating},{maxRating}")
	public List<BookDTO> filterByRating(@PathVariable double minRating, @PathVariable double maxRating) {
		return bookService.filterBooksByRating(minRating, maxRating);
	}

}
