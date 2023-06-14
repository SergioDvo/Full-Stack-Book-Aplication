package es.metrica.ddtbookstore.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.metrica.ddtbookstore.model.RatingDTO;
import es.metrica.ddtbookstore.repository.RatingRepository;
import es.metrica.ddtbookstore.services.exceptions.CustomWrongArgumentException;
import es.metrica.ddtbookstore.services.exceptions.NullDataException;

@Service
public class RatingService {

	@Autowired
	private final RatingRepository ratingRepository;

	public RatingService(RatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
	}

	public RatingDTO getRating(Long id) {
		if (id == null) {
			throw new NullDataException("id is null");
		}
		Optional<RatingDTO> ratingFound = ratingRepository.findById(id);
		if (ratingFound.isEmpty()) {
			throw new NullDataException("Rating not exist");
		}
		return ratingFound.get();

	}

	public RatingDTO addRating(RatingDTO rating) {
		if (rating == null)
			throw new NullDataException("rating is null");
		if (rating.getStarValue() < 1 || rating.getStarValue() > 5) {
			throw new CustomWrongArgumentException("Invalid StarValue must be > 1 and < 5");
		}
		return ratingRepository.save(rating);
	}

	public void deleteRatingById(Long id) {
		if (id == null) {
			throw new NullDataException("id is null");
		} else if (ratingRepository.findById(id).get() == null) {
			throw new NullDataException("rating id not exist");
		} else {
			ratingRepository.deleteById(id);
		}
	}

	public RatingDTO modifyRatingById(RatingDTO rating, double starValue) {
		if (rating == null)
			throw new NullDataException("rating is null");
		if (starValue < 1 || starValue > 5) {
			throw new CustomWrongArgumentException("Invalid StarValue must be > 1 and < 5");
		}
		return ratingRepository.modifyRatingById(rating.getRatingID(), starValue);
	}

}
