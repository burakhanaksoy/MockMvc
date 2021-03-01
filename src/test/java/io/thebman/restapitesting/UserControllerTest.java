package io.thebman.restapitesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.thebman.restapitesting.controllers.UserController;
import io.thebman.restapitesting.service.UserService;
import io.thebman.restapitesting.view.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getUserByIdTest() throws Exception {
        //first thing we need to do:
            // We need to mock the data coming from the service layer (User service)
        User user = new User(1,"Burakhan Aksoy",
                "burak@burak.com","Male",
                "123456789");


        when(userService.getUser(1))
                .thenReturn(user);

        //Secondly,
            // We need to create a mock http request to verify expected response from the REST API
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(jsonPath("$.name").value("Burakhan Aksoy"))
                .andExpect(jsonPath("$.email").value("burak@burak.com"))
                .andExpect(jsonPath("$.phoneNumber").value("123456789"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(status().isOk());
    }

    //Test /users (GET)
    @Test
    public void getMultipleUsersWithDifferentIDValuesAndAssertIDValuesExists() throws Exception {
        List<User> userList = Arrays.asList(
                new User(1,"Burakhan Aksoy",
                        "burak@burak.com","Male",
                        "12321"),
                new User(2,"Wentworth Miller",
                        "wentworth@miller.com","Male",
                        "523543"),
                new User(3,"James Bond",
                        "james@bond.com","Male",
                        "524234"),
                new User(4,"Elliot Alderson",
                        "elliot@elliot.com","Male",
                        "11123355")

        );

        when(userService.getUsers()).thenReturn(userList);

        // Start mock request
         mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.[0]",hasKey("id")))
                .andExpect(jsonPath("$.[0]",hasKey("name")))
                 .andExpect(jsonPath("$.[0]",hasKey("email")))
                .andExpect(jsonPath("$.[0]",hasKey("phoneNumber")))
                .andExpect(jsonPath("$.[1]",hasKey("id")))
                 .andExpect(jsonPath("$.[2]",hasKey("id")))
                 .andExpect(jsonPath("$.[3]",hasKey("id")));
    }

    //Test /users (POST)
    @Test
    public void postUserThenValidateUserAddedSuccessfully() throws Exception {
        User user1 =new User(1,"Burakhan Aksoy",
                "burak@burak.com","Male",
                "12321");

        when(userService.addUser(any(User.class))).thenReturn(user1);

        //Start mocking http request
         mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new User(1,"Burakhan Aksoy",
                        "burak@burak.com","Male",
                        "12321"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Burakhan Aksoy"))
                 .andExpect(jsonPath("$.email").value("burak@burak.com"))
                 .andExpect(jsonPath("$.gender").value("Male"))
                 .andExpect(jsonPath("$.phoneNumber").value("12321"));

//        verify(userService,times(1)).addUser(user1);
//        verifyNoMoreInteractions(userService);
    }
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
