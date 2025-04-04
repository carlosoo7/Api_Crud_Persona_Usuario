package com.proyecto.Proyecto.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
@Entity
@Table(name = "Persona", schema= "Proyecto")
public class Persona  implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public Long id;

    @Column(name = "identificacion")
    private long ident;

    @Column(name = "pnombre")
    private String pNombre;

    @Column(name = "snombre")
    private String sNombre;

    @Column(name = "papellido")
    private String pApellido;

    @Column(name = "sapellido")
    private String sApellido;

    @Column(name = "email")
    private String email;

    @Column (name = "fechanacimiento")
    private LocalDate fechaN;

    @Column(name = "edad")
    private int edad;

    @Column(name = "edadclinica")
    private String edadC;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    @JsonIgnore
    Usuario usuario;

}
