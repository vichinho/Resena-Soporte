package com.resenasup.resenasup.Repository;

import com.resenasup.resenasup.Model.Soporte;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SoporteRepository extends JpaRepository<Soporte, String> {
    List<Soporte> findByEstado(String estado);
}