package org.example.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class AddressBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Used to convert Java objects to JSON strings

    private AddressBook addressBook;
    private BuddyInfo buddyInfo;

    @BeforeEach
    public void setup() {
        addressBook = new AddressBook();
        addressBook.setName("testing");
        buddyInfo = new BuddyInfo();
        buddyInfo.setFirstName("John");
        buddyInfo.setPhoneNumber(13213);
    }

    @Test
    public void testCreateAndGetAddressBook() throws Exception {
        String payload = objectMapper.writeValueAsString(addressBook);
        String buddyLoad = objectMapper.writeValueAsString(buddyInfo);

        mockMvc.perform(post("/addressBooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
        .andExpect(jsonPath("$.id").value(1));

        mockMvc.perform(post("/addressBooks/1/buddies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(buddyLoad))
                .andExpect(status().isOk());

        mockMvc.perform(get("/addressBooks/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
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
