package com.example.eng2project.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "itinerario")
public class ItinerarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_itinerario")
    private long id;

    private String nome;

    @OneToMany(mappedBy = "itinerario",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Horarios> horarios;

}
