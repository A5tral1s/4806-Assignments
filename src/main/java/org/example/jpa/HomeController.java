package org.example.jpa;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class HomeController {

    AddressBookRepository  addressBookRepository;

    public HomeController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @GetMapping("/")
    public ModelAndView showAddressBook(Model model) {
        List<AddressBook> addressBooks = addressBookRepository.findAll();
        model.addAttribute("addressBooks", addressBooks);
        return new ModelAndView("index");
    }

    @GetMapping("/add")
    public ModelAndView addAddress() {
        return new ModelAndView("index");
    }

    @PostMapping("/add")
    public ModelAndView addAddress(@RequestParam("name") String name) {
        AddressBook addressBook = new AddressBook(name);
        addressBookRepository.save(addressBook);
        return new ModelAndView("redirect:/");
    }
}
