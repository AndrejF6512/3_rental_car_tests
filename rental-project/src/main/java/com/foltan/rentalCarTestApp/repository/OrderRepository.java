package com.foltan.rentalCarTestApp.repository;

import com.foltan.rentalCarTestApp.domain.PlacedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<PlacedOrder, Long> {}
