package com.learning.java.reactspringboot.controller;

import java.util.*;
import java.util.Optional;

import javax.validation.Valid;
//import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.java.reactspringboot.models.Book;
import com.learning.java.reactspringboot.repositories.mongo.BookMongoRepository;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookMongoRepository _bookRepo;

    //get all books
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return  _bookRepo.findAll();
    }
    //add new book
    @PostMapping("/books/create")
    public Book createBook(@Valid @RequestBody Book book) {
        return _bookRepo.save(book);
    }
    //get book by id
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable("id") String id) {
    	Optional<Book> bookData = _bookRepo.findById(id);
    	if(bookData.isPresent()) {
    		return new ResponseEntity<Book>(bookData.get(), HttpStatus.OK);
    		
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }
    
    //update book by id
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") String id, @RequestBody Book book) {
    	Optional<Book> bookData = _bookRepo.findById(id);
    	if(bookData.isPresent()) {
    		//there is a book to update
    		Book savedBook = bookData.get();
    		savedBook.setTitle(book.getTitle());
    		savedBook.setDescription(book.getDescription());
    		savedBook.setAuthor(book.getAuthor());
    		savedBook.setPublished(book.getPublished());
    		
    		Book updatedBook = _bookRepo.save(savedBook);
    		return new ResponseEntity<Book>(updatedBook,HttpStatus.OK);
    	}
    	else {
    		//there is no book to update
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }
    
    //delete a book
    @DeleteMapping("books/{id}")
    public ResponseEntity<String> deleteBookByID(@PathVariable("id") String id) {
        try {
          _bookRepo.deleteById(id);
        } catch (Exception e) {
          return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
        }
     
        return new ResponseEntity<>("Book has been deleted!", HttpStatus.OK);
    }
    
    @DeleteMapping("books/delete")
    public ResponseEntity<String> deleteBooks() {
        try {
          _bookRepo.deleteAll();
        } catch (Exception e) {
          return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
        }
     
        return new ResponseEntity<>("All books are deleted!", HttpStatus.OK);
    }
    
}