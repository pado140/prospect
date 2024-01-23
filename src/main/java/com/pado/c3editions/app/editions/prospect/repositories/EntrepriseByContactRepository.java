package com.pado.c3editions.app.editions.prospect.repositories;

import com.pado.c3editions.app.editions.prospect.pojos.Contact;
import com.pado.c3editions.app.editions.prospect.pojos.Entreprise;
import com.pado.c3editions.app.editions.prospect.pojos.EntrepriseByContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepriseByContactRepository extends JpaRepository<EntrepriseByContact, Long> {
    boolean existsByContact(Contact contact);

    boolean existsByEntrepriseAndContact(Entreprise entreprise, Contact contact);
}