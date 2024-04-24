package com.example.projetomusicatoken.repositories;

import com.example.projetomusicatoken.models.Album;
import com.example.projetomusicatoken.models.Banda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Page<Album> findAllByBanda(Banda banda, Pageable pageable);
    //Page<Album>: Indica que o método retornará uma página de objetos do tipo Album. Essa página contém uma lista de álbuns, além de informações sobre a paginação, como o número total de elementos e o número total de páginas.
    //Esse método é responsável por executar a consulta no banco de dados para buscar os álbuns associados à banda especificada, respeitando as configurações de paginação e ordenação fornecidas.
}


