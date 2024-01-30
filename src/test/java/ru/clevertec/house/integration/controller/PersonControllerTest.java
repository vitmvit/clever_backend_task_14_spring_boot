package ru.clevertec.house.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.clevertec.house.config.PostgresSqlContainerInitializer;
import ru.clevertec.house.config.auth.TokenProvider;
import ru.clevertec.house.exception.EntityNotFoundException;
import ru.clevertec.house.model.dto.PersonDto;
import ru.clevertec.house.model.dto.update.PersonUpdateDto;
import ru.clevertec.house.service.PersonService;
import ru.clevertec.house.service.UserService;
import ru.clevertec.house.util.PersonTestBuilder;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.clevertec.house.constant.Constant.LIMIT;
import static ru.clevertec.house.constant.Constant.OFFSET;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PersonControllerTest extends PostgresSqlContainerInitializer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonService personService;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    void getByUuidShouldReturnExpectedPersonDtoAndStatus200() throws Exception {
        UUID uuid = UUID.fromString("faa3c8d8-b6f8-4100-b253-3cd453a03da7");

        PersonDto expected = personService.getByUuid(uuid);

        mvc.perform(get("/api/persons/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }


    @Test
    public void getByUuidShouldReturnExceptionAndStatus404() throws Exception {
        UUID uuid = UUID.fromString("b1bb5882-a45e-4982-b2bc-3fa626a03edf");

        mvc.perform(get("/api/persons/" + uuid))
                .andExpect(status().isNotFound())
                .andExpect(MvcResult::getResolvedException).getClass().equals(EntityNotFoundException.class);
    }

    @Test
    public void getAllShouldReturnExpectedPagePersonDtoAndStatus200() throws Exception {
        mvc.perform(get("/api/persons?page=" + OFFSET + "&size=" + LIMIT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllResidentsShouldReturnExpectedListHouseDtoAndStatus200() throws Exception {
        UUID uuid = UUID.fromString("8ac37416-6e16-495f-a5ed-28af69e78ea7");

        var expected = personService.getAllHouses(uuid);

        mvc.perform(get("/api/persons/houses/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void updateShouldReturnExpectedPersonDtoAndStatus200() throws Exception {
        PersonUpdateDto personUpdateDto = PersonTestBuilder.builder().build().buildPersonUpdateDto();
        personUpdateDto.setUuid(UUID.fromString("1cd31719-2064-4f90-a909-d7dd3b880d1e"));

        mvc.perform(put("/api/persons")
                        .content(objectMapper.writeValueAsString(personUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void patchShouldReturnExpectedPersonDtoAndStatus200() throws Exception {
        PersonUpdateDto personUpdateDto = PersonTestBuilder.builder().build().buildPersonUpdateDto();
        personUpdateDto.setUuid(UUID.fromString("1cd31719-2064-4f90-a909-d7dd3b880d1e"));

        mvc.perform(patch("/api/persons")
                        .content(objectMapper.writeValueAsString(personUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteShouldReturnStatus204() throws Exception {
        UUID uuid = UUID.fromString("faa3c8d8-b6f8-4100-b253-3cd453a03da7");

        personService.delete(uuid);

        mvc.perform(delete("/api/persons/" + uuid))
                .andExpect(status().isNoContent());
    }
}
