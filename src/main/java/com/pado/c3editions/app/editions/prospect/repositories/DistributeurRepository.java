package com.pado.c3editions.app.editions.prospect.repositories;

import com.pado.c3editions.app.editions.prospect.pojos.Demarcheurs;
import com.pado.c3editions.app.editions.prospect.pojos.Distributeurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributeurRepository extends JpaRepository<Distributeurs, Long> {
//    List<Demarcheurs> findByDepartement(String departement);

    boolean existsByTelephone(String telephone);
}