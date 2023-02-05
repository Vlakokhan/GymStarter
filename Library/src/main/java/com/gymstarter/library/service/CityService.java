package com.gymstarter.library.service;


import com.gymstarter.library.model.City;
import com.gymstarter.library.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CityService {
    List<City> getAll();
}
