package com.gymstarter.library.service.impl;

import com.gymstarter.library.model.City;
import com.gymstarter.library.repository.CityRepository;
import com.gymstarter.library.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }
}
