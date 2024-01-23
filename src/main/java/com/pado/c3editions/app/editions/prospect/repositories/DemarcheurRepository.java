package com.pado.c3editions.app.editions.prospect.repositories;

import com.pado.c3editions.app.editions.prospect.pojos.Demarcheurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemarcheurRepository extends JpaRepository<Demarcheurs, Long> {
    List<Demarcheurs> findByDepartement(String departement);

    boolean existsByTelephone(String telephone);

    @Override
    List<Demarcheurs> findAll();
}