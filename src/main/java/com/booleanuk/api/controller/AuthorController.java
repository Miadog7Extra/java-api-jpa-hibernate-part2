package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Author> getALlPublishers() {
        return this.authorRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        return new ResponseEntity<>(this.authorRepository.save(author), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable int id) {
        return ResponseEntity.ok(this.getAAuthor(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author authorToDelete = this.getAAuthor(id);
        this.authorRepository.delete(authorToDelete);
        return ResponseEntity.ok(authorToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updatePublisher(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = this.getAAuthor(id);
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.getAlive());

        return new ResponseEntity<>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }

    private Author getAAuthor(int id) {
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }
}

