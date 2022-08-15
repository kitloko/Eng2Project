package com.example.eng2project.Repository;

import com.example.eng2project.Entity.ItinerarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItinerarioRepository extends JpaRepository<ItinerarioEntity,Long> {
    ItinerarioEntity findByNome(String nome);
}

