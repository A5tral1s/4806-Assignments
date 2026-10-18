package org.example.jpa;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
