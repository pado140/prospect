package com.pado.c3editions.app.editions.prospect.repositories;

import com.pado.c3editions.app.editions.prospect.pojos.Contact;
import com.pado.c3editions.app.editions.prospect.pojos.ENUM.CONTACT_TYPE;
import com.pado.c3editions.app.editions.prospect.pojos.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    boolean existsByTelephoneAllIgnoreCase(String telephone);

    boolean existsByTelephone(String telephone);

    boolean existsByTelephoneAndSuccursale(String telephone, Long succursale);

//    List<Contact> findByEntreprise(Entreprise entreprise);

    long countBySuccursale(Long succursale);

    @Transactional
    @Modifying
    @Query(value = "update Contact c set c.contact_class = ?1,type=?1,c.ctype= ?2 where c.id = ?3",nativeQuery = true)
    int transformContact(String clas,String type, Long id);

    @Transactional
    @Modifying
    @Query(value = "update Contact c set entreprise_id=NULL, c.contact_class = ?1,type=?1,nif=?2,niu=?3,nom_plume=?4,dob=?5,pob=?6,profession=?7 where c.id = ?8",nativeQuery = true)
    int transformContactAuteur(String clas, String nif, String niu, String NP, LocalDate dob, String pob, String prof, Long id);

    @Transactional
    @Modifying
    @Query(value = "update Contact c set entreprise_id=NULL, c.contact_class = ?1 where c.id = ?2",nativeQuery = true)
    int initContactAuteur(String clas,  Long id);

    @Query("select c from contact c where c.contact_type = ?1")
    List<Contact> getAllByType(CONTACT_TYPE contact_type);


    @Override
    @Query("select c from contact c")
    List<Contact> findAll();

    @Query("select c from contact c where c.succursale = ?1")
    List<Contact> findBySuccursale(Long succursale);
}