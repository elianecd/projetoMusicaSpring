package com.example.projetomusicatoken.repositories;

import com.example.projetomusicatoken.models.Banda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandaRepository extends JpaRepository<Banda, Long> { //JpaRepository é uma interface do Spring Data JPA que fornece métodos CRUD para a entidade especificada.
//<Banda, Long> especifica que esta interface é para a entidade Banda, onde Banda é a classe da entidade e Long é o tipo do identificador (ID) da entidade.
    Page<Banda> findAll(Pageable pageable); //retorna uma página de todas as bandas no banco de dados, permitindo paginação e ordenação.
    //Page<Banda> retorna uma página de todas as bandas no banco de dados, permitindo paginação e ordenação.
    //findAll(Pageable pageable) é o nome do método. Ele recebe um objeto Pageable como parâmetro, que define as configurações de paginação e ordenação para a consulta.
}