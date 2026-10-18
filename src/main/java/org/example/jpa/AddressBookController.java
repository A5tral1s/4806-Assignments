package org.example.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/AddressBook")
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

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressBook createAddressBook(@RequestBody AddressBook book) {
        return addressBookRepository.save(book);
    }

    @PostMapping(path = "/{id}/BuddyInfo")
    public BuddyInfo createBuddy(@PathVariable Long id, @RequestBody BuddyInfo buddyInfo) {
        AddressBook addressBook = addressBookRepository.getReferenceById(id);
        addressBook.addBuddy(buddyInfo);
        addressBookRepository.save(addressBook);
        return buddyInfo;
    }

    @GetMapping("/{id}")
    public ModelAndView listBuddies(@PathVariable("id") Long id, Model model) {
        AddressBook addressBook = addressBookRepository.getReferenceById(id);

        // Add data to the Spring UI Model
        model.addAttribute("addressBookId", id);
        model.addAttribute("buddies", addressBook.getBuddies());

        // Returns the name of the Thymeleaf template: src/main/resources/templates/buddies-list.html
        return new ModelAndView("buddies-list");
    }

    @GetMapping("{id}/add")
    public ModelAndView addAddress() {
        return new ModelAndView("index");
    }

    @PostMapping("{id}/add")
    public ModelAndView addBuddy(@RequestParam("name") String name, @RequestParam("phoneNumber") int phoneNumber, @RequestParam("address") String address, @RequestParam("addressBookId") long id) {
        BuddyInfo buddyInfo = new BuddyInfo();
        buddyInfo.setFirstName(name);
        buddyInfo.setPhoneNumber(phoneNumber);
        AddressBook addressBook = addressBookRepository.getReferenceById(id);
        buddyInfo.setBook(addressBook);
        buddyInfo.setLocation(address);
        addressBook.addBuddy(buddyInfo);
        addressBookRepository.save(addressBook);
        return new ModelAndView("redirect:/AddressBook/" + id);
    }
}