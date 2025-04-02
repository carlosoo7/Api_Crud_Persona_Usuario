package com.proyecto.Proyecto.Entities;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
@Entity
@Table(name = "Usuario", schema = "Proyecto")
public class Usuario implements Serializable {
    @Id
    @Column(name = "idpersona")
    public int id;

    @Column(name = "login")
    private String log;

    @Column(name = "apikey")
    private String aPikey;

}
