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
@Table(name = "MusicaTable")

public class Musica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //muitas músicas podem estar associados a uma única banda.
    @JoinColumn(name = "banda_id", nullable = false)
    private Banda banda;

    @ManyToOne //muitas músicas podem estar associados a um unico album.
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @Column(name = "nome")
    private String nome;

    @Column(name = "resumo")
    private String resumo;

    @Column(name = "media")
    private Double media = 0.0;

    @Column(name = "duracao")
    private int duracao = 0; //em segundos
}
