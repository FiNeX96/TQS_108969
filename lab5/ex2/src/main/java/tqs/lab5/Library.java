package tqs.lab5;

import java.util.ArrayList;

public class Library {

    private ArrayList<Book> books;


    public Library(){
        this.books = new ArrayList<Book>();
    }

    public void addBook(Book b){
        this.books.add(b);
    }

    public ArrayList<Book> getBooks(){
        return this.books;
    }

    public ArrayList<Book> getBooksByAuthor(String author){
        ArrayList<Book> books = new ArrayList<Book>();
        for (Book b : this.books){
            if (b.getAuthor().equals(author)){
                books.add(b);
            }
        }
        return books;
    }

    public ArrayList<Book> getBooksByTitle(String title){
        ArrayList<Book> books = new ArrayList<Book>();
        for (Book b : this.books){
            if (b.getTitle().equals(title)){
                books.add(b);
            }
        }
        return books;
    }

    public void removeBook(String title){
        ArrayList<Book> books = this.getBooksByTitle(title);
        for (Book b : books){
            this.books.remove(b);
        }
    }

    public ArrayList<Book> getBooksByDate(int year){
        ArrayList<Book> books = new ArrayList<Book>();
        for (Book b : this.books){
            if (b.getDate().getYear() == year){
                books.add(b);
            }
        }
        return books;
    }
    
}
