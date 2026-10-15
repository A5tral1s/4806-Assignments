package org.example.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Used to convert Java objects to JSON strings

    @Test
    public void testCreateAndGetAddressBook() throws Exception {
        AddressBook addressBook = new AddressBook("testing");
        System.out.println(addressBook.getId());
        addressBook.setId(1L);
        System.out.println(addressBook.getId());

        String payload = objectMapper.writeValueAsString(addressBook);

        mockMvc.perform(post("/addressBooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));

        this.mockMvc.perform(post("/buddies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Steve\", \"phoneNumber\": \"12312\", \"}"))
                .andExpect(status().isCreated());

        BuddyInfo newBuddy = new BuddyInfo("John Doe", 12313, addressBook);

        mockMvc.perform(post("/buddyInfoes/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBuddy)))
                .andExpect(status().isOk());

        //mockMvc.perform(post("/addressBooks"))
        mockMvc.perform(get("/addressBooks/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.buddies[0].firstName").value("Steve"))
                .andExpect(jsonPath("$.buddies[1].firstName").value("John"));
    }

    @Test
    public void testAddBuddyToAddressBook() throws Exception {
        // Create a mock BuddyInfo object
        AddressBook book = new AddressBook("testing");
        book.setId(1L);
        BuddyInfo newBuddy = new BuddyInfo("John Doe", 12313, book);

        // 3. Test POST: Add a BuddyInfo to an AddressBook (Assuming AddressBook ID is 1)
        mockMvc.perform(post("/addressBooks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBuddy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John Doe"))
                .andExpect(jsonPath("$.phoneNumber").value(12313));

        // 4. Test GET: Retrieve buddies from AddressBook 1
        mockMvc.perform(get("/addressBooks/1/buddies")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John Doe"));
    }
}
