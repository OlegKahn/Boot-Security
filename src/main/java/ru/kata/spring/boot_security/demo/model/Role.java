package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;
import javax.persistence.*;
@Embeddable
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 560L;

    @Column
    private String role;

    public Role(){}

    public Role(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + role;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return (obj instanceof Role) && this.role.equals(((Role)obj).role);
        }
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }
}
