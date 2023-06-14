package es.metrica.ddtbookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.metrica.ddtbookstore.model.RatingDTO;

@Repository
public interface RatingRepository extends JpaRepository<RatingDTO, Long> {

	@Modifying
	@Query("update RatingDTO r set r.starValue = ?2 where r.ratingId = ?1")
	RatingDTO modifyRatingById(Long id, double starValue);
}
