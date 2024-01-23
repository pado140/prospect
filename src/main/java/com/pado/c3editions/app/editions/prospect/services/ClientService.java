package com.pado.c3editions.app.editions.prospect.services;

import com.pado.c3editions.app.editions.prospect.exception.ContactException;
import com.pado.c3editions.app.editions.prospect.pojos.Clients;
import com.pado.c3editions.app.editions.prospect.pojos.Visites;
import com.pado.c3editions.app.editions.prospect.repositories.ClientsRepository;
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
public class ClientService {

    @Autowired
    private ClientsRepository repository;

    
    public Clients save(Clients entity) {
        if(repository.existsByTelephone(entity.getTelephone())){
            throw new ContactException("Client deja enregistre!", HttpStatus.CONFLICT);
        }
        if(Objects.nonNull(entity.getVisites()))
            for(Visites v: entity.getVisites())
                v.setContact(entity);

        entity=repository.save(entity);
        return entity;
    }

    
    public List<Clients> save(List<Clients> entities) {
        List<Clients> exists=entities.stream().filter(Client -> repository.existsByTelephone(Client.getTelephone())).collect(Collectors.toList());
        entities=entities.stream().dropWhile(Client -> repository.existsByTelephone(Client.getTelephone())).collect(Collectors.toList());

        if(exists.isEmpty()){
            entities=repository.saveAllAndFlush(entities);
        }
        else throw new ContactException("Certains de ces Clients sont deja enregistres!", HttpStatus.CONFLICT);

        return entities;
    }

    
    public boolean deleteById(Long id) {
        Clients emp=repository.findById(id).orElseThrow(() -> new ContactException("Cet client n'existe pas", HttpStatus.NOT_FOUND));
        repository.deleteById(id);
        return true;
    }

    
    public Optional<Clients> findById(Long id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(() -> new ContactException("Cet client n'existe pas", HttpStatus.NOT_FOUND)));
    }

    
    public List<Clients> findAll() {
        List<Clients> Contact_all=repository.findAll();
        if(Contact_all.isEmpty())
            throw new ContactException("Aucun client trouve",HttpStatus.NOT_FOUND);
        return Contact_all;
    }

    
    public Page<Clients> findAll(Pageable pageable) {
        return null;
    }

    
    public Clients update(Clients entity, Long id) {
        Optional<Clients> contact= Optional.ofNullable(
                repository.findById(id).orElseThrow(
                        () -> new ContactException("Cet contact n'existe pas", HttpStatus.NOT_FOUND)));
        entity.setId(id);
        return repository.saveAndFlush(entity);
    }
}
