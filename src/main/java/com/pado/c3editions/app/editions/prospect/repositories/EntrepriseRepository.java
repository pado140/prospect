package com.pado.c3editions.app.editions.prospect.repositories;

import com.pado.c3editions.app.editions.prospect.pojos.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
    boolean existsByMail(String mail);

    boolean existsByNom(String nom);

    boolean existsByTel(String tel);
}