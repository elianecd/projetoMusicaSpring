package com.example.projetomusicafinal.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor //gera automaticamente um construtor com todos os campos da classe como argumentos.
@NoArgsConstructor
@Table(name = "Avaliacao_Album_Table")

public class AvaliacaoAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "album_id")
    private Long albumId;

    @Column(name = "nota")
    private Integer nota;

}
