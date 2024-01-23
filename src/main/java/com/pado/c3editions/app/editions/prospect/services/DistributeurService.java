package com.pado.c3editions.app.editions.prospect.services;

import com.pado.c3editions.app.editions.prospect.exception.ContactException;
import com.pado.c3editions.app.editions.prospect.pojos.Distributeurs;
import com.pado.c3editions.app.editions.prospect.repositories.DemarcheurRepository;
import com.pado.c3editions.app.editions.prospect.repositories.DistributeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DistributeurService {

    @Autowired
    private DistributeurRepository repository;

    public Distributeurs save(Distributeurs entity) {
        if(repository.existsByTelephone(entity.getTelephone())){
            throw new ContactException("Distributeurs deja enregistre!", HttpStatus.CONFLICT);
        }

        entity=repository.save(entity);
        return entity;
    }

    public List<Distributeurs> save(List<Distributeurs> entities) {
        List<Distributeurs> exists=entities.stream().filter(Distributeurs -> repository.existsByTelephone(Distributeurs.getTelephone())).collect(Collectors.toList());
        entities=entities.stream().dropWhile(Distributeurs -> repository.existsByTelephone(Distributeurs.getTelephone())).collect(Collectors.toList());

        if(exists.isEmpty()){
            entities=repository.saveAllAndFlush(entities);
        }
        else throw new ContactException("Certains de ces Distributeurs sont deja enregistres!", HttpStatus.CONFLICT);

        return entities;
    }

    public boolean deleteById(Long id) {
        Distributeurs emp=repository.findById(id).orElseThrow(() -> new ContactException("Ce demarcheur n'existe pas", HttpStatus.NOT_FOUND));
        repository.deleteById(id);
        return true;
    }

    public Optional<Distributeurs> findById(Long id) {
        return repository.findById(id);
    }

    public List<Distributeurs> findAll() {
        List<Distributeurs> Contact_all=repository.findAll();

        return Contact_all;
    }

    public Page<Distributeurs> findAll(Pageable pageable) {
        return null;
    }

    public Distributeurs update(Distributeurs entity, Long id) {
        Optional<Distributeurs> contact= Optional.ofNullable(
                repository.findById(id).orElseThrow(
                        () -> new ContactException("Ce demarcheur n'existe pas", HttpStatus.NOT_FOUND)));
        entity.setId(id);
        return repository.saveAndFlush(entity);
    }
}
