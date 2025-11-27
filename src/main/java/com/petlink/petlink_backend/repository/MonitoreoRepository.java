package com.petlink.petlink_backend.repository;

import com.petlink.petlink_backend.entity.Monitoreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoreoRepository extends JpaRepository<Monitoreo, Long> {
}