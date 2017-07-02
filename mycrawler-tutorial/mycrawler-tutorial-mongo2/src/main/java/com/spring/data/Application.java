package com.spring.data;

import com.spring.data.configuration.Config;
import com.spring.data.deserializer.LocationRepository;
import com.spring.data.deserializer.PlaceCoordination;
import com.spring.data.fulltext.search.DummyBook;
import com.spring.data.fulltext.search.DummyBookRepository;
import com.spring.data.geospatial.features.FakeBook;
import com.spring.data.geospatial.features.FakeBookRepository;
import com.spring.data.geospatial.features.Library;
import com.spring.data.repository.BookRepository;
import com.spring.data.template.Author;
import com.spring.data.template.MockBook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by Ghazi Naceur on 26/06/2017.
 */
public class Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        // Using Mongo Client
//        MongoClient client = (MongoClient) context.getBean("mongoClient");
//        DB db = client.getDB("sandbox");
//        DBCollection collection = db.getCollection("book");
//        collection.insert(new BasicDBObject().append("character","Isaac Netero"));

        // Using Mongo Facory
//        MongoDbFactory factory = context.getBean(MongoDbFactory.class);
//        DB db = factory.getDb();
//        DBCollection collection = db.getCollection("person");
//        collection.insert(new BasicDBObject().append("character","Isaac Netero"));

        // Using Mongo Template
//        MongoOperations template = context.getBean(MongoTemplate.class);
//        MockBook book = new MockBook("id1","A useful book", new Date(),300,new BigDecimal(8.500));
//        template.insert(book);
//        //Then we get rid of the javax.persistence.@Annotations (@Id, GeneratedValue ...) and we substitute them with
//        // the org.springframework.data.@Annotations

        // Inserting documents
//        List<MockBook> books = new ArrayList<>();
//        books.add(new MockBook("id1", "A useful book", new Date(), 300, new BigDecimal(8.500), new Author(), new ArrayList<>()));
//        books.add(new MockBook("id2", "A useful book", new Date(), 300, new BigDecimal(8.500), new Author(), new ArrayList<>()));
//        books.add(new MockBook("id3", "A useful book", new Date(), 300, new BigDecimal(8.500), new Author(), new ArrayList<>()));
//        books.add(new MockBook("id4", "A useful book", new Date(), 300, new BigDecimal(8.500), new Author(), new ArrayList<>()));
//        books.add(new MockBook("id5", "A useful book", new Date(), 300, new BigDecimal(8.500), new Author(), new ArrayList<>()));
//        MongoOperations template = context.getBean(MongoTemplate.class);
//        template.insert(books, MockBook.class);

        // Saving documents - upsert behavior - inserting an entity not a collection
//        MockBook book = new MockBook("id1", "A useful book 2", new Date(), 300, new BigDecimal(8.500), new Author(), new ArrayList<>());
//        MongoOperations template = context.getBean(MongoTemplate.class);
//        template.save(book);
//        BasicQuery query = new BasicQuery("{title:'A useful book 2'}");
//        MockBook book1 = template.find(query,MockBook.class).get(0);
//        book1.setTitle("the new title");
//        template.save(book1);
//        System.out.println(book1.toString());

        // Updating documents
//        MongoOperations template = context.getBean(MongoTemplate.class);
//        Query query = query(where("title").is("the new title"));
//        Update update = update("title","new value"); //new Update().set("title","new value");
//        template.updateFirst(query,update,MockBook.class);

//        //Query query1 = new Query(); // return all
//        template.save(new MockBook("id4", "the new title", new Date(), 300, new BigDecimal(8.500), new Author(), new ArrayList<>()));
//        template.save(new MockBook("id5", "the new title", new Date(), 300, new BigDecimal(8.500), new Author(), new ArrayList<>()));
//        template.save(new MockBook("id6", "the new title", new Date(), 300, new BigDecimal(8.500), new Author(), new ArrayList<>()));
//        template.updateMulti(query,update,MockBook.class);

        // Upserting documents
//        MongoOperations template = context.getBean(MongoTemplate.class);
//        Query query = new Query(where("title").is("a title !")
//                .and("author.firstName").is("Someone")
//                .and("author.lastName").is("human"));
//        Update update = update("pageCount",1000);
//        template.upsert(query,update,MockBook.class);

        // Removing documents
//        MongoOperations template = context.getBean(MongoTemplate.class);
//        Query query = new Query(where("title").is("a title !")
//                .and("author.firstName").is("Someone")
//                .and("author.lastName").is("human"));
//        template.remove(query, MockBook.class);

        // Retrieving documents
//        MongoOperations template = context.getBean(MongoTemplate.class);
//        MockBook book = new MockBook("id7", "A book", new Date(), 300,
//                new BigDecimal(8.500), new Author("per1","unknown","known",45,
//                "The 3rd street in the middle of nowhere city"), new ArrayList<>());
//        template.save(book);
//        MockBook b = template.findById(book.getBookId(), MockBook.class);
//        MockBook b = template.findOne(new Query(where("bookId").is(book.getBookId()).and("author.lastName").is("known")), MockBook.class);
//        System.out.println(b.toString());

        // Criteria and Query objects
//        MongoOperations template = context.getBean(MongoTemplate.class);
//        Criteria criteria = Criteria.where("title").is("A book 9");
//        Criteria criteria = Criteria.where("title").regex(Pattern.compile(".*book.*"));
//        Query query = new Query(criteria).addCriteria(where("pageCount").gt(800));
//        List<MockBook> books = template.find(query, MockBook.class);
//        books.forEach(book -> System.out.println(book.toString()));

        // Java Configuration
//        ApplicationContext contextWithJavaConfig = new AnnotationConfigApplicationContext(Config.class);
//        MongoOperations template = contextWithJavaConfig.getBean(MongoTemplate.class);
//        Criteria criteria = Criteria.where("title").is("A book 9");
//        Criteria criteria = Criteria.where("title").regex(Pattern.compile(".*book.*"));
//        Query query = new Query(criteria).addCriteria(where("pageCount").gt(800));
//        List<MockBook> books = template.find(query, MockBook.class);
//        books.forEach(book -> System.out.println(book.toString()));

        // Mongo Repositories
//        LocationRepository rep = context.getBean(LocationRepository.class);
//        PlaceCoordination coor = new PlaceCoordination("id1",new Point(20.5,30.7));
//        rep.save(coor);

        // Mongo Repository Basics
//        BookRepository repo = context.getBean(BookRepository.class);
//        MockBook book = repo.findByTitle("A book 12");
//        System.out.println(book.toString());

        // Geospatial features
//        FakeBookRepository rep = context.getBean(FakeBookRepository.class);
//        Point point = new Point(20.5,60.80);
//        Distance distance = new Distance(10, Metrics.KILOMETERS);
//        rep.save(new FakeBook("id1","book1",new Date(),100,
//                new BigDecimal(10.000),new Author("per1","f1","l1",41,"add1"),
//                new ArrayList<>(),new Library("lib1","libname1",new Point(20.5,60.80))));
//        List<FakeBook> books = rep.findByLocationCoordsNear(point, distance);
//        books.forEach(fakeBook -> System.out.println(fakeBook.toString()));

        // Full text search
        DummyBookRepository rep = context.getBean(DummyBookRepository.class);
        rep.save(new DummyBook("id1","book1",new Date(),100,
                new BigDecimal(10.000),new Author("per1","f1","l1",41,"add1"),
                new ArrayList<>(),new Library("lib1","libname1",new Point(20.5,60.80)),
                "This is the description of the 1st book. You will find here some unnecessary, useless information." +
                        "This description is used to test the full text search feature provided by MongoDB."));
        rep.save(new DummyBook("id2","book2",new Date(),200,
                new BigDecimal(20.000),new Author("per2","f2","l2",42,"add2"),
                new ArrayList<>(),new Library("lib2","libname2",new Point(30.5,70.80)),
                "This is the description of the 2nd book. It provides some ambiguous information about the book." +
                        "We actually don't see any purpose from this additional field. Well! It will help when exploring the full text search feature."));
        rep.save(new DummyBook("id3","book3",new Date(),300,
                new BigDecimal(30.000),new Author("per3","f3","l3",43,"add3"),
                new ArrayList<>(),new Library("lib3","libname3",new Point(40.5,80.80)),
                "This is the description of the 3rd book. It provides some summary about this great that no one knows anything about. "));
        rep.save(new DummyBook("id4","book4",new Date(),400,
                new BigDecimal(40.000),new Author("per4","f4","l4",44,"add4"),
                new ArrayList<>(),new Library("lib1","libname1",new Point(50.5,90.80)),
                "This is the description of the 4th book. It shows how much the author needs to go to sleep and probably how much he needs to think about writing again."));
        rep.save(new DummyBook("id5","book5",new Date(),500,
                new BigDecimal(50.000),new Author("per5","f5","l5",45,"add5"),
                new ArrayList<>(),new Library("lib5","libname5",new Point(60.5,10.80)),
                "This is the description of the 5th book. It is simply useless."));
//
//        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching("provid");
//        List<DummyBook> books = rep.findAllByOrderByScoreDesc(criteria);
//        books.forEach(b -> System.out.println(b));

        // JSON queries
//        List<DummyBook> books = rep.findLargeBooks(300);
//        books.forEach(b-> System.out.println(b));
    }
}
