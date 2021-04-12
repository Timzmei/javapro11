package skillbox.javapro11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import skillbox.javapro11.api.request.PostRequest;
import skillbox.javapro11.api.response.CommonListResponse;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.PersonResponse;
import skillbox.javapro11.api.response.StatusMessageResponse;
import skillbox.javapro11.enums.PermissionMessage;
import skillbox.javapro11.service.PersonService;
import skillbox.javapro11.service.ProfileService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

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

    @MockBean
    private ProfileService profileService;

    @MockBean
    private SecurityContext securityContext;

    @Test
    @DisplayName("Get current user")
    @SneakyThrows
    @WithMockUser
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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.first_name").value("Ivan"))
                .andExpect(jsonPath("$.last_name").value("Ivanov"))
                //.andExpect(jsonPath("$.reg_date").value(localDateTime.toEpochSecond(ZoneOffset.UTC)))
                //.andExpect(jsonPath("$.birth_date").value(localDateTime.toEpochSecond(ZoneOffset.UTC)))
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.phone").value("phone"))
                .andExpect(jsonPath("$.photo").value("photo"))
                .andExpect(jsonPath("$.about").value("about"))
                .andExpect(jsonPath("$.city").value("city"))
                .andExpect(jsonPath("$.country").value("country"))
                //.andExpect(jsonPath("$.massage_permission").value("ALL"))
                //.andExpect(jsonPath("$.last_online_time").value(localDateTime.toEpochSecond(ZoneOffset.UTC)))
                //.andExpect(jsonPath("$.last_online_time").value(localDateTime.toEpochSecond(ZoneOffset.UTC)))
                .andExpect(jsonPath("$.is_blocked").value("false"));
        //TODO: Person response class return time in not log format, need or change return type, or edit test.
        //TODO: Massage permission has no value
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

    @Test
    @DisplayName("Get users wall")
    @WithMockUser
    void getPostsUserWall() throws Exception {
        long offset = 0L;
        int itemPerPage = 2;
        CommonListResponse response = new CommonListResponse();
        response.setError("string");
        response.setPerPage(itemPerPage);
        response.setOffset(offset);

        Mockito.when(profileService.getUserWall(1L, offset, itemPerPage))
                .thenReturn(response);

        mockMvc.perform(get("/users/1/wall")
                .param("offset", String.valueOf(offset))
                .param("itemPerPage", String.valueOf(itemPerPage)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Add post on user wall")
    @WithMockUser
    void addPostUserWall() throws Exception {

        PostRequest postRequest = new PostRequest();
        String postRequestJSON = new ObjectMapper().writeValueAsString(postRequest);
        Mockito.when(profileService.postOnUserWall(1, 0, postRequest))
                .thenReturn(new CommonResponseData());

        mockMvc.perform(post("/users/1/wall")
                .param("publish_date", String.valueOf(0))
                .contentType(MediaType.APPLICATION_JSON)
                .content(postRequestJSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Search user request")
    @WithMockUser
    void searchUser() throws Exception {
        CommonListResponse response = new CommonListResponse();
        long offset = 0L;
        int itemPerPage = 2;
        response.setError("string");
        response.setOffset(offset);
        response.setPerPage(itemPerPage);

        Mockito.when(profileService.searchUser(
                null, null, null, null, null, null, offset, itemPerPage))
                .thenReturn(response);

        mockMvc.perform(get("/users/search")
                .param("offset", String.valueOf(offset))
                .param("itemPerPage", String.valueOf(itemPerPage)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Block user by ID")
    @WithMockUser
    void blockUserById() throws Exception {
        CommonResponseData responseData = new CommonResponseData();
        responseData.setError("string");
        responseData.setData(new StatusMessageResponse("ok"));

        Mockito
                .when(profileService.blockUser(true, 1L))
                .thenReturn(responseData);

        mockMvc.perform(put("/users/block/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("string"))
                .andExpect(jsonPath("$.data.message").value("ok"));
    }

    @Test
    @DisplayName("Unblock user by ID")
    @WithMockUser
    void unblockUserById() throws Exception {
        CommonResponseData responseData = new CommonResponseData();
        responseData.setError("string");
        responseData.setData(new StatusMessageResponse("ok"));

        Mockito
                .when(profileService.blockUser(false, 1L))
                .thenReturn(responseData);

        mockMvc.perform(delete("/users/block/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("string"))
                .andExpect(jsonPath("$.data.message").value("ok"));
    }
}
