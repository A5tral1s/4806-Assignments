package org.example.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class BuddyControllerTest {

    private RestTestClient restTestClient;

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

        mockMvc.perform(post("/buddyInfoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));

        mockMvc.perform(get("/buddyInfoes/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value("John"));
    }
}
