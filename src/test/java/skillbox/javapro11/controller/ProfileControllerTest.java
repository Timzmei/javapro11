package skillbox.javapro11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import skillbox.javapro11.api.request.ProfileEditRequest;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.PersonResponse;
import skillbox.javapro11.api.response.StatusMessageResponse;
import skillbox.javapro11.enums.PermissionMessage;
import skillbox.javapro11.service.ProfileService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest //(try after creating tests instead @SpringBootTest and @AutoConfigureMvc)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Profile controller test")
public class ProfileControllerTest {

    private final LocalDateTime localDateTime = LocalDateTime.of(2021, 1, 1, 12, 0);
    private final LocalDate localDate = LocalDate.of(2021, 1, 1);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProfileService profileService;

    @Test
    @DisplayName("Get current user")
    @SneakyThrows
    @WithMockUser
    void getCurrentUserTest() {
        PersonResponse personResponse = new PersonResponse(
                1L,
                "Ivan",
                "Ivanov",
                localDateTime,
                localDate,
                "email",
                "phone",
                "photo",
                "about",
                "city",
                "country",
                PermissionMessage.ALL,
                localDateTime,
                false,
                null);
        Mockito.when(profileService.getCurrentUser()).thenReturn(new CommonResponseData(personResponse, "string"));

        this.mockMvc.perform(get("/users/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("string"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.first_name").value("Ivan"))
                .andExpect(jsonPath("$.data.last_name").value("Ivanov"))
                .andExpect(jsonPath("$.data.email").value("email"))
                .andExpect(jsonPath("$.data.phone").value("phone"))
                .andExpect(jsonPath("$.data.photo").value("photo"))
                .andExpect(jsonPath("$.data.about").value("about"))
                .andExpect(jsonPath("$.data.city").value("city"))
                .andExpect(jsonPath("$.data.country").value("country"))
                .andExpect(jsonPath("$.data.messages_permission").value("ALL"))
                .andExpect(jsonPath("$.data.is_blocked").value("false"));
    }

    @Test
    @DisplayName("Edit current user")
    @SneakyThrows
    @WithMockUser
    void editCurrentUserTest() {


        ProfileEditRequest profileEditRequest = new ProfileEditRequest(
                "Petr",
                "Petrov",
                localDate,
                "phone",
                "newPhoto",
                "newAbout",
                "town",
                "country",
                PermissionMessage.FRIEND);

        this.mockMvc.perform(put("/users/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profileEditRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @SneakyThrows
    @DisplayName("Delete current user")
    void deleteCurrentUserTest() {

        CommonResponseData responseData = new CommonResponseData();
        responseData.setError("string");
        responseData.setTimestamp(LocalDateTime.now());
        responseData.setData(new StatusMessageResponse("ok"));

        Mockito.when(profileService.deleteCurrentUser()).thenReturn(responseData);

        this.mockMvc.perform(delete("/users/me"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @SneakyThrows
    @DisplayName("Get user by Id")
    void getUserById() {

        PersonResponse personResponse = new PersonResponse(
                1L,
                "Ivan",
                "Ivanov",
                localDateTime,
                localDate,
                "email",
                "phone",
                "photo",
                "about",
                "city",
                "country",
                PermissionMessage.ALL,
                localDateTime,
                false,
                null);
        Mockito.when(profileService.findUserById(1L)).thenReturn(new CommonResponseData(personResponse, "string"));

        this.mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("string"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.first_name").value("Ivan"))
                .andExpect(jsonPath("$.data.last_name").value("Ivanov"))
                .andExpect(jsonPath("$.data.email").value("email"))
                .andExpect(jsonPath("$.data.phone").value("phone"))
                .andExpect(jsonPath("$.data.photo").value("photo"))
                .andExpect(jsonPath("$.data.about").value("about"))
                .andExpect(jsonPath("$.data.city").value("city"))
                .andExpect(jsonPath("$.data.country").value("country"))
                .andExpect(jsonPath("$.data.messages_permission").value("ALL"))
                .andExpect(jsonPath("$.data.is_blocked").value("false"));
    }
}
