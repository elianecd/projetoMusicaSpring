package com.example.projetomusicafinal.repositories;

import com.example.projetomusicafinal.models.Banda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandaRepository extends JpaRepository<Banda, Long> {
    Page<Banda> findAll(Pageable pageable);
    Banda findByNome(String nome);
}
