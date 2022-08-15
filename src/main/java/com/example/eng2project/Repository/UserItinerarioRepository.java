package com.example.eng2project.Repository;

import com.example.eng2project.Entity.Horarios;
import com.example.eng2project.Entity.ItinerarioEntity;
import com.example.eng2project.Entity.UserItinerarioEntity;
import com.example.eng2project.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserItinerarioRepository extends JpaRepository<UserItinerarioEntity, Long> {
    Optional<List<UserItinerarioEntity>> findByUserAndItinerarioOrderByHorarios(UserEntity user, ItinerarioEntity itinerario);

    Optional<UserItinerarioEntity> findByUserAndItinerarioAndHorarios(UserEntity user, ItinerarioEntity itinerario, Horarios horarios);

    Optional<UserItinerarioEntity> findByHorarios(Horarios horarios);
}
