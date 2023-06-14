package es.metrica.ddtbookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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
import es.metrica.ddtbookstore.repository.BookRepository;
import es.metrica.ddtbookstore.repository.UserRepository;
import es.metrica.ddtbookstore.services.UserService;
import es.metrica.ddtbookstore.services.exceptions.EntityAlreadyExistException;
import es.metrica.ddtbookstore.services.exceptions.NullDataException;
import es.metrica.ddtbookstore.services.exceptions.CustomWrongArgumentException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private BookRepository bookRepository;

	@Captor
	private ArgumentCaptor<UserDTO> userCaptor;

	@Captor
	private ArgumentCaptor<Long> userIdCaptor;

	@Captor
	private ArgumentCaptor<BookDTO> bookCaptor;

	@Captor
	private ArgumentCaptor<Long> bookIdCaptor;

	@Test
	void test_getAllUsers() {
		// given
		List<UserDTO> users = new ArrayList<>();
		UserDTO user = new UserDTO("raul1", "raul", "ramirez", "123", "raul@gmail.com", false, true, UserDTO.Rol.USER,
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

		users.add(user);
		when(userRepository.findAll()).thenReturn(users);

		// when
		List<UserDTO> result = userService.getAllUsers();

		// then
		verify(userRepository).findAll();
		verifyNoMoreInteractions(userRepository);
		assertEquals(users, result);
	}

	@Test
	void test_getUserById_existingUser() {
		// given
		Long userId = 1L;
		UserDTO user = new UserDTO("raul1", "raul", "ramirez", "123", "raul@gmail.com", false, true, UserDTO.Rol.USER,
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		// when
		Optional<UserDTO> result = userService.getUserById(userId);

		// then
		verify(userRepository).findById(userIdCaptor.capture());
		assertEquals(userId, userIdCaptor.getValue());
		assertTrue(result.isPresent());
		assertEquals(user, result.get());
	}

	@Test
	void test_getUserById_nonExistingUser() {
		// given
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// when
		Optional<UserDTO> result = userService.getUserById(userId);

		// then
		verify(userRepository).findById(userIdCaptor.capture());
		assertEquals(userId, userIdCaptor.getValue());
		assertFalse(result.isPresent());
	}

	@Test
	void test_getByUsername_existingUser() {
		// given
		String userName = "john.doe";
		UserDTO user = new UserDTO("raul1", "raul", "ramirez", "123", "raul@gmail.com", false, true, UserDTO.Rol.USER,
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		when(userRepository.findByUserName(userName)).thenReturn(user);

		// when
		UserDTO result = userService.getByUsername(userName);

		// then
		verify(userRepository).findByUserName(userName);
		verifyNoMoreInteractions(userRepository);
		assertEquals(user, result);
	}

	@Test
	void test_addUser_newUser() {
		// given
		UserDTO newUser = new UserDTO("raul1", "raul", "ramirez", "123", "raul@gmail.com", false, true,
				UserDTO.Rol.USER, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		when(userRepository.checkIfExists(newUser.getUserId())).thenReturn(false);
		when(userRepository.save(newUser)).thenReturn(newUser);

		// when
		UserDTO result = userService.addUser(newUser);

		// then
		verify(userRepository).checkIfExists(newUser.getUserId());
		verify(userRepository).save(newUser);
		verifyNoMoreInteractions(userRepository);
		assertEquals(newUser, result);
	}

	@Test
	void test_addUser_existingUser() {
		// given
		UserDTO existingUser = new UserDTO("raul1", "raul", "ramirez", "123", "raul@gmail.com", false, true,
				UserDTO.Rol.USER, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		when(userRepository.checkIfExists(existingUser.getUserId())).thenReturn(true);

		// when
		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class,
				() -> userService.addUser(existingUser));

		// then
		verify(userRepository).checkIfExists(existingUser.getUserId());
		verifyNoMoreInteractions(userRepository);
		assertEquals("User already added into the database", exception.getMessage());
	}

	@Test
	void test_deleteUserById_existingUser() {
		// given
		Long userId = 1L;
		UserDTO user = new UserDTO("raul1", "raul", "ramirez", "123", "raul@gmail.com", false, true, UserDTO.Rol.USER,
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(userRepository.deleteUserById(userId)).thenReturn(user);

		// when
		UserDTO result = userService.deleteUserById(userId);

		// then
		verify(userRepository).findById(userIdCaptor.capture());
		verify(userRepository).deleteUserById(userIdCaptor.capture());
		verifyNoMoreInteractions(userRepository);
		assertEquals(userId, userIdCaptor.getValue());
		assertEquals(user, result);
	}

	@Test
	void test_deleteUserById_nonExistingUser() {
		// given
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// when
		NullDataException exception = assertThrows(NullDataException.class, () -> userService.deleteUserById(userId));

		// then
		verify(userRepository).findById(userIdCaptor.capture());
		verifyNoMoreInteractions(userRepository);
		assertEquals("User not exist", exception.getMessage());
		assertEquals(userId, userIdCaptor.getValue());
	}

	@Test
	void test_modifyUser_nonExistingUser() {
		// given
		Long userId = null;
		UserDTO modifiedUser = new UserDTO("raul1", "raul", "ramirez", "123", "raul@gmail.com", false, true,
				UserDTO.Rol.USER, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// when
		NullDataException exception = assertThrows(NullDataException.class, () -> userService.modifyUser(modifiedUser));

		// then
		verify(userRepository).findById(userId);
		verifyNoMoreInteractions(userRepository);
		assertEquals("User not exist", exception.getMessage());
	}

	@Test
	void test_getUserFavoriteBooks() {
		// given
		Long userId = 1L;
		UserDTO user = new UserDTO();
		List<BookDTO> favoriteBooks = new ArrayList<>();
		List<RatingDTO> ratingList = new ArrayList<>();
		favoriteBooks.add(new BookDTO("Book 1", "123456789", 10.0, "Description 1", true, ratingList));
		favoriteBooks.add(new BookDTO("Book 2", "987654321", 15.0, "Description 2", true, ratingList));
		user.setFavourites(favoriteBooks);
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		// when
		List<BookDTO> result = userService.getUserFavoriteBooks(userId);

		// then
		verify(userRepository).findById(userIdCaptor.capture());
		assertEquals(userId, userIdCaptor.getValue());
		assertEquals(favoriteBooks, result);
	}

	@Test
	void test_getUserFavoriteBooks_nullUser() {
		// given
		Long userId = -1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// when
		NullDataException exception = assertThrows(NullDataException.class,
				() -> userService.getUserFavoriteBooks(userId));

		// then
		assertEquals("idUser is null", exception.getMessage());
		verify(userRepository).findById(userIdCaptor.capture());
		assertEquals(userId, userIdCaptor.getValue());
	}

	@Test
	void test_addToFavorites() {

		// given
		Long userId = 1L;
		BookDTO book = new BookDTO("Book 1", "123456789", 10.0, "Description 1", true, new ArrayList<>());
		UserDTO user = new UserDTO();

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);

		// when
		UserDTO result = userService.addToFavorites(userId, book);

		// then
		verify(userRepository).findById(userIdCaptor.capture());
		assertEquals(userId, userIdCaptor.getValue());
		verify(userRepository).save(user);
		assertEquals(user, result);
	}

	@Test
	void test_addToFavorites_withNullUser() {

		Long userId = 1L;
		BookDTO book = new BookDTO("Book 1", "123456789", 10.0, "Description 1", true, new ArrayList<>());
		when(userRepository.findById(userId)).thenReturn(Optional.empty());
		assertThrows(NullDataException.class, () -> {
			userService.addToFavorites(userId, book);
		});
	}

	@Test
	void test_addToFavorites_withNullBook() {

		Long userId = 1L;
		assertThrows(NullDataException.class, () -> {
			userService.addToFavorites(userId, null);
		});
	}

	@Test
	void test_removeFromFavorites() {

		// given
		Long userId = 1L;
		Long bookId = 123L;
		UserDTO user = new UserDTO();
		List<BookDTO> favorites = new ArrayList<>();
		BookDTO bookToRemove = new BookDTO("Book 1", "123456789", 10.0, "Description 1", true, new ArrayList<>());
		bookToRemove.setId(bookId);
		favorites.add(bookToRemove);
		user.setFavorites(favorites);

		// when
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);

		// then
		userService.removeFromFavorites(userId, bookId);

		verify(userRepository).findById(userIdCaptor.capture());
		assertEquals(userId, userIdCaptor.getValue());
		verify(userRepository).save(userCaptor.capture());
		assertEquals(favorites.isEmpty(), userCaptor.getValue().getFavorites().isEmpty());
	}

	@Test
	void test_removeFromFavorites_nullUser() {

		// given
		Long userId = 1L;
		Long bookId = 123L;

		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		assertThrows(NullDataException.class, () -> {
			userService.removeFromFavorites(userId, bookId);
		});
	}

	@Test
	void test_removeFromFavorites_bookNotFound() {

		// given
		Long userId = 1L;
		Long bookId = 123L;
		UserDTO user = new UserDTO();

		List<BookDTO> favorites = new ArrayList<>();
		user.setFavorites(favorites);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		assertThrows(CustomWrongArgumentException.class, () -> {
			userService.removeFromFavorites(userId, bookId);
		});
	}

	@Test
	void test_removeFromFavorites_userNotFound() {
		// given
		Long userId = 0L;
		Long bookId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// when
		NullDataException exception = assertThrows(NullDataException.class,
				() -> userService.removeFromFavorites(userId, bookId));

		// then
		assertEquals("null arguments", exception.getMessage());
		verify(userRepository).findById(userIdCaptor.capture());
		assertEquals(userId, userIdCaptor.getValue());
		verifyNoMoreInteractions(userRepository);
	}

}
