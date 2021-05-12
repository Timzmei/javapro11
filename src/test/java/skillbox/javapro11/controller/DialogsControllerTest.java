package skillbox.javapro11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import skillbox.javapro11.api.request.DialogRequest;
import skillbox.javapro11.enums.PermissionMessage;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.service.DialogsService;
import skillbox.javapro11.service.impl.AccountServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Dialog controller test")
public class DialogsControllerTest {

    private final LocalDateTime localDateTime = LocalDateTime.of(2021, 1, 1, 12, 0);
    private final LocalDate localDate = LocalDate.of(2021, 1, 1);
    private int offset = 0;
    private int itemPerPage = 20;
    Person currentPerson;

    @MockBean
    private AccountServiceImpl accountServiceImpl;

    @MockBean
    private DialogsService dialogsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        Mockito.reset(accountServiceImpl);

        currentPerson = new Person(
                1L,
                "Ivan",
                "Ivanov",
                localDateTime, // registration date
                localDate, // birth date
                "ivanov@mail.com",
                "+7(111)222-33-44",
                "password",
                "photo",
                "about",
                "Russia",
                "Moscow",
                true,
                PermissionMessage.ALL,
                localDateTime, // last online time
                false);

        Mockito.when(accountServiceImpl.getCurrentPerson()).thenReturn(currentPerson);


    }

    @Test
    @DisplayName("Dialogs list")
    @WithMockUser
    void getDialogsListTest() throws Exception {

        //With parameters
        mockMvc.perform(get("/dialogs")
                .param("offset", String.valueOf(offset))
                .param("itemPerPage", String.valueOf(itemPerPage))
                .param("query", ""))
                .andDo(print())
                .andExpect(status().isOk());
        //No parameters
        mockMvc.perform(get("/dialogs"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Create dialog")
    @WithMockUser
    void createDialogTest() throws Exception {

        DialogRequest dialogRequest = new DialogRequest();
        int[] userIds = new int[1];
        userIds[0] = 1;
        dialogRequest.setUsersIds(userIds);

        mockMvc.perform(put("/dialogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dialogRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("unreaded")
    @WithMockUser
    void getCountUnreadMessagesTest() throws Exception {

        mockMvc.perform(get("/dialogs/unreaded"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("add User Into Dialog")
    @WithMockUser
    void addUserIntoDialogTest() throws Exception {

        DialogRequest dialogRequest = new DialogRequest();
        int[] userIds = new int[1];
        userIds[0] = 1;
        dialogRequest.setUsersIds(userIds);
        mockMvc.perform(put("/dialogs/1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dialogRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get invite")
    @WithMockUser
    void getInviteDialogTest() throws Exception {

        mockMvc.perform(get("/dialogs/1/users/invite"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get History Of Messages")
    @WithMockUser
    void getHistoryOfMessagesTest() throws Exception {

        //With parameters
        mockMvc.perform(get("/dialogs/1/messages")
                .param("offset", String.valueOf(offset))
                .param("itemPerPage", String.valueOf(itemPerPage))
                .param("query", ""))
                .andDo(print())
                .andExpect(status().isOk());
        //No parameters
        mockMvc.perform(get("/dialogs/1/messages"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("delete Message")
    @WithMockUser
    void deleteMessageTest() throws Exception {

        mockMvc.perform(delete("/dialogs/1/messages/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("recover Message")
    @WithMockUser
    void recoverMessageTest() throws Exception {

        mockMvc.perform(put("/dialogs/1/messages/1/recover"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("get Status")
    @WithMockUser
    void getStatusTest() throws Exception {

        mockMvc.perform(get("/dialogs/1/activity/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
