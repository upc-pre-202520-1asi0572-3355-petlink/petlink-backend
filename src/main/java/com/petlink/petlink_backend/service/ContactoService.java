package com.petlink.petlink_backend.service;

import com.petlink.petlink_backend.entity.Contacto;
import com.petlink.petlink_backend.repository.ContactoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;

    public ContactoService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    public Contacto guardar(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

    public List<Contacto> listar() {
        return contactoRepository.findAll();
    }
}