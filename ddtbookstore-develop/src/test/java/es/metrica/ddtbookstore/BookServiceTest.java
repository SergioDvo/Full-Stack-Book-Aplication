package es.metrica.ddtbookstore;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.metrica.ddtbookstore.model.BookDTO;
import es.metrica.ddtbookstore.repository.BookRepository;
import es.metrica.ddtbookstore.services.BookService;
import es.metrica.ddtbookstore.services.exceptions.NullDataException;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@InjectMocks
	private BookService bookService;

	@Mock
	private BookRepository repository;

	@Captor
	private ArgumentCaptor<Long> bookIdCaptor;

	@Test
	void checkId() {
		// given
		Optional<BookDTO> book = Optional
				.of(new BookDTO("test", "111", 10D, "This is a test", true, new ArrayList<>()));
		when(repository.findById(anyLong())).thenReturn(book);
		// when
		Optional<BookDTO> result = bookService.getBookDTObyId(1L);
		// then
		verify(repository).findById(bookIdCaptor.capture());
		assertEquals(1, bookIdCaptor.getValue());
		assertEquals(result, book);
	}

	@Test
	void checkIdNotExisting() {
		// given
		Optional<BookDTO> book = Optional
				.of(new BookDTO("test", "111", 10D, "This is a test", true, new ArrayList<>()));
		when(repository.findById(1L)).thenReturn(book);
		when(repository.findById(2L)).thenReturn(null);
		// when
		Optional<BookDTO> resultNull = bookService.getBookDTObyId(2L);
		Optional<BookDTO> resultCorrect = bookService.getBookDTObyId(1L);
		// then
		assertNull(resultNull);
		assertEquals(resultCorrect, book);

	}

	@Test
	void findAllBooks() {
		// given

		List<BookDTO> ListBooks = new ArrayList<>();

		BookDTO book1 = new BookDTO("El Quijote", "Miguel de Cervantes", 800D,
				"La historia de un hidalgo que se vuelve loco leyendo libros de caballería.", true, new ArrayList<>());
		ListBooks.add(book1);

		BookDTO book2 = new BookDTO("Cien años de soledad", "Gabriel García Márquez", 500D,
				"La historia de una familia a lo largo de siete generaciones en un pueblo ficticio de Colombia.", true,
				new ArrayList<>());
		ListBooks.add(book2);

		BookDTO book3 = new BookDTO("La odisea", "Homero", 700D,
				"La historia del viaje de Odiseo de regreso a casa después de la Guerra de Troya.", false,
				new ArrayList<>());
		ListBooks.add(book3);

		// when
		when(repository.findAll()).thenReturn(ListBooks);

		List<BookDTO> resultListBook = bookService.findAll();

		// then
		assertArrayEquals(ListBooks.toArray(), resultListBook.toArray());

		assertEquals(ListBooks.size(), resultListBook.size());

		// Verificar que se ha hecho al menos una llamada al respositorio

		verify(repository, atLeastOnce()).findAll();
	}

	@Test
    void findAllBooksEmpty() {

        when(repository.findAll()).thenReturn(new ArrayList<>());

        List<BookDTO> books = bookService.findAll();
        assertTrue(books.isEmpty());
    }

	@Test
	void findBookAvailable() {
		// given

		List<BookDTO> ListBooks = new ArrayList<>();

		BookDTO book1 = new BookDTO("El Quijote", "Miguel de Cervantes", 800D,
				"La historia de un hidalgo que se vuelve loco leyendo libros de caballería.", true, new ArrayList<>());
		ListBooks.add(book1);

		BookDTO book2 = new BookDTO("Cien años de soledad", "Gabriel García Márquez", 500D,
				"La historia de una familia a lo largo de siete generaciones en un pueblo ficticio de Colombia.", true,
				new ArrayList<>());
		ListBooks.add(book2);

		BookDTO book3 = new BookDTO("La odisea", "Homero", 700D,
				"La historia del viaje de Odiseo de regreso a casa después de la Guerra de Troya.", false,
				new ArrayList<>());
		ListBooks.add(book3);

		List<BookDTO> ListBooksAvailable = ListBooks.stream().filter(b -> b.getAvailable() == true)
				.collect(Collectors.toList());

		// when
		when(bookService.findAllAvailable(true)).thenReturn(ListBooksAvailable);

		List<BookDTO> resultListBookAvailable = bookService.findAllAvailable(true);

		// then
		assertArrayEquals(ListBooksAvailable.toArray(), resultListBookAvailable.toArray());
	}

	@Test
	void searchBook() {
		// given

		List<BookDTO> ListBooksSearch = new ArrayList<>();

		BookDTO book1 = new BookDTO("El Quijote", "Miguel de Cervantes", 800D,
				"La historia de un hidalgo que se vuelve loco leyendo libros de caballería.", true, new ArrayList<>());
		ListBooksSearch.add(book1);

		// when
		when(repository.getByName("Quijote")).thenReturn(ListBooksSearch);

		List<BookDTO> resultListBookSearch = bookService.getBookDTObyName("Quijote");

		// then
		assertArrayEquals(ListBooksSearch.toArray(), resultListBookSearch.toArray());

	}

	@Test
	void searchBookNull() {
		// given

		List<BookDTO> ListBooksSearch = new ArrayList<>();

		BookDTO book1 = new BookDTO("El Quijote", "Miguel de Cervantes", 800D,
				"La historia de un hidalgo que se vuelve loco leyendo libros de caballería.", true, new ArrayList<>());
		ListBooksSearch.add(book1);

		assertThrows(NullDataException.class, () -> {
			bookService.getBookDTObyName(null);
		});
	}

	@Test
	void addBook() {
		// given
		BookDTO book = new BookDTO("test", "111", 10D, "This is a test", true, new ArrayList<>());
		when(repository.save(book)).thenReturn(book);
		// when
		BookDTO result = bookService.addBook(book);
		// then
		assertEquals(result, book);
	}

	@Test
	void addBookNull() {
		assertThrows(NullDataException.class, () -> bookService.addBook(null));
	}

	@Test
	void updateBook() {
		// given
		BookDTO book = new BookDTO("test", "111", 10D, "This is a test", true, new ArrayList<>());
		when(repository.save(book)).thenReturn(book);
		// when
		BookDTO result = bookService.updateBook(book);
		// then
		assertEquals(result, book);
	}

	@Test
	void updateBookNull() {
		assertThrows(NullDataException.class, () -> bookService.updateBook(null));
	}

	@Test
	void deleteBook() {
		BookDTO book = new BookDTO("test", "111", 10D, "This is a test", true, new ArrayList<>());
		bookService.deleteBookDTO(book);
		verify(repository, atLeastOnce()).delete(book);
	}

	@Test
	void deleteBookNull() {
		assertThrows(NullDataException.class, () -> bookService.deleteBookDTO(null));
	}

	@Test
	void filterByRating() {
		BookDTO book1 = new BookDTO("El Quijote", "Miguel de Cervantes", 800D,
				"La historia de un hidalgo que se vuelve loco leyendo libros de caballería.", true, new ArrayList<>());

		BookDTO book2 = new BookDTO("Cien años de soledad", "Gabriel García Márquez", 500D,
				"La historia de una familia a lo largo de siete generaciones en un pueblo ficticio de Colombia.", true,
				new ArrayList<>());

		List<BookDTO> booksFilteredByRating = new ArrayList<>(List.of(book1, book2));
		when(repository.getAvgFromRating(3D, 5D)).thenReturn(booksFilteredByRating);

		List<BookDTO> listResultBooks = bookService.filterBooksByRating(3D, 5D);

		assertEquals(listResultBooks, booksFilteredByRating);
	}
}
