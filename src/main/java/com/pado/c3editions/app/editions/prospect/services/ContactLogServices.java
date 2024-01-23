package com.pado.c3editions.app.editions.prospect.services;
import com.pado.c3editions.app.editions.prospect.repositories.ContactLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.pado.c3editions.app.editions.prospect.pojos.logs.ContactLogs;

import java.util.List;
import java.util.Optional;

@Service
public class ContactLogServices  {

    @Autowired
    ContactLogsRepository contactLogsRepository;


    public ContactLogs save(ContactLogs entity) {
        return contactLogsRepository.save(entity);
    }


    public List<ContactLogs> save(List<ContactLogs> entities) {
        return contactLogsRepository.saveAll(entities);
    }


    public boolean deleteById(Long id) {
        return true;
    }


    public Optional<ContactLogs> findById(Long id) {
        return Optional.empty();
    }


    public List<ContactLogs> findAll() {
        return null;
    }


    public Page<ContactLogs> findAll(Pageable pageable) {
        return null;
    }


    public ContactLogs update(ContactLogs entity, Long id) {
        return null;
    }
}
