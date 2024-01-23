package com.pado.c3editions.app.editions.prospect.repositories;

import com.pado.c3editions.app.editions.prospect.pojos.Auteurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuteursRepository extends JpaRepository<Auteurs, Long> {
    boolean existsByNiu(String niu);

    boolean existsByNifOrNiu(String nif, String niu);

//    boolean existsByNif(String nif);
}