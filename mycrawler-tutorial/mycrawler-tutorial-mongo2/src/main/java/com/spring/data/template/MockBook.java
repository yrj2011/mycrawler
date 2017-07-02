package com.spring.data.template;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ghazi Naceur on 27/06/2017.
 */
@Document(collection = "mockbook")
public class MockBook {

    @Id
    private String bookId;

    @Field(value = "title")
    private String title;

    private Date publishDate;

    private int pageCount;

    private BigDecimal price;

    private Author author;

    private List<String> tags = new ArrayList<>();

    public MockBook() {
        super();
    }

    public MockBook(String bookId, String title, Date publishDate, int pageCount, BigDecimal price, Author author, List<String> tags) {
        this.bookId = bookId;
        this.title = title;
        this.publishDate = publishDate;
        this.pageCount = pageCount;
        this.price = price;
        this.author = author;
        this.tags = tags;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "MockBook{" +
                "bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", publishDate=" + publishDate +
                ", pageCount=" + pageCount +
                ", price=" + price +
                ", author=" + author +
                ", tags=" + tags +
                '}';
    }
}
