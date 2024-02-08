package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Book> getAllEmployees() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getOne(@PathVariable int id){
        Book book = this.bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found"));
        return ResponseEntity.ok(book);
    }
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Publisher tmpPublisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Could not find Publisher"));
        book.setPublisher(tmpPublisher);
        Author tmpAuthor = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Could not find author"));
        book.setAuthor(tmpAuthor);
        return new ResponseEntity<Book>(this.bookRepository.save(book), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book){
        Book book1 = this.bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Id Not found"));

        book1.setTitle(book.getTitle());
        book1.setGenre(book.getGenre());

        Publisher tmpPublisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Could not find Publisher"));
        book1.setPublisher(tmpPublisher);
        Author tmpAuthor = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Could not find author"));
        book1.setAuthor(tmpAuthor);

        return new ResponseEntity<Book>(this.bookRepository.save(book1), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that ID was found"));
        this.bookRepository.delete(book);
        return ResponseEntity.ok(book);
    }
}
