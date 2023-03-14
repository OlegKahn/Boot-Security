package ru.kata.spring.boot_security.demo.model;

import java.util.List;

public class UsersCreationDto {
    private List<User> users;

    // default and parameterized constructor

    public void addBook(User user) {
        this.users.add(user);
    }

    @Override
    public String toString() {
        return users.toString();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
