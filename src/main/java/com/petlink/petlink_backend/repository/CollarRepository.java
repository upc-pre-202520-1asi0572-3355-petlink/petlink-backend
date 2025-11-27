package com.petlink.petlink_backend.repository;

import com.petlink.petlink_backend.entity.Collar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollarRepository extends JpaRepository<Collar, Long> {
}