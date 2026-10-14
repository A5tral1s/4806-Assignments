package org.example.jpa.controller;

import org.example.jpa.repository.AddressBookRepository;
import org.example.jpa.repository.BuddyRepository;
import org.example.jpa.model.AddressBook;
import org.example.jpa.model.BuddyInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buddyInfoes")
public class BuddyInfoController {

    private final BuddyRepository buddyRepository;
    private final AddressBookRepository addressBookRepository;

    public BuddyInfoController(AddressBookRepository addressBookRepository, BuddyRepository buddyRepository) {
        this.addressBookRepository = addressBookRepository;
        this.buddyRepository = buddyRepository;
    }

    @GetMapping
    public List<BuddyInfo> getAllBuddies() {
        return (List<BuddyInfo>) buddyRepository.findAll();
    }

    // Add
    @PostMapping("/book/{bookId}")
    public BuddyInfo addBuddyToBook(@PathVariable Long bookId, @RequestBody BuddyInfo buddy) {
        AddressBook company = addressBookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        buddy.setBook(company);
        return buddyRepository.save(buddy);

    }

}
