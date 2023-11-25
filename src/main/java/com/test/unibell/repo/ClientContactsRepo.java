package com.test.unibell.repo;



import com.test.unibell.entity.ClientContacts;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientContactsRepo extends JpaRepository<ClientContacts, Long> {

}
