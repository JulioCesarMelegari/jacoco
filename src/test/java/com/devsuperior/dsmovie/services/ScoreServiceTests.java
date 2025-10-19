package com.devsuperior.dsmovie.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;
import com.devsuperior.dsmovie.entities.ScoreEntityPK;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import com.devsuperior.dsmovie.tests.UserFactory;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTests {
	
	@InjectMocks
	private ScoreService service;
	
	@Mock
	private ScoreRepository scoreRepository;
	
	@Mock
	private MovieRepository movieRepository;
	
	@Mock
	private UserService userService;
	
	private long existingMovieId, nonExistingMovieId;
	private MovieEntity movie;
	private UserEntity user;
	
	@BeforeEach
	void setUp() {
		
		existingMovieId = 1L;
		nonExistingMovieId = 20L;
		movie = MovieFactory.createMovieEntity();
		user = UserFactory.createUserEntity();
		
		Mockito.when(userService.authenticated()).thenReturn(user);
		Mockito.when(movieRepository.findById(existingMovieId)).thenReturn(Optional.of(movie));
		Mockito.when(movieRepository.findById(nonExistingMovieId)).thenReturn(Optional.empty());
		
		 Mockito.when(scoreRepository.saveAndFlush(Mockito.any())).thenAnswer(invocation -> {
	            ScoreEntity s = invocation.getArgument(0);
	            s.setId(new ScoreEntityPK()); // se tiver PK composta
	            return s;
	        });
		
		Mockito.when(movieRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
		Mockito.when(movieRepository.save(Mockito.any())).thenReturn(movie);
	}
	
	
	@Test
	public void saveScoreShouldReturnMovieDTO() {
		
		ScoreDTO dto = new ScoreDTO(existingMovieId, 5.5);
		
		
		MovieDTO result = service.saveScore(dto);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(movie.getId(), result.getId());
		Assertions.assertEquals(5.5, result.getScore(), 0.01);
		Assertions.assertEquals(1, result.getCount());
		System.out.println(result);
		
		Mockito.verify(userService, Mockito.times(1)).authenticated();
		Mockito.verify(movieRepository, Mockito.times(1)).findById(existingMovieId);
		Mockito.verify(scoreRepository, Mockito.times(1)).saveAndFlush(Mockito.any());
		Mockito.verify(movieRepository, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {
		ScoreDTO dto = new ScoreDTO(nonExistingMovieId, 4.5);
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.saveScore(dto);
		});
		
		Mockito.verify(movieRepository, Mockito.times(1)).findById(nonExistingMovieId);
		Mockito.verify(scoreRepository, Mockito.never()).saveAndFlush(Mockito.any());
	}
}
