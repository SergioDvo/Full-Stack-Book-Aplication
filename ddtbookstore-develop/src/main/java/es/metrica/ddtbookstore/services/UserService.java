package es.metrica.ddtbookstore.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.metrica.ddtbookstore.model.BookDTO;
import es.metrica.ddtbookstore.model.UserDTO;
import es.metrica.ddtbookstore.repository.BookRepository;
import es.metrica.ddtbookstore.repository.UserRepository;
import es.metrica.ddtbookstore.services.exceptions.EntityAlreadyExistException;
import es.metrica.ddtbookstore.services.exceptions.NullDataException;
import es.metrica.ddtbookstore.services.exceptions.CustomWrongArgumentException;

/**
 * @author Raul Ramirez
 *
 */
@Service
public class UserService {

	private final UserRepository userRepository;
	private final BookRepository bookRepository;

	@Autowired
	public UserService(UserRepository userRepository, BookRepository bookRepository) {
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
	}

	public List<UserDTO> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<UserDTO> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public UserDTO getByUsername(final String userName) {
		return userRepository.findByUserName(userName);
	}

	public UserDTO addUser(UserDTO addedUser) {
		if (userRepository.checkIfExists(addedUser.getUserId())) {
			throw new EntityAlreadyExistException("User already added into the database");
		}
		return userRepository.save(addedUser);
	}

	public UserDTO deleteUserById(long id) {
		Optional<UserDTO> userFound = userRepository.findById(id);
		if (userFound.isEmpty()) {
			throw new NullDataException("User not exist");
		}
		return userRepository.deleteUserById(id);
	}

	public UserDTO modifyUser(UserDTO modifiedUser) {
		Long userId = modifiedUser.getUserId();
		UserDTO existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser == null) {
			throw new NullDataException("User not exist");
		}
		return userRepository.modifyUser(userId, modifiedUser.getEmail(), modifiedUser.getFirstName(),
				modifiedUser.getLastName(), modifiedUser.getRol(), modifiedUser.getUserName());
	}

	public List<BookDTO> getUserFavoriteBooks(Long userId) {
		UserDTO user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			throw new NullDataException("idUser is null");
		}
		return user.getFavourites();
	}

	public UserDTO addToFavorites(Long userId, BookDTO book) {
		UserDTO user = userRepository.findById(userId).orElse(null);
		if (user == null || book == null) {
			throw new NullDataException("null arguments");
		}
		List<BookDTO> favoriteBook = new ArrayList<>();
		favoriteBook.add(book);
		user.setFavorites(favoriteBook);
		return userRepository.save(user);
	}

	public void removeFromFavorites(Long userId, Long bookId) {
		UserDTO user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			throw new NullDataException("null arguments");
		};
		
	    List<BookDTO> favorites = user.getFavorites();
	    BookDTO bookToRemove = null;
	    for (BookDTO book : favorites) {
	        if (book.getId().equals(bookId)) {
	            bookToRemove = book;
	            break;
	        }
	    }
	    if (bookToRemove != null) {
	        favorites.remove(bookToRemove);
	        user.setFavorites(favorites);
	        userRepository.save(user);
	    } else {
	        throw new CustomWrongArgumentException("Book not found in favorites");
	    }
	}

}
