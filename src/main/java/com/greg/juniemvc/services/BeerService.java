package com.greg.juniemvc.services;

import com.greg.juniemvc.entities.Beer;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Beer operations
 */
public interface BeerService {
    
    /**
     * Save a beer
     * @param beer the beer to save
     * @return the saved beer
     */
    Beer saveBeer(Beer beer);
    
    /**
     * Get a beer by ID
     * @param id the beer ID
     * @return an Optional containing the beer if found
     */
    Optional<Beer> getBeerById(Integer id);
    
    /**
     * Get all beers
     * @return a list of all beers
     */
    List<Beer> getAllBeers();
}