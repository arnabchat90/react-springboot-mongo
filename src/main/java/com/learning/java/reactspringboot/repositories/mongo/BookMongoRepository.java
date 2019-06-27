package com.learning.java.reactspringboot.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.learning.java.reactspringboot.models.Book;

public interface BookMongoRepository extends MongoRepository<Book,String> {

}
