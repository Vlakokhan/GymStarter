package com.gymstarter.library.repository;

import com.gymstarter.library.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {
}
