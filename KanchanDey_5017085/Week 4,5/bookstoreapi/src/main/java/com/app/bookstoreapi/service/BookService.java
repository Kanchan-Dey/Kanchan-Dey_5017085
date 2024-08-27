package com.app.bookstoreapi.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.app.bookstoreapi.entity.Book;
import com.app.bookstoreapi.repo.BookRepository;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepo;

    public List<Book> getAllBooks(){
        return bookRepo.findAll();
    }
    public ResponseEntity<Object> getBookById(Long id){
        Optional<Book> existing=bookRepo.findById(id);
        if(!existing.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with id: "+id+" not found");
        return ResponseEntity.ok(existing);
    }
    public ResponseEntity<Object> getBookByTitle(String title){
        Optional<Book> existing=bookRepo.findByTitle(title);
        if(!existing.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with title: "+title+" not found");
        return ResponseEntity.ok(existing);
    }
    public ResponseEntity<Object> getBookByAuthor(String author){
        Optional<List<Book>> existing=bookRepo.findByAuthor(author);
        if(existing.map(List::size).orElse(0)==0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book of author: "+author+" not present");
        return ResponseEntity.ok(existing);
    }
    public ResponseEntity<Object> getBookByPrice(Double price){
        Optional<List<Book>> existing=bookRepo.findByPrice(price);
        if(existing.map(List::size).orElse(0)==0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book at price: "+price+" not present");
        return ResponseEntity.ok(existing);
    }
    public ResponseEntity<Object> getBookByIsbn(String isbn){
        Optional<Book> existing=bookRepo.findByIsbn(isbn);
        if(!existing.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with isbn: "+isbn+" not present");
        return ResponseEntity.ok(existing);
    }
    public ResponseEntity<String> saveBook(Book book){
        bookRepo.save(book);
        return ResponseEntity.ok("Book Inserted id: "+book.getId());
    }
    public ResponseEntity<String> updateBook(Long id, Book updatedBook){
        Optional<Book> existingBook=bookRepo.findById(id);
        if(existingBook.isPresent()){
            Book book=existingBook.get();
            book.setAuthor(updatedBook.getAuthor());
            book.setIsbn(updatedBook.getIsbn());
            book.setTitle(updatedBook.getTitle());
            book.setPrice(updatedBook.getPrice());
            return ResponseEntity.ok(book.getTitle()+" book updated");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedBook.getTitle()+" book not present");
    }
    public ResponseEntity<String> updateBookPrice(Long id, Book updated) {
        Optional<Book> existing=bookRepo.findById(id);
        if(!existing.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with id: "+id+" not present");
        }
        Book book=existing.get();
        if(updated.getAuthor()!=null)
            book.setAuthor(updated.getAuthor());
        if(updated.getTitle()!=null)
            book.setTitle(updated.getTitle());
        if(updated.getPrice()!=null)
            book.setPrice(updated.getPrice());
        if(updated.getIsbn()!=null)
            book.setIsbn(updated.getIsbn());
        bookRepo.save(book);
        return ResponseEntity.ok("Book with id: "+id+" updated");
    }
    public ResponseEntity<Object> deleteBookById(Long id) {
        Optional<Book> existing=bookRepo.findById(id);
        if(!existing.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with id: "+id+" not found...");
        }
        bookRepo.deleteById(id);
        return ResponseEntity.ok("Book with id: "+id+" deleted successfully !");
    }
    public void deleteBook(){
        bookRepo.deleteAll();
    }
}
