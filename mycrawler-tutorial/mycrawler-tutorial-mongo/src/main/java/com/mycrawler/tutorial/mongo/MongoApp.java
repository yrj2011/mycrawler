package com.mycrawler.tutorial.mongo;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import com.mongodb.Mongo;

public class MongoApp {
	public static void main(String[] args) throws Exception {

	    MongoOperations mongoOps = new MongoTemplate(new Mongo(), "database");
	    mongoOps.insert(new Person("Joe", 34));

	   System.out.println(mongoOps.findOne(new Query(where("name").is("Joe")), Person.class));

	    mongoOps.dropCollection("person");
	  }
}
