package com.example.eng2project.Entity;

import com.example.eng2project.User.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
public class FormUserItinerario {

    private long itinerario;
    private long horarios;
    private long user;

}
