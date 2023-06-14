package es.metrica.ddtbookstore.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.metrica.ddtbookstore.model.BookDTO;
import es.metrica.ddtbookstore.repository.BookRepository;
import es.metrica.ddtbookstore.services.exceptions.EntityAlreadyExistException;
import es.metrica.ddtbookstore.services.exceptions.NullDataException;

@Service
public class BookService {

	@Autowired
	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public Optional<BookDTO> getBookDTObyId(Long id) {
		return bookRepository.findById(id);
	}

	public List<BookDTO> getBookDTObyName(String name) {
		if (name == null) {
			throw new NullDataException("Name is null");
		}
		return bookRepository.getByName(name);
	}

	public void deleteBookDTO(BookDTO book) {
		if (book == null) {
			throw new NullDataException("Book is null");
		} else {
			bookRepository.delete(book);
		}
	}

	public BookDTO updateBook(BookDTO book) {
		if (book == null) {
			throw new NullDataException("Book is null");
		} else {
			return bookRepository.save(book);
		}
	}

	public List<BookDTO> filterBooksByPrice(double minPrice, double maxPrice) {
		List<BookDTO> filteredBooks = new ArrayList<>();
		for (BookDTO book : bookRepository.findAll()) {
			if (book.getPrice() >= minPrice && book.getPrice() <= maxPrice) {
				filteredBooks.add(book);
			}
		}
		return filteredBooks;
	}

	public List<BookDTO> findAllAvailable(boolean available) {
		return bookRepository.findAll().stream().filter(book -> book.getAvailable() == available)
				.collect(Collectors.toList());
	}

	public List<BookDTO> filterBooksBySales(int minSales, int maxSales) {
		// TODO todavia no se puede debido a los modelos
		return null;
	}

	public List<BookDTO> filterBooksByRating(double minRating, double maxRating) {
		List<BookDTO> filteredBooks = bookRepository.getAvgFromRating(minRating, maxRating);
		return filteredBooks;
	}

	public BookDTO addBook(BookDTO book) {
		if (book == null) {
			throw new NullDataException("Book is null");
		} else if (bookRepository.findByTitle(book.getTitle()) != null) {
			throw new EntityAlreadyExistException("Book title already exists");
		} else {
			return bookRepository.save(book);
		}
	}

	public List<BookDTO> findAll() {
		return bookRepository.findAll();
	}

}
