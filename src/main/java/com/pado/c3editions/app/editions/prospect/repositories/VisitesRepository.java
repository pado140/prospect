package com.pado.c3editions.app.editions.prospect.repositories;

import com.pado.c3editions.app.editions.prospect.pojos.Visites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitesRepository extends JpaRepository<Visites, Long> {
    @Override
    @Query("select v,c from Visites v inner join v.contact c")
    List<Visites> findAll();

}