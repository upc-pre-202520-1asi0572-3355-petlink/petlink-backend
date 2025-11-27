package com.petlink.petlink_backend.repository;

import com.petlink.petlink_backend.entity.Mascota;
import com.petlink.petlink_backend.entity.Monitoreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoreoRepository extends JpaRepository<Monitoreo, Long> {
    List<Monitoreo> findByMascota(Mascota mascota);
}