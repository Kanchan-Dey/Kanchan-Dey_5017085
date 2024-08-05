package com.librarymanagement.librarymanagement.repository;

import com.librarymanagement.librarymanagement.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    
}
