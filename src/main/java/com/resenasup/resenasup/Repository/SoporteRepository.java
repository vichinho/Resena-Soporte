package com.resenasup.resenasup.repository;

import com.resenasup.resenasup.model.Soporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoporteRepository extends JpaRepository<Soporte, Long> {
    List<Soporte> findByIdUsuario(Long idUsuario);
}