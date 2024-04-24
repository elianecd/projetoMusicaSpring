package com.example.projetomusicafinal.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AlbumDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String resumo;

    @NotNull
    private Long bandaId;

}

