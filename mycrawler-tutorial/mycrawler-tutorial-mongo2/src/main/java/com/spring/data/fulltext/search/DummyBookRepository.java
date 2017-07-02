package com.spring.data.fulltext.search;

import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by Ghazi Naceur on 29/06/2017.
 */
public interface DummyBookRepository extends MongoRepository<DummyBook, String> {

    public List<DummyBook> findAllByOrderByScoreDesc(TextCriteria criteria);

    @Query(value = "{'pageCount':{$gt:?0}}")
    public List<DummyBook> findLargeBooks(int pageCount);
}
