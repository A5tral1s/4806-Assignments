package org.example.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class BuddyControllerTest {

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
    public void testCreateAndGetBuddy() throws Exception {
        String payload = objectMapper.writeValueAsString(buddyInfo);

        MvcResult result = mockMvc.perform(post("/BuddyInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(jsonPath("$.phoneNumber").value(13213))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andReturn();

        String contentJson = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(contentJson);
        long generatedId = jsonNode.get("id").asLong();
        mockMvc.perform(get("/BuddyInfo/" + generatedId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value("John"));
    }
}
