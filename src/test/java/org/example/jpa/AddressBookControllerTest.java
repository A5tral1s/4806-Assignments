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

        mockMvc.perform(post("/AddressBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
        .andExpect(jsonPath("$.id").value(2));

        mockMvc.perform(get("/AddressBook/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddBuddyToAddressBook() throws Exception {
        String payload = objectMapper.writeValueAsString(addressBook);
        String buddyLoad = objectMapper.writeValueAsString(buddyInfo);

        mockMvc.perform(post("/AddressBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(jsonPath("$.id").value(1));

        mockMvc.perform(post("/AddressBook/1/BuddyInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buddyLoad))
                .andExpect(status().isOk());

        /**mockMvc.perform(get("/AddressBook/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("buddies[0].firstName").value("John"));
         **/
    }
}
