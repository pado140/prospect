package com.pado.c3editions.app.editions.prospect.services;

import com.pado.c3editions.app.editions.prospect.exception.ContactException;
import com.pado.c3editions.app.editions.prospect.repositories.EntrepriseRepository;
import com.pado.c3editions.app.editions.prospect.pojos.Entreprise;
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
public class EntrepriseService {

    @Autowired
    private EntrepriseRepository repository;

 
    public Entreprise save(Entreprise entity) {
        if(repository.existsByNom(entity.getNom())|| repository.existsByTel(entity.getTel())){
            throw new ContactException("Entreprise deja enregistre!", HttpStatus.CONFLICT);
        }

        entity=repository.save(entity);
        return entity;
    }

 
    public List<Entreprise> save(List<Entreprise> entities) {
        List<Entreprise> exists=entities.stream().filter(entity -> repository.existsByMail(entity.getMail())|| repository.existsByNom(entity.getNom())|| repository.existsByTel(entity.getTel())).collect(Collectors.toList());
        entities=entities.stream().dropWhile(entity -> repository.existsByMail(entity.getMail())|| repository.existsByNom(entity.getNom())|| repository.existsByTel(entity.getTel())).collect(Collectors.toList());

        if(exists.isEmpty()){
            entities=repository.saveAllAndFlush(entities);
        }
        else throw new ContactException("Certains de ces entreprises sont deja enregistres!", HttpStatus.CONFLICT);

        return entities;
    }

 
    public boolean deleteById(Long id) {
        Entreprise emp=repository.findById(id).orElseThrow(() -> new ContactException("Cette entreprise n'existe pas", HttpStatus.NOT_FOUND));
        repository.deleteById(id);
        return true;
    }

 
    public Optional<Entreprise> findById(Long id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(() -> new ContactException("Cette entreprise n'existe pas", HttpStatus.NOT_FOUND)));
    }

 
    public List<Entreprise> findAll() {
        List<Entreprise> all=repository.findAll();
        if(all.isEmpty())
            throw new ContactException("Aucune entreprise trouvee",HttpStatus.NOT_FOUND);
        return all;
    }

 
    public Page<Entreprise> findAll(Pageable pageable) {
        return null;
    }

 
    public Entreprise update(Entreprise entity, Long id) {
        Optional<Entreprise> contact= Optional.ofNullable(
                repository.findById(id).orElseThrow(
                        () -> new ContactException("Cette entreprsie n'existe pas", HttpStatus.NOT_FOUND)));
        entity.setId(id);
        return repository.saveAndFlush(entity);
    }

}
