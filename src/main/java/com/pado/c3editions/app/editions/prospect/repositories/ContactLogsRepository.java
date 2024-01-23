package com.pado.c3editions.app.editions.prospect.repositories;

import com.pado.c3editions.app.editions.prospect.pojos.logs.ContactLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactLogsRepository extends JpaRepository<ContactLogs, Long> {

}