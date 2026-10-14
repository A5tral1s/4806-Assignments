package org.example.jpa.controller;

import org.example.jpa.model.AddressBook;
import org.example.jpa.model.BuddyInfo;
import org.example.jpa.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/addressBooks")
public class AddressBookViewController {

    private final AddressBookRepository addressBookRepository;

    @Autowired
    public AddressBookViewController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
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
