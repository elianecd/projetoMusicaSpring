package com.example.projetomusicafinal.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BandaResponseDTO {

    private Long id;
    private String nome;
    private String resumo;
    private Double media;

}


