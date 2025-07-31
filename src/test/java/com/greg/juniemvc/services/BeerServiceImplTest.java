package com.greg.juniemvc.services;

import com.greg.juniemvc.entities.Beer;
import com.greg.juniemvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BeerServiceImplTest {

    @Mock
    BeerRepository beerRepository;

    BeerService beerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        beerService = new BeerServiceImpl(beerRepository);
    }

    @Test
    void testSaveBeer() {
        // Given
        Beer beer = Beer.builder()
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .qtyOnHand(100)
                .build();
        
        Beer savedBeer = Beer.builder()
                .id(1)
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .qtyOnHand(100)
                .build();
        
        when(beerRepository.save(any(Beer.class))).thenReturn(savedBeer);
        
        // When
        Beer result = beerService.saveBeer(beer);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        verify(beerRepository, times(1)).save(any(Beer.class));
    }

    @Test
    void testGetBeerById() {
        // Given
        Integer beerId = 1;
        Beer beer = Beer.builder()
                .id(beerId)
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .qtyOnHand(100)
                .build();
        
        when(beerRepository.findById(beerId)).thenReturn(Optional.of(beer));
        
        // When
        Optional<Beer> result = beerService.getBeerById(beerId);
        
        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(beerId);
        verify(beerRepository, times(1)).findById(beerId);
    }

    @Test
    void testGetBeerById_NotFound() {
        // Given
        Integer beerId = 999;
        when(beerRepository.findById(beerId)).thenReturn(Optional.empty());
        
        // When
        Optional<Beer> result = beerService.getBeerById(beerId);
        
        // Then
        assertThat(result).isEmpty();
        verify(beerRepository, times(1)).findById(beerId);
    }

    @Test
    void testGetAllBeers() {
        // Given
        List<Beer> beers = Arrays.asList(
            Beer.builder().id(1).beerName("Beer One").beerStyle("IPA").build(),
            Beer.builder().id(2).beerName("Beer Two").beerStyle("Stout").build()
        );
        
        when(beerRepository.findAll()).thenReturn(beers);
        
        // When
        List<Beer> result = beerService.getAllBeers();
        
        // Then
        assertThat(result).hasSize(2);
        verify(beerRepository, times(1)).findAll();
    }
}