package com.app.bookstoreapi.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.bookstoreapi.entity.Book;
import com.app.bookstoreapi.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable Long id){
        return bookService.getBookById(id);
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<Object> getBookByTitle(@PathVariable String title){
        return bookService.getBookByTitle(title);
    }
    @GetMapping("/author/{author}")
    public ResponseEntity<Object> getBookByAuthor(@PathVariable String author){
        return bookService.getBookByAuthor(author);
    }
    @GetMapping("/price/{price}")
    public ResponseEntity<Object> getBookByPrice(@PathVariable Double price){
        return bookService.getBookByPrice(price);
    }
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Object> getBookByIsbn(@PathVariable String isbn){
        return bookService.getBookByIsbn(isbn);
    }
    @PostMapping
    public ResponseEntity<String> insertBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody Book book){
        return bookService.updateBook(id,book);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateBookPrice(@PathVariable Long id, @RequestBody Book book){
        return bookService.updateBookPrice(id,book);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBookById(@PathVariable Long id){
        return bookService.deleteBookById(id);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteBook(){
        bookService.deleteBook();
        return ResponseEntity.ok("All books deleted successfully");
    }
}
