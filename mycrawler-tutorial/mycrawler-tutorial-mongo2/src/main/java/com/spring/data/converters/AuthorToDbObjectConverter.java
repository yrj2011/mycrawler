package com.spring.data.converters;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.spring.data.template.Author;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Ghazi Naceur on 29/06/2017.
 */
public class AuthorToDbObjectConverter implements Converter<Author, DBObject> {
    @Override
    public DBObject convert(Author author) {
        DBObject obj = new BasicDBObject();
        obj.put("authorId",author.getPersonId());
        obj.put("name",author.getFirstName() + " " + author.getLastName());
        obj.put("age",author.getAge());
        obj.put("address",author.getAddress());
        return obj;
    }
}
