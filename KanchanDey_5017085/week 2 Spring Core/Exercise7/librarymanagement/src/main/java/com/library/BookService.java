package com.library;

public class BookService {
    private BookRepository bookRepository;
    
    public BookService(BookRepository bookRepository){
        this.bookRepository=bookRepository;
        System.out.println("\nConstructor Injection...\n");
    }
    public void setBookRepository(BookRepository bookRepository){
        this.bookRepository=bookRepository;
        System.out.println("\nSetter Injection....\n");
    }
    public void displayBookRepository(){
        System.out.println("The Book Repository is : "+bookRepository);
    }
}
