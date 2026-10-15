package org.example.jpa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("my")
public class MyController {

    @GetMapping("/buddyInfoes/{id}")
    public ResponseEntity<BuddyInfo> getAddressBookById(@PathVariable Long id) {
        return id == 1
                ? ResponseEntity.ok(new BuddyInfo("John Doe", 1, null))
                : ResponseEntity.noContent().build();
    }
}
