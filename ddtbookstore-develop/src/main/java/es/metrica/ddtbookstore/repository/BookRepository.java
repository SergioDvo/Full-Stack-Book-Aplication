package es.metrica.ddtbookstore.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.metrica.ddtbookstore.model.BookDTO;

@Repository
public interface BookRepository extends JpaRepository<BookDTO, Long> {

	@Query("select b FROM BookDTO b where b.title like %:name%")
	List<BookDTO> getByName(@Param("name")String name);

	@Query("select b FROM BookDTO b where b.title=:title")
	BookDTO findByTitle(@Param("title")String title);

	@Query("SELECT b FROM BookDTO b WHERE b.bookId = (SELECT a.book.bookId from RatingDTO a where a.starValue >= :minRating AND a.starValue <= :maxRating)")
	List<BookDTO> getAvgFromRating(@Param("minRating") Double minRating, @Param("maxRating") Double maxRating);
	
}
