package com.proyecto.Proyecto.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;


@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
@Entity
@Table(name = "Usuario", schema = "Proyecto")
public class Usuario implements Serializable {
    @Id
    @Column(name = "id")
    public Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "apikey")
    private String apikey;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    private Persona persona;


}
