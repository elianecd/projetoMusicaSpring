package com.example.projetomusicafinal.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Avaliacao_Musica_Table")

public class AvaliacaoMusica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "musica_id")
    private Long musicaId;

    @Column(name = "nota")
    private Integer nota;

}
