package com.example.StockStick.Repositery;

import com.example.StockStick.Model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<Contact,Long> {

    List<Contact> findByEmail(String email);

    List<Contact> findBySubject(String subject);


    List<Contact> findByFirstNameAndLastName(String firstName, String lastName);


    @Query("SELECT c FROM Contact c WHERE c.timestamp >= :weekAgo ORDER BY c.timestamp DESC")
    List<Contact> findRecentContacts(@Param("weekAgo") String weekAgo);


    List<Contact> findByEmailContaining(String emailPart);

    long countBySubject(String subject);
}
