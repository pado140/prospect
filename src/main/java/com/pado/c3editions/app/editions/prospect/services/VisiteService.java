package com.pado.c3editions.app.editions.prospect.services;

import com.pado.c3editions.app.editions.prospect.exception.ContactException;
import com.pado.c3editions.app.editions.prospect.pojos.Visites;
import com.pado.c3editions.app.editions.prospect.repositories.VisitesRepository;
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
public class VisiteService {

    @Autowired
    private VisitesRepository repository;


    public Visites save(Visites entity) {
        
        return entity;
    }


    public List<Visites> save(List<Visites> entities) {

        return entities;
    }


    public boolean deleteById(Long id) {

        return true;
    }


    public Optional<Visites> findById(Long id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(() -> new ContactException("Cet Visites n'existe pas", HttpStatus.NOT_FOUND)));
    }


    public List<Visites> findAll() {
        List<Visites> Visites_all=repository.findAll();
        if(Visites_all.isEmpty())
            throw new ContactException("Aucune Visites trouve",HttpStatus.NOT_FOUND);
        return Visites_all;
    }


    public Page<Visites> findAll(Pageable pageable) {
        return null;
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

}
