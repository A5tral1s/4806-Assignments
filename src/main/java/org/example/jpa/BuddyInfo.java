package org.example.jpa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class BuddyInfo implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private Integer phoneNumber;
    private String address;

    public BuddyInfo() {
        firstName = "Carl";
        phoneNumber = 1231;
        address = "Null Street";
    }

    public BuddyInfo(String firstName, Integer phoneNumber, String address, AddressBook book) {
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.book = book;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties("buddies")
    private AddressBook book;

    @Override
    public String toString() {
        return String.format(
                "Buddy[id=%d, firstName='%s', phone number='%s']",
                id, firstName, phoneNumber);
    }

    public AddressBook getBook() {
        return book;
    }

    public void setBook(AddressBook book) {
        this.book = book;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPhoneNumber(Integer phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
