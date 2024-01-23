package com.pado.c3editions.app.editions.prospect.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.pado.c3editions.app.editions.prospect.exception.ContactException;
import com.pado.c3editions.app.editions.prospect.pojos.Contact;
import com.pado.c3editions.app.editions.prospect.pojos.ENUM.CONTACT_TYPE;
import com.pado.c3editions.app.editions.prospect.pojos.Visites;
import com.pado.c3editions.app.editions.prospect.repositories.ContactRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepository repository;


    public Contact save(Contact entity) {
        if(repository.existsByTelephone(entity.getTelephone())){
            throw new ContactException("Contact déjà enregistré!", HttpStatus.CONFLICT);
        }

        if(Objects.nonNull(entity.getVisites()))
            for(Visites v: entity.getVisites())
                v.setContact(entity);

        entity=repository.save(entity);
        return entity;
    }


    public List<Contact> save(List<Contact> entities) {
        List<Contact> exists=entities.stream().filter(Contact -> repository.existsByTelephoneAllIgnoreCase(Contact.getTelephone())).collect(Collectors.toList());
        entities=entities.stream().dropWhile(Contact -> repository.existsByTelephoneAllIgnoreCase(Contact.getTelephone())).collect(Collectors.toList());

        if(exists.isEmpty()){
            entities=repository.saveAllAndFlush(entities);
        }
        else throw new ContactException("Certains de ces Contact sont deja enregistres!", HttpStatus.CONFLICT);

        return entities;
    }


    public boolean deleteById(Long id) {
        Contact emp=repository.findById(id).orElseThrow(() -> new ContactException("Cet contact n'existe pas", HttpStatus.NOT_FOUND));
        repository.deleteById(id);
        return true;
    }

    @CircuitBreaker(name = "myCircuitBreaker",fallbackMethod = "notFound")
    public Optional<Contact> findById(Long id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(() -> new ContactException("Cet contact n'existe pas", HttpStatus.NOT_FOUND)));
    }

    public Optional<Contact> notFound(Throwable throwable){
        return Optional.empty();
    }

    public List<Contact> findAll() {
        List<Contact> Contact_all=repository.findAll();

        return Contact_all;
    }

    public List<Contact> getDemarcheur() {
        List<Contact> demarcheur=repository.getAllByType(CONTACT_TYPE.DEMARCHEUR);
        return demarcheur;
    }

    public List<Contact> getDistributeur() {
        List<Contact> distributeur=repository.getAllByType(CONTACT_TYPE.DISTRIBUTEUR);

        return distributeur;
    }


    public Page<Contact> findAll(Pageable pageable) {
        return null;
    }


    public List<Contact> getBySuccursale(Long id) {
        return repository.findBySuccursale(id);
    }

    
    public Contact update(Contact entity, Long id) {
        Optional<Contact> contact= Optional.ofNullable(
                repository.findById(id).orElseThrow(
                        () -> new ContactException("Cet contact n'existe pas", HttpStatus.NOT_FOUND)));
        entity.setId(id);
        return repository.saveAndFlush(entity);
    }

    public int changeContactTo(Contact contact,String cl,String cl_type){
        return repository.transformContact(cl,cl_type, contact.getId());
    }

    public int changeContactToAuteur(String nif, String niu, String NP, LocalDate dob, String pob, String prof, Long id){
        return repository.transformContactAuteur("AUTEUR",nif, niu,  NP, dob,  pob, prof, id);
    }

    public int initContactToAuteur(Long id){
        return repository.initContactAuteur("AUTEUR", id);
    }

}
