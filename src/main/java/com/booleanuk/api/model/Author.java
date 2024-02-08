package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private Boolean isAlive;

    @OneToMany(mappedBy = "author")
    @JsonIncludeProperties(value = {"title", "genre"})
    private List<Book> books;

    public Author(){

    }

    public Author(int id){
        this.id = id;
    }

    public Author(String firstName, String lastName, String email, Boolean isAlive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAlive = isAlive;
    }
    public List<Book> getBooks(){
        return books;
    }
    public void setBooks(List<Book> books){
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }
}
