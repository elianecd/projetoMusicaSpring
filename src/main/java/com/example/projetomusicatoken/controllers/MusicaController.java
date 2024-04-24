package com.example.projetomusicatoken.controllers;

import com.example.projetomusicatoken.dtos.MusicaDTO;
import com.example.projetomusicatoken.models.Album;
import com.example.projetomusicatoken.models.AvaliacaoMusica;
import com.example.projetomusicatoken.models.AvaliacaoRequest;
import com.example.projetomusicatoken.models.Musica;
import com.example.projetomusicatoken.repositories.AvaliacaoMusicaRepository;
import com.example.projetomusicatoken.repositories.MusicaRepository;
import com.example.projetomusicatoken.services.AlbumService;
import com.example.projetomusicatoken.services.MusicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/musicas")
public class MusicaController {
    Locale enUS = new Locale("en", "US");

    @Autowired
    private AlbumService albumService;

    @Autowired
    private MusicaService musicaService;

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private AvaliacaoMusicaRepository avaliacaoMusicaRepository;

    @PostMapping("/novo-registro")
    public ResponseEntity<String> createMusica(@Valid @RequestBody MusicaDTO musicaDTO) {
        if (musicaDTO.getNome() == null || musicaDTO.getResumo() == null || musicaDTO.getDuracao() == null || musicaDTO.getAlbumId() == null) {
            return new ResponseEntity<>("Os campos nome, resumo, duração e album_id são obrigatórios", HttpStatus.BAD_REQUEST);
        }

        Musica musica = new Musica();
        musica.setNome(musicaDTO.getNome());
        musica.setResumo(musicaDTO.getResumo());
        musica.setDuracao(musicaDTO.getDuracao());

        Album album = albumService.findById(musicaDTO.getAlbumId());
        //aqui criei uma instancia album do tipo Album e chamei o metodo findById que está no AlbumService.
        //Esse método recebe um id como parâmetro e retorna um objeto Album que corresponde ao id passado.
        //musicaDTO.getAlbumId() está pegando o id do album do objeto musicaDTO que foi passado como parâmetro.

        if (album == null) {
            return new ResponseEntity<>("Album não encontrado", HttpStatus.BAD_REQUEST);
        }

        musica.setAlbum(album); //está associando a música a um álbum específico.
        musica.setBanda(album.getBanda()); //para associar a música à banda que é dona do álbum.

        musicaService.createMusica(musica); //criar uma nova música no banco de dados usando o serviço de musica (MusicaService).
        albumService.updateDuracaoTotal(album, musica.getDuracao()); //atualizar a duração total do álbum associado à música.

        return new ResponseEntity<>("Música " + musica.getNome() + ", " + musica.getResumo() + " do álbum " + musica.getAlbum().getNome() + " criada com sucesso.", HttpStatus.CREATED);
    }

    @PostMapping("/{id}/avaliar-musica")
    public ResponseEntity<String> avaliarMusica(@PathVariable Long id, @RequestBody AvaliacaoRequest avaliacaoRequest) {
        Integer nota = avaliacaoRequest.getNota();

        if (nota == null || nota < 0 || nota > 10) {
            return new ResponseEntity<>("Nota inválida, a nota deve ser um valor inteiro de 1 a 10.", HttpStatus.BAD_REQUEST);
        }

        try {
            Musica musica = musicaService.findById(id);
            if (musica == null) {
                return new ResponseEntity<>("Música não encontrada", HttpStatus.NOT_FOUND);
            }
            AvaliacaoMusica avaliacaoMusica = new AvaliacaoMusica();
            avaliacaoMusica.setMusicaId(id);
            avaliacaoMusica.setNota(nota);
            avaliacaoMusicaRepository.save(avaliacaoMusica);

            musicaService.updateMedia(avaliacaoMusicaRepository.findByMusicaId(id));

            // Atualizar a média da música
            musica.setMedia(musicaService.getMedia());
            musicaRepository.save(musica);

            String mediaFormatada = String.format(enUS, "%.2f", musica.getMedia());

            return new ResponseEntity<>("Música " + musica.getNome() + " avaliada com sucesso com nota " + nota + ". Média atual: " + mediaFormatada, HttpStatus.CREATED);

        } catch (RuntimeException exception) {
            throw new RuntimeException("Não foi possível avaliar a música", exception);
        }
    }
}
