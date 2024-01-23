package com.pado.c3editions.app.editions.prospect.services;

import com.pado.c3editions.app.editions.prospect.exception.ContactException;
import com.pado.c3editions.app.editions.prospect.pojos.Contact;
import com.pado.c3editions.app.editions.prospect.pojos.ENUM.CONTACT_TYPE;
import com.pado.c3editions.app.editions.prospect.pojos.EntrepriseByContact;
import com.pado.c3editions.app.editions.prospect.pojos.Visites;
import com.pado.c3editions.app.editions.prospect.repositories.ContactRepository;
import com.pado.c3editions.app.editions.prospect.repositories.EntrepriseByContactRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactEntrepriseService {

    @Autowired
    private EntrepriseByContactRepository repository;

    public Contact save(EntrepriseByContact entity) {
        if(repository.existsByEntrepriseAndContact(entity.getEntreprise(),entity.getContact())){
            throw new ContactException("Contact déjà enregistré!", HttpStatus.CONFLICT);
        }

        entity=repository.save(entity);
        return entity.getContact();
    }

}
