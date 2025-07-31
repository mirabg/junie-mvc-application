package com.greg.juniemvc.controllers;

import com.greg.juniemvc.entities.Beer;
import com.greg.juniemvc.services.BeerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Beer operations
 */
@RestController
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    /**
     * Create a new beer
     * @param beer the beer to create
     * @return the created beer
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Beer createBeer(@RequestBody Beer beer) {
        return beerService.saveBeer(beer);
    }

    /**
     * Get a beer by ID
     * @param id the beer ID
     * @return the beer if found, or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Beer> getBeerById(@PathVariable Integer id) {
        Optional<Beer> beerOptional = beerService.getBeerById(id);
        
        return beerOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all beers
     * @return a list of all beers
     */
    @GetMapping
    public List<Beer> getAllBeers() {
        return beerService.getAllBeers();
    }
}