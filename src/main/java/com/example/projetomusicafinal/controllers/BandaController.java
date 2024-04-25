package com.example.projetomusicafinal.controllers;

import com.example.projetomusicafinal.dtos.AlbumResponseDTO;
import com.example.projetomusicafinal.dtos.BandaDTO;
import com.example.projetomusicafinal.dtos.BandaResponseDTO;
import com.example.projetomusicafinal.dtos.MusicaResponseDTO;
import com.example.projetomusicafinal.models.Album;
import com.example.projetomusicafinal.models.AvaliacaoBanda;
import com.example.projetomusicafinal.models.AvaliacaoRequest;
import com.example.projetomusicafinal.models.Banda;
import com.example.projetomusicafinal.repositories.AvaliacaoBandaRepository;
import com.example.projetomusicafinal.services.AlbumService;
import com.example.projetomusicafinal.services.BandaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bandas")
public class BandaController {
    Locale enUS = new Locale("en", "US");

    @Autowired
    private BandaService bandaService;

    @Autowired
    private AvaliacaoBandaRepository avaliacaoBandaRepository;

    @Autowired
    private AlbumService albumService;

    @PostMapping("/novo-registro")
    public ResponseEntity<String> createBanda(@Valid @RequestBody BandaDTO bandaDTO) {

        if (bandaDTO.getNome() == null || bandaDTO.getResumo() == null) {
            return new ResponseEntity<>("Nome e resumo são obrigatórios", HttpStatus.BAD_REQUEST);
        }

        Banda banda = new Banda(); //A nova instância da classe Banda representa uma nova linha na tabela do banco de
        // dados onde as informações da banda serão armazenadas.
        banda.setNome(bandaDTO.getNome());
        banda.setResumo(bandaDTO.getResumo());
        bandaService.createBanda(banda);
        return new ResponseEntity<>("Banda " + banda.getNome() + ", " + banda.getResumo() + " criada com sucesso.", HttpStatus.CREATED);
    }

    @PostMapping("/{id}/avaliar-banda")
    public ResponseEntity<String> avaliarBanda(@PathVariable(value = "id") Long idBanda, @RequestBody @Valid AvaliacaoRequest avaliacaoRequest) {

        Integer nota = avaliacaoRequest.getNota();

        if (nota == null || nota < 0 || nota > 10) {
            return new ResponseEntity<>("Valor inválido", HttpStatus.BAD_REQUEST);
        }

        try {
            Banda banda = bandaService.findById(idBanda);
            if (banda == null) {
                return new ResponseEntity<>("Banda não encontrada", HttpStatus.NOT_FOUND);
            }

            AvaliacaoBanda avaliacaoBanda = new AvaliacaoBanda();
            avaliacaoBanda.setIdBanda(idBanda);
            avaliacaoBanda.setNota(nota);
            avaliacaoBandaRepository.save(avaliacaoBanda);

            bandaService.updateMedia(banda);

            String mediaFormatada = String.format(enUS, "%.2f", banda.getMedia());

            return new ResponseEntity<>("Banda " + banda.getNome() + " avaliada com sucesso com nota " + nota + ". Média atual: " + mediaFormatada, HttpStatus.CREATED);
        } catch (RuntimeException exception) {
            throw new RuntimeException("Não foi possível avaliar a banda", exception);
        }
    }

    @GetMapping
    public ResponseEntity<?> listarBandas(Pageable pageable) {

        Page<Banda> bandas = bandaService.findAll(pageable);

        if (bandas.isEmpty()) {
            return new ResponseEntity<>("Nenhuma banda registrada", HttpStatus.OK);
        }

        List<BandaResponseDTO> response = bandas.stream()
                .map(banda -> new BandaResponseDTO(banda.getId(), banda.getNome(), banda.getResumo(), banda.getMedia()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/albuns")
    public ResponseEntity<?> listarAlbunsDaBanda(@PathVariable Long id, Pageable pageable) {

        Banda banda = bandaService.findById(id);

        if (banda == null) {
            return new ResponseEntity<>("Nenhuma banda registrada.", HttpStatus.OK);
        }

        Pageable pageableWithTenItems = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());

        Page<Album> albunsPage = albumService.findAllByBanda(banda, pageableWithTenItems);

        if (albunsPage.isEmpty()) {
            return new ResponseEntity<>("Nenhum álbum registrado", HttpStatus.OK);
        }

        // Convert the Page<Album> to List<AlbumResponseDTO>
        List<AlbumResponseDTO> albuns = albunsPage.getContent().stream()
                .map(album -> {
                    AlbumResponseDTO albumDTO = new AlbumResponseDTO();
                    albumDTO.setId(album.getId());
                    albumDTO.setNome(album.getNome());
                    albumDTO.setMedia(album.getMedia());
                    albumDTO.setDuracaoTotal(album.getDuracaoTotal());
                    if (album.getMusicas().isEmpty()) {
                        albumDTO.setMensagem("Não há musicas cadastradas");
                    } else {
                        albumDTO.setMusicas(album.getMusicas().stream().map(musica -> {
                            MusicaResponseDTO musicaDTO = new MusicaResponseDTO();
                            musicaDTO.setId(musica.getId());
                            musicaDTO.setNome(musica.getNome());
                            musicaDTO.setResumo(musica.getResumo());
                            musicaDTO.setMedia(musica.getMedia());
                            musicaDTO.setDuracao(musica.getDuracao());
                            return musicaDTO;
                        }).collect(Collectors.toList()));
                    }
                    return albumDTO;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(albuns, HttpStatus.OK);
    }
}

