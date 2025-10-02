package com.petlink.petlink_backend.repository;

import com.petlink.petlink_backend.entity.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    List<Tratamiento> findByEstado(String estado);

    List<Tratamiento> findByMascota_Id(Long mascotaId);
}