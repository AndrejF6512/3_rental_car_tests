package com.foltan.rentalCarTestApp.repository;

import com.foltan.rentalCarTestApp.domain.CarParameters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarParametersRepository extends JpaRepository<CarParameters, Long> {
}
