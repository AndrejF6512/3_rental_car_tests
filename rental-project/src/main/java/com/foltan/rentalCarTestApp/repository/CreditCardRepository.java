package com.foltan.rentalCarTestApp.repository;

import com.foltan.rentalCarTestApp.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
