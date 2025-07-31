package com.greg.juniemvc.services;

import com.greg.juniemvc.entities.Beer;
import com.greg.juniemvc.repositories.BeerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the BeerService interface
 */
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    public BeerServiceImpl(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public Beer saveBeer(Beer beer) {
        return beerRepository.save(beer);
    }

    @Override
    public Optional<Beer> getBeerById(Integer id) {
        return beerRepository.findById(id);
    }

    @Override
    public List<Beer> getAllBeers() {
        return beerRepository.findAll();
    }
}