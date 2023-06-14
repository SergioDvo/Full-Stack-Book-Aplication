package es.metrica.ddtbookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.metrica.ddtbookstore.model.BookDTO;
import es.metrica.ddtbookstore.model.RatingDTO;
import es.metrica.ddtbookstore.model.UserDTO;
import es.metrica.ddtbookstore.repository.RatingRepository;
import es.metrica.ddtbookstore.services.RatingService;
import es.metrica.ddtbookstore.services.exceptions.CustomWrongArgumentException;
import es.metrica.ddtbookstore.services.exceptions.NullDataException;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

	@InjectMocks
	private RatingService ratingService;

	@Mock
	private RatingRepository repository;

	@Captor
	private ArgumentCaptor<Long> ratingIdCaptor;

	@Test
	void testCheckRatingByID() {
		// given
		Long id = 1L;
		BookDTO book1 = new BookDTO("El Quijote", "Miguel de Cervantes", 800D, "La historia", true, new ArrayList<>());
		UserDTO user = new UserDTO("miguel", "miguel", "f", "123", "miguel@gmail.com", false, true, UserDTO.Rol.USER,
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		Optional<RatingDTO> rating = Optional.of(new RatingDTO(4.0, book1, user));

		// when
		when(repository.findById(id)).thenReturn(rating);
		Optional<RatingDTO> result = Optional.of(ratingService.getRating(id));

		// then
		assertEquals(result, rating);
	}

	@Test
	void testCheckAddRating() {
		// given
		BookDTO book1 = new BookDTO("El Quijote", "Miguel de Cervantes", 800D, "La historia", true, new ArrayList<>());
		UserDTO user = new UserDTO("miguel", "miguel", "f", "123", "miguel@gmail.com", false, true, UserDTO.Rol.USER,
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		RatingDTO rating = new RatingDTO(4.0, book1, user);
		// when
		when(repository.save(rating)).thenReturn(rating);
		RatingDTO result = ratingService.addRating(rating);

		// then
		verify(repository).save(rating);
		verifyNoMoreInteractions(repository);
		assertEquals(rating, result);
	}

	@Test
	void testCheckAddNotValidRating() {
		BookDTO book1 = new BookDTO("El Quijote", "Miguel de Cervantes", 800D, "La historia", true, new ArrayList<>());
		UserDTO user = new UserDTO("miguel", "miguel", "f", "123", "miguel@gmail.com", false, true, UserDTO.Rol.USER,
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		RatingDTO rating = new RatingDTO(7.0, book1, user);

		CustomWrongArgumentException exception = assertThrows(CustomWrongArgumentException.class,
				() -> ratingService.addRating(rating));

		assertEquals("Invalid StarValue must be > 1 and < 5", exception.getMessage());
	}

	// TODO este aun no funciona, revisar
	@Test
	void testDeleteRating() {
		Long id = 1L;
		BookDTO book1 = new BookDTO("El Quijote", "Miguel de Cervantes", 800D, "La historia", true, new ArrayList<>());
		UserDTO user = new UserDTO("miguel", "miguel", "f", "123", "miguel@gmail.com", false, true, UserDTO.Rol.USER,
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		RatingDTO rating = new RatingDTO(3.0, book1, user);
		// when
		when(repository.findById(id)).thenReturn(Optional.of(rating));
		ratingService.deleteRatingById(id);
		verify(repository, atLeastOnce()).deleteById(id);
	}

	@Test
	void testDeleteRatingNull() {
		assertThrows(NullDataException.class, () -> ratingService.deleteRatingById(null));
	}

	@Test
	void testModifyRatingValue() {
		BookDTO book1 = new BookDTO("El Quijote", "Miguel de Cervantes", 800D, "La historia", true, new ArrayList<>());
		UserDTO user = new UserDTO("miguel", "miguel", "f", "123", "miguel@gmail.com", false, true, UserDTO.Rol.USER,
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		RatingDTO rating = new RatingDTO(4.0, book1, user);

		when(repository.modifyRatingById(rating.getRatingID(), 5.0)).thenReturn(rating);
		RatingDTO result = ratingService.modifyRatingById(rating, 5.0);

		assertEquals(result, rating);
	}

	@Test
	void testModifyNotValidRatingValue() {
		BookDTO book1 = new BookDTO("El Quijote", "Miguel de Cervantes", 800D, "La historia", true, new ArrayList<>());
		UserDTO user = new UserDTO("miguel", "miguel", "f", "123", "miguel@gmail.com", false, true, UserDTO.Rol.USER,
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		RatingDTO rating = new RatingDTO(4.0, book1, user);

		CustomWrongArgumentException exception = assertThrows(CustomWrongArgumentException.class,
				() -> ratingService.modifyRatingById(rating, 7.4));

		assertEquals("Invalid StarValue must be > 1 and < 5", exception.getMessage());
	}

}
