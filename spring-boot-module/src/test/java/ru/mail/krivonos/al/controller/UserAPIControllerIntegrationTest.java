package ru.mail.krivonos.al.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mail.krivonos.al.service.model.RoleDTO;
import ru.mail.krivonos.al.service.model.UserDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserAPIControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldSaveUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("customer3");
        userDTO.setPassword("customer3");
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_CUSTOMER");
        userDTO.setRole(roleDTO);
        restTemplate.withBasicAuth("admin", "admin");
        ResponseEntity responseEntity = restTemplate
                .withBasicAuth("admin", "admin")
                .postForEntity("http://localhost:8080/api/users", userDTO, ResponseEntity.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void shouldGetBadRequestForExistingUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user");
        userDTO.setPassword("user");
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_CUSTOMER");
        userDTO.setRole(roleDTO);
        ResponseEntity responseEntity = restTemplate
                .withBasicAuth("admin", "admin")
                .postForEntity("http://localhost:8080/api/users", userDTO, ResponseEntity.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
