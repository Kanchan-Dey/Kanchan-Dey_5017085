package com.app.bookstoreapi.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.app.bookstoreapi.dto.BookDTO;
import com.app.bookstoreapi.entity.Book;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE=Mappers.getMapper(BookMapper.class);
    BookDTO bookToBookDTO(Book book);
    Book bookDTOtoBook(BookDTO bookDTO);
}
