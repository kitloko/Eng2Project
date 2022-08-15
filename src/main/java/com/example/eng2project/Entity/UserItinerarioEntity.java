package com.example.eng2project.Entity;

import com.example.eng2project.User.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_itinerario")
public class UserItinerarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user_itinerario")
    private long id;

    @OneToOne
    @JoinColumn(name = "id_itinerario")
    private ItinerarioEntity itinerario;

    @OneToOne
    @JoinColumn(name = "id_horarios")
    private Horarios horarios;

    @OneToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;

}
