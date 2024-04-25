package com.example.projetomusicafinal.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "AlbumTable")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @JsonManagedReference
    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY) //significa que a lista de músicas associadas a um álbum específico só será carregada quando você explicitamente chamar album.getMusicas().
    //Isso é útil para melhorar a performance da aplicação, pois evita carregar dados desnecessários do banco de dados, especialmente quando se trabalha com grandes volumes de dados ou relações entre entidades que podem não ser necessárias imediatamente.
    private List<Musica> musicas = new ArrayList<>();
}
