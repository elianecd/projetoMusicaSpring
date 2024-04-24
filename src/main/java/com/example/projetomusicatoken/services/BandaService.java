package com.example.projetomusicatoken.services;

import com.example.projetomusicatoken.models.AvaliacaoBanda;
import com.example.projetomusicatoken.models.Banda;
import com.example.projetomusicatoken.repositories.AvaliacaoBandaRepository;
import com.example.projetomusicatoken.repositories.BandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BandaService { //é a classe de serviço que contém a lógica de negócios para criar uma banda.
    @Autowired //cria uma instância do BandaRepository e a fornece ao BandaService quando o BandaService é criado.
    private BandaRepository bandaRepository;

    @Autowired
    private AvaliacaoBandaRepository avaliacaoBandaRepository;

    public Banda createBanda(Banda banda) { //salva a banda no banco de dados.
        return bandaRepository.save(banda); //o retorno me serve para salvar a instancia da banda na camada de serviço, para q eu tenha acesso a banda salva no banco de dados para futuras operações.
    } //retorna a própria instância salva como resultado da operação.
    //Quando chamamos o método save do JpaRepository, ele insere a entidade no banco de dados se ela ainda não existir,
    // ou atualiza a entidade existente se ela já existir.

    public Banda findById(Long idBanda) {
        return bandaRepository.findById(idBanda).orElse(null);
    }
    //findById é um método fornecido pelo JpaRepository que retorna um Optional contendo a entidade com o ID especificado.

    public void updateMedia(Banda banda) {
        List<AvaliacaoBanda> avaliacoes = avaliacaoBandaRepository.findAllByIdBanda(banda.getId());
        double soma = 0.0;
        for (AvaliacaoBanda avaliacao : avaliacoes) {
            soma += avaliacao.getNota();
        }
        double media = soma / avaliacoes.size();
        banda.setMedia(media); //Define o valor da média calculada no objeto Banda fornecido como parâmetro.
        bandaRepository.save(banda);
    }

    public Page<Banda> findAll(Pageable pageable) {
        return bandaRepository.findAll(pageable);
    }
    // retorna uma página de objetos Banda do banco de dados, de acordo com as configurações de paginação e ordenação fornecidas.
}
