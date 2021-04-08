package skillbox.javapro11.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import skillbox.javapro11.api.response.PersonResponse;
import skillbox.javapro11.enums.PermissionMessage;
import skillbox.javapro11.service.PersonService;
import skillbox.javapro11.service.ProfileService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @MockBean
    private ProfileService profileService;

    @MockBean
    private SecurityContext securityContext;

    @Test
    @DisplayName("Get current user")
    @SneakyThrows
    //@WithMockUser
    void getCurrentUserTest(){
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
        Mockito.when(profileService.getCurrentUser()).thenReturn(personResponse);

        this.mockMvc.perform(get("/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("string"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.first_name").value("Ivan"))
                .andExpect(jsonPath("$.data.last_name").value("Ivanov"))
                .andExpect(jsonPath("$.data.reg_date").value(localDateTime.toEpochSecond(ZoneOffset.UTC)))
                .andExpect(jsonPath("$.data.birth_date").value(localDateTime.toEpochSecond(ZoneOffset.UTC)))
                .andExpect(jsonPath("$.data.email").value("email"))
                .andExpect(jsonPath("$.data.phone").value("phone"))
                .andExpect(jsonPath("$.data.photo").value("photo"))
                .andExpect(jsonPath("$.data.about").value("about"))
                .andExpect(jsonPath("$.data.city").value("city"))
                .andExpect(jsonPath("$.data.country").value("country"))
                .andExpect(jsonPath("$.data.massage_permission").value("ALL"))
                .andExpect(jsonPath("$.data.last_online_time").value(localDateTime.toEpochSecond(ZoneOffset.UTC)))
                .andExpect(jsonPath("$.data.last_online_time").value(localDateTime.toEpochSecond(ZoneOffset.UTC)))
                .andExpect(jsonPath("$.data.is_blocked").value("false"));
    }

    @Test
    @DisplayName("Edit current user")
    void editCurrentUserTest(){

    }

    @Test
    @DisplayName("Delete current user")
    void deleteCurrentUserTest(){

    }

    @Test
    @DisplayName("Get user by Id")
    void getUserById(){

    }
}
