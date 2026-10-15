package org.example.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.RestTestClient;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(BuddyInfoController.class)
class BuddyControllerTest {

    private RestTestClient restTestClient;

    @BeforeEach
    void beforeEach() {
        // TODO: initialize restTestClient
    }
}
