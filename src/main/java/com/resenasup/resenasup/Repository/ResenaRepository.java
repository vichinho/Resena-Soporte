package com.resenasup.resenasup.repository;

import com.resenasup.resenasup.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByIdProducto(String idProducto);
}