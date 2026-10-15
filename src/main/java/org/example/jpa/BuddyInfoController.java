package org.example.jpa;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BuddyInfo")
public class BuddyInfoController {

    private final BuddyRepository buddyRepository;
    private final AddressBookRepository addressBookRepository;

    public BuddyInfoController(AddressBookRepository addressBookRepository, BuddyRepository buddyRepository) {
        this.addressBookRepository = addressBookRepository;
        this.buddyRepository = buddyRepository;
    }

    @GetMapping("/{id}")
    public BuddyInfo getAddressBookById(@PathVariable Long id) {
        return buddyRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<BuddyInfo> getAllBuddies() {
        return (List<BuddyInfo>) buddyRepository.findAll();
    }

    @PostMapping(path = "/book/{bookId}")
    public BuddyInfo addBuddyToBook(@PathVariable Long bookId, @RequestBody BuddyInfo buddy) {
        AddressBook book = addressBookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.addBuddy(buddy);
        addressBookRepository.save(book);
        buddy.setBook(book);
        return buddyRepository.save(buddy);
    }

    @PutMapping(path = "/{id}/book")
    public BuddyInfo addToBook(@PathVariable Long bookId, @RequestBody BuddyInfo buddy) {
        AddressBook book = addressBookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.addBuddy(buddy);
        addressBookRepository.save(book);
        buddy.setBook(book);
        return buddyRepository.save(buddy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BuddyInfo addBuddy(@RequestBody BuddyInfo buddy) {
        return buddyRepository.save(buddy);
    }

}
