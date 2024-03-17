package tqs.lab5;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class Book{


    private String title;
    private String author;
    private LocalDateTime date;

    public Book(String title, String author, LocalDateTime publish_time){
        this.title = title;
        this.author = author;
        this.date = publish_time;
    }

    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }

    public LocalDateTime getDate(){
        return this.date;
    }

    public String toString(){
        return "Title: " + this.title + " Author: " + this.author + " Date: " + this.date;
    }


}
