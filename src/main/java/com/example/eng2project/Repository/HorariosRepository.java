package com.example.eng2project.Repository;

import com.example.eng2project.Entity.Horarios;
import com.example.eng2project.Entity.ItinerarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorariosRepository extends JpaRepository<Horarios,Long> {
    List<Horarios> findByItinerarioOrderById (ItinerarioEntity itinerario);
}
