package org.example.jpa.repository;


import org.example.jpa.model.AddressBook;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {

}