package com.devsuperior.dsmovie.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.tests.MovieFactory;
import com.devsuperior.dsmovie.tests.ScoreFactory;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTests {
	
	@InjectMocks
	private ScoreService service;
	
	@Mock
	private ScoreRepository ScoreRepository;
	
	@Mock
	private MovieRepository MovieRepository;
	
	private long existingMovieId, nonExistingMovieId;
	private MovieEntity movie;
	private ScoreEntity score;
	
	@BeforeEach
	void setUp() {
		
		existingMovieId = 1L;
		nonExistingMovieId = 20L;
		movie = MovieFactory.createMovieEntity();
		score = ScoreFactory.createScoreEntity();
	}
	
	/*
	@Test
	public void saveScoreShouldReturnMovieDTO() {
	}
	
	@Test
	public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {
	}
	*/
}
