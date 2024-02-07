package ru.clevertec.house.starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.house.config.PostgresSqlContainerInitializer;
import ru.clevertec.house.config.auth.TokenProvider;
import ru.clevertec.house.model.dto.auth.SignUpDto;
import ru.clevertec.house.util.AuthTestBuilder;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerIntegrationTest extends PostgresSqlContainerInitializer {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    public void testHandleEntityNotFoundException() throws Exception {
        UUID uuid = UUID.fromString("e9ae795e-cc7c-48d8-9621-1a441deff956");

        mockMvc.perform(get("/api/houses/" + uuid))
                .andExpect(jsonPath("$.errorMessage").value("Entity not found!"))
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void testHandleEmptyListException() throws Exception {
        String fragment = "k";

        mockMvc.perform(get("/api/search/house/" + fragment))
                .andExpect(jsonPath("$.errorMessage").value("List is empty!"))
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void testHandleInvalidJwtException() throws Exception {
        SignUpDto signUpDto = AuthTestBuilder.builder().build().buildSignUpDto();

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto)))
                .andExpect(jsonPath("$.errorMessage").value("Username is exists"))
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.FOUND.value()));
    }
}
