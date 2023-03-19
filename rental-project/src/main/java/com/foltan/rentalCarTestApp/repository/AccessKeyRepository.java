package com.foltan.rentalCarTestApp.repository;

import com.foltan.rentalCarTestApp.domain.AccessKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessKeyRepository extends JpaRepository<AccessKey, Long> {
}
