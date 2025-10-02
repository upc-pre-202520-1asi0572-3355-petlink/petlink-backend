package com.petlink.petlink_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.petlink.petlink_backend.entity.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}