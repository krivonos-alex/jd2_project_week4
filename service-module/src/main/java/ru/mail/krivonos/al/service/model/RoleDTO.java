package ru.mail.krivonos.al.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RoleDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
