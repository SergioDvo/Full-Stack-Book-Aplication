package es.metrica.ddtbookstore.endPoint.controller;

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

import es.metrica.ddtbookstore.model.RatingDTO;
import es.metrica.ddtbookstore.services.RatingService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/rating")
public class RatingController {

	private final RatingService ratingService;

	public RatingController(RatingService ratingService) {
		this.ratingService = ratingService;
	}

	@GetMapping("/get/{id}")
	public RatingDTO getRatingById(@PathVariable("id") Long id) {
		return ratingService.getRating(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RatingDTO createRating(@RequestBody RatingDTO rating) {
		return ratingService.addRating(rating);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRating(@PathVariable("id") Long id) {
		ratingService.deleteRatingById(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public RatingDTO updateRating(@RequestBody RatingDTO rating, @PathVariable("id") Long id) {
		return ratingService.modifyRatingById(rating, id);
	}
}
