package org.example.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class AddressBook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BuddyInfo> buddies = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressBook() {
    }

    public AddressBook(String name) {
        this.name = name;
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void setBuddies(List<BuddyInfo> buddies) {
        this.buddies = buddies;
    }

    public void addBuddy(BuddyInfo buddy) {
        if(buddy != null){
            buddies.add(buddy);
        }
    }

}
