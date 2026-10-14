package org.example.jpa.controller;

import org.example.jpa.repository.AddressBookRepository;
import org.example.jpa.model.AddressBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/addressBooks")
public class AddressBookController {

    @Autowired
    final AddressBookRepository addressBookRepository;

    public AddressBookController(AddressBookRepository companyRepository) {
        this.addressBookRepository = companyRepository;
    }

    @GetMapping
    public List<AddressBook> getAllBooks() {
        return (List<AddressBook>) addressBookRepository.findAll();
    }

    @PostMapping
    public AddressBook createAddressBook(@RequestBody AddressBook company) {
        return addressBookRepository.save(company);
    }
}