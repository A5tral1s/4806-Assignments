package org.example.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/addressBooks")
public class AddressBookController {

    @Autowired
    final AddressBookRepository addressBookRepository;

    public AddressBookController(AddressBookRepository bookRepository) {
        this.addressBookRepository = bookRepository;
    }

    @GetMapping
    public List<AddressBook> getAllBooks() {
        return (List<AddressBook>) addressBookRepository.findAll();
    }

    @GetMapping("/{id}")
    public List<BuddyInfo> getAllBuddies(@PathVariable Long id) {
        AddressBook addressBook = addressBookRepository.getReferenceById(id);
        return addressBook.getBuddies();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressBook createAddressBook(@RequestBody AddressBook book) {
        return addressBookRepository.save(book);
    }

    @PostMapping("/{id}/buddies")
    public BuddyInfo createBuddy(@PathVariable Long id, @RequestBody BuddyInfo buddyInfo) {
        AddressBook addressBook = addressBookRepository.getReferenceById(id);
        addressBook.addBuddy(buddyInfo);
        addressBookRepository.save(addressBook);
        return buddyInfo;
    }
}