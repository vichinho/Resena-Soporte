package com.resenasup.resenasup.Repository;

import com.resenasup.resenasup.Model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
}

