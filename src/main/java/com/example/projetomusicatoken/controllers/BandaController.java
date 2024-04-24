package com.example.projetomusicatoken.controllers;

import com.example.projetomusicatoken.dtos.BandaDTO;
import com.example.projetomusicatoken.dtos.BandaResponseDTO;
import com.example.projetomusicatoken.models.Album;
import com.example.projetomusicatoken.models.AvaliacaoBanda;
import com.example.projetomusicatoken.models.AvaliacaoRequest;
import com.example.projetomusicatoken.models.Banda;
import com.example.projetomusicatoken.repositories.AvaliacaoBandaRepository;
import com.example.projetomusicatoken.services.AlbumService;
import com.example.projetomusicatoken.services.BandaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        banda.setNome(bandaDTO.getNome()); //obtêm os valores do nome e resumo da banda a partir do objeto BandaDTO.
        banda.setResumo(bandaDTO.getResumo());
        bandaService.createBanda(banda);
        return new ResponseEntity<>("Banda " + banda.getNome() + ", " + banda.getResumo() + " criada com sucesso.", HttpStatus.CREATED);
        //ResponseEntity é uma classe fornecida pelo Spring Framework para representar uma resposta HTTP.
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
            avaliacaoBandaRepository.save(avaliacaoBanda); //salva uma instância de AvaliacaoBanda no banco de dados.

            bandaService.updateMedia(banda);

            String mediaFormatada = String.format(enUS, "%.2f", banda.getMedia());

            return new ResponseEntity<>("Banda " + banda.getNome() + " avaliada com sucesso com nota " + nota + ". Média atual: " + mediaFormatada, HttpStatus.CREATED);

        } catch (RuntimeException exception) {
            throw new RuntimeException("Não foi possível avaliar a banda", exception);
        }
    }

    @GetMapping
    public ResponseEntity<?> listarBandas(Pageable pageable) {
        Page<Banda> bandas = bandaService.findAll(pageable); //retorna uma página de bandas de acordo com as configurações de paginação e ordenação fornecidas.

        if (bandas.isEmpty()) {
            return new ResponseEntity<>("Nenhuma banda registrada", HttpStatus.OK);
        }

        List<BandaResponseDTO> response = bandas.stream()//bandas.stream(): transforma a lista bandas em um stream, permitindo operações de manipulação de elementos da lista.
                .map(banda -> new BandaResponseDTO(banda.getId(), banda.getNome(), banda.getResumo(), banda.getMedia()))
                //transforma cada elemento do stream (cada objeto Banda) em um objeto BandaResponseDTO. Para cada objeto Banda, é criado um novo objeto BandaResponseDTO, utilizando seu construtor que recebe o ID, nome, resumo e média da banda.
                .collect(Collectors.toList());
                //Este método collect converte o stream resultante de volta em uma lista. O argumento Collectors.toList() especifica que queremos uma lista como resultado da operação de coleta.

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/albuns")
    public ResponseEntity<?> listarAlbunsDaBanda(@PathVariable Long id, Pageable pageable) {
        Banda banda = bandaService.findById(id);

        if (banda == null) {
            return new ResponseEntity<>("Banda não encontrada", HttpStatus.NOT_FOUND);
        }

        Pageable pageableWithTenItems = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
        //cria um novo objeto Pageable chamado pageableWithTenItems, que mantém o número da página, define que cada página terá no máximo 10 itens e mantém as informações de ordenação, se houver.
        //PageRequest.of(...): é um método estático da classe PageRequest que cria um objeto PageRequest, que implementa a interface Pageable, com base nos parâmetros fornecidos.
        //pageable.getPageNumber(): retorna o índice da página atual baseado na indexação 0, ou seja, a primeira página tem índice 0, a segunda tem índice 1 e assim por diante. Este número de página é utilizado para determinar qual conjunto de dados deve ser retornado para atender à solicitação.
        //10 indica o numero maximo de itens por página.
        //pageable.getSort(): Obtém as informações de ordenação do objeto pageable, se houver. Isso garante que a ordenação seja mantida na página retornada.
        Page<Album> albunsPage = albumService.findAllByBanda(banda, pageableWithTenItems);
        //albumService.findAllByBanda(banda, pageableWithTenItems): Este método chama o serviço albumService para buscar todos os álbuns relacionados à banda especificada, utilizando o objeto pageableWithTenItems para aplicar as configurações de paginação e ordenação.
        //Page<Album> albunsPage: Este é o resultado da chamada do método findAllByBanda. É uma página de objetos Album, que contém os álbuns relacionados à banda de acordo com as configurações de paginação e ordenação fornecidas.

        if (albunsPage.isEmpty()) {
            return new ResponseEntity<>("Nenhum álbum registrado", HttpStatus.OK);
        }

        // Convert the Page<Album> to List<Album>
        List<Album> albuns = albunsPage.getContent(); //Obtém a lista de álbuns da página retornada.
        return new ResponseEntity<>(albuns, HttpStatus.OK);
    }
}
