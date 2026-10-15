package org.example.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    final AddressBookRepository addressBookRepository;

    public AddressBookController(AddressBookRepository bookRepository) {
        this.addressBookRepository = bookRepository;
    }

    @GetMapping
    public List<AddressBook> getAllBooks() {
        return addressBookRepository.findAll();
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

    @PostMapping("/{id}/BuddyInfo")
    public BuddyInfo createBuddy(@PathVariable Long id, @RequestBody BuddyInfo buddyInfo) {
        AddressBook addressBook = addressBookRepository.getReferenceById(id);
        addressBook.addBuddy(buddyInfo);
        addressBookRepository.save(addressBook);
        return buddyInfo;
    }

    /**
     * Displays a list of buddies for a specific address book.
     *
     * @param id    The ID of the address book to view.
     * @param model The UI model to pass data to Thymeleaf.
     * @return      The name of the Thymeleaf template (buddies-list.html).
     */
    @GetMapping("/{id}/view")
    public String listBuddies(@PathVariable("id") Long id, Model model) {
        AddressBook addressBook = addressBookRepository.getReferenceById(id);

        // Add data to the Spring UI Model
        model.addAttribute("addressBookId", id);
        model.addAttribute("buddies", addressBook.getBuddies());

        // Returns the name of the Thymeleaf template: src/main/resources/templates/buddies-list.html
        return "buddies-list";
    }
}