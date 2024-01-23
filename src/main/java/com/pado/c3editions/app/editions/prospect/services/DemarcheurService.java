package com.pado.c3editions.app.editions.prospect.services;

import com.pado.c3editions.app.editions.prospect.exception.ContactException;
import com.pado.c3editions.app.editions.prospect.pojos.Demarcheurs;
import com.pado.c3editions.app.editions.prospect.pojos.Visites;
import com.pado.c3editions.app.editions.prospect.repositories.DemarcheurRepository;
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
public class DemarcheurService {

    @Autowired
    private DemarcheurRepository repository;

    public Demarcheurs save(Demarcheurs entity) {
        if(repository.existsByTelephone(entity.getTelephone())){
            throw new ContactException("Demarcheurs deja enregistre!", HttpStatus.CONFLICT);
        }

        entity=repository.save(entity);
        return entity;
    }

    public List<Demarcheurs> save(List<Demarcheurs> entities) {
        List<Demarcheurs> exists=entities.stream().filter(Demarcheurs -> repository.existsByTelephone(Demarcheurs.getTelephone())).collect(Collectors.toList());
        entities=entities.stream().dropWhile(Demarcheurs -> repository.existsByTelephone(Demarcheurs.getTelephone())).collect(Collectors.toList());

        if(exists.isEmpty()){
            entities=repository.saveAllAndFlush(entities);
        }
        else throw new ContactException("Certains de ces Demarcheurs sont deja enregistres!", HttpStatus.CONFLICT);

        return entities;
    }

    public boolean deleteById(Long id) {
        Demarcheurs emp=repository.findById(id).orElseThrow(() -> new ContactException("Ce demarcheur n'existe pas", HttpStatus.NOT_FOUND));
        repository.deleteById(id);
        return true;
    }

    public Optional<Demarcheurs> findById(Long id) {
        return repository.findById(id);
    }

    public List<Demarcheurs> findAll() {
        List<Demarcheurs> Contact_all=repository.findAll();

        return Contact_all;
    }

    public Page<Demarcheurs> findAll(Pageable pageable) {
        return null;
    }

    public Demarcheurs update(Demarcheurs entity, Long id) {
        Optional<Demarcheurs> contact= Optional.ofNullable(
                repository.findById(id).orElseThrow(
                        () -> new ContactException("Ce demarcheur n'existe pas", HttpStatus.NOT_FOUND)));
        entity.setId(id);
        return repository.saveAndFlush(entity);
    }
}
