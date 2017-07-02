package com.spring.data.converters;

import com.mongodb.DBObject;
import com.spring.data.template.Author;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Ghazi Naceur on 29/06/2017.
 */
public class DbObjectToAuthorConverter implements Converter<DBObject, Author> {
    @Override
    public Author convert(DBObject dbObject) {
        Author author = new Author();
        author.setPersonId((String) dbObject.get("authorId"));
        author.setFirstName(dbObject.get("name").toString().split(" ")[0]);
        author.setLastName(dbObject.get("name").toString().split(" ")[1]);
        author.setAge((Integer) dbObject.get("age"));
        author.setAddress(dbObject.get("address").toString());
        return author;
    }
}
