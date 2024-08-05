package com.library;

public class BookService {
    private BookRepository bookRepository;
    
    public void setBookRepository(BookRepository bookRepository){
        this.bookRepository=bookRepository;
    }
    public void displayBookRepository(){
        System.out.println("The Book Repository is : "+bookRepository);
    }
}
