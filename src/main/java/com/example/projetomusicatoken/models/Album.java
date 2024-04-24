package com.example.projetomusicatoken.models;

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
@Table(name = "AlbumTable")

public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //muitos álbuns podem estar associados a uma única banda.
    @JoinColumn(name = "banda_id", nullable = false)
    private Banda banda;

    @Column(name = "nome")
    private String nome;

    @Column(name = "resumo")
    private String resumo;

    @Column(name = "duracao_total")
    private int duracaoTotal = 0;

    @Column(name = "media")
    private Double media = 0.0;
}
