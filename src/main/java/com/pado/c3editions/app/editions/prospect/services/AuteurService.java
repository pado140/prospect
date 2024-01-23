package com.pado.c3editions.app.editions.prospect.services;

import com.pado.c3editions.app.editions.prospect.exception.ContactException;
import com.pado.c3editions.app.editions.prospect.pojos.Visites;
import com.pado.c3editions.app.editions.prospect.repositories.AuteursRepository;
import com.pado.c3editions.app.editions.prospect.pojos.Auteurs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuteurService  {

    @Autowired
    private AuteursRepository repository;

    public Auteurs save(Auteurs entity) {
//        if(repository.existsByNif(entity.getNif())){
//            throw new ContactException("Auteurs déjà enregistré!", HttpStatus.CONFLICT);
//        }
        if(Objects.nonNull(entity.getVisites()))
            for(Visites v: entity.getVisites())
                v.setContact(entity);

        entity=repository.save(entity);
        return entity;
    }

    public List<Auteurs> save(List<Auteurs> entities) {
        List<Auteurs> exists=entities.stream().filter(Auteurs -> repository.existsByNiu(Auteurs.getNiu())).collect(Collectors.toList());
        entities=entities.stream().dropWhile(Auteurs -> repository.existsByNiu(Auteurs.getNiu())).collect(Collectors.toList());

        if(exists.isEmpty()){
            entities=repository.saveAllAndFlush(entities);
        }
        else throw new ContactException("Certains de ces Auteurs sont deja enregistres!", HttpStatus.CONFLICT);

        return entities;
    }

    public boolean deleteById(Long id) {
        Auteurs emp=repository.findById(id).orElseThrow(() -> new ContactException("Cet auteur n'existe pas", HttpStatus.NOT_FOUND));
        repository.deleteById(id);
        return true;
    }

    public Optional<Auteurs> findById(Long id) {
        return repository.findById(id);
    }

    public List<Auteurs> findAll() {
        List<Auteurs> Contact_all=repository.findAll();
        if(Contact_all.isEmpty())
            throw new ContactException("Aucun auteur trouve",HttpStatus.NOT_FOUND);
        return Contact_all;
    }

    public Page<Auteurs> findAll(Pageable pageable) {
        return null;
    }

    public Auteurs update(Auteurs entity, Long id) {
//        Optional<Auteurs> contact= Optional.ofNullable(
//                repository.findById(id).orElseThrow(
//                        () -> new ContactException("Cet auteur n'existe pas", HttpStatus.NOT_FOUND)));
        entity.setId(id);
        return repository.saveAndFlush(entity);
    }
}
