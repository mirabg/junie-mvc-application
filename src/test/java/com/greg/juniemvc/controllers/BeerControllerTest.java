package com.greg.juniemvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greg.juniemvc.entities.Beer;
import com.greg.juniemvc.services.BeerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
@Import(BeerControllerTest.TestConfig.class)
class BeerControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        BeerService beerService() {
            return Mockito.mock(BeerService.class);
        }
    }

    @Autowired
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCreateBeer() throws Exception {
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

        when(beerService.saveBeer(any(Beer.class))).thenReturn(savedBeer);

        // When/Then
        mockMvc.perform(post("/api/v1/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.beerName", is("Test Beer")))
                .andExpect(jsonPath("$.beerStyle", is("IPA")))
                .andExpect(jsonPath("$.upc", is("123456")))
                .andExpect(jsonPath("$.price", is(12.99)))
                .andExpect(jsonPath("$.qtyOnHand", is(100)));

        verify(beerService, times(1)).saveBeer(any(Beer.class));
    }

    @Test
    void testGetBeerById_Found() throws Exception {
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

        when(beerService.getBeerById(beerId)).thenReturn(Optional.of(beer));

        // When/Then
        mockMvc.perform(get("/api/v1/beers/{id}", beerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.beerName", is("Test Beer")))
                .andExpect(jsonPath("$.beerStyle", is("IPA")));

        verify(beerService, times(1)).getBeerById(beerId);
    }

    @Test
    void testGetBeerById_NotFound() throws Exception {
        // Given
        Integer beerId = 999;
        when(beerService.getBeerById(beerId)).thenReturn(Optional.empty());

        // When/Then
        mockMvc.perform(get("/api/v1/beers/{id}", beerId))
                .andExpect(status().isNotFound());

        verify(beerService, times(1)).getBeerById(beerId);
    }

    @Test
    void testGetAllBeers() throws Exception {
        // Given
        List<Beer> beers = Arrays.asList(
            Beer.builder().id(1).beerName("Beer One").beerStyle("IPA").build(),
            Beer.builder().id(2).beerName("Beer Two").beerStyle("Stout").build()
        );

        when(beerService.getAllBeers()).thenReturn(beers);

        // When/Then
        mockMvc.perform(get("/api/v1/beers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].beerName", is("Beer One")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].beerName", is("Beer Two")));

        verify(beerService, times(1)).getAllBeers();
    }
}
