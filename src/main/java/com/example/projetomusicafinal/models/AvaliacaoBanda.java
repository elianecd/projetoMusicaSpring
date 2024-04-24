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
@Table(name = "Avaliacao_Banda_Table")

public class AvaliacaoBanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "banda_id")
    private Long idBanda;

    @Column(name = "nota")
    private Integer nota;

}
