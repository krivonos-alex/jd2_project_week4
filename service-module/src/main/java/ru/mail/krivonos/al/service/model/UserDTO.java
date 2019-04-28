package ru.mail.krivonos.al.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 70)
    private String username;

    @NotNull
    @Size(min = 1, max = 20)
    private String password;

    @NotNull
    private RoleDTO role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }
}
