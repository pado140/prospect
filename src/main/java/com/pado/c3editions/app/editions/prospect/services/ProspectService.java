package com.pado.c3editions.app.editions.prospect.services;

import com.pado.c3editions.app.editions.prospect.repositories.ClientsRepository;
import com.pado.c3editions.app.editions.prospect.repositories.ContactRepository;
import com.pado.c3editions.app.editions.prospect.repositories.VisitesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProspectService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private VisitesRepository visitesRepository;
    @Autowired
    private ClientsRepository clientsRepository;


}
