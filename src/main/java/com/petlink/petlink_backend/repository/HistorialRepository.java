package com.petlink.petlink_backend.repository;

import com.petlink.petlink_backend.entity.HistorialClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistorialRepository extends JpaRepository<HistorialClinico, Long> {
    List<HistorialClinico> findByMascotaId(Long mascotaId);
}