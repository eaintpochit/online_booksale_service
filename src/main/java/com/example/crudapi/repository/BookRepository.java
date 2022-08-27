package com.example.crudapi.repository;

import com.example.crudapi.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Id> {

    @Transactional
    @Modifying
    @Query("delete from Book b where b.bookId = :bookId")
    void deleteBook(@Param("bookId") String bookId);

    @Query("select b from Book b where b.bookId = :bookId")
    Book searchById(@Param("bookId") String bookId);

    @Query("select b from Book b where b.bookName = :bookName")
    List<Book> searchByBookName(@Param("bookName") String bookName);

    @Query("select b from Book b where b.author = :author")
    List<Book> searchByAuthor(@Param("author") String author);

    @Query("select b from Book b where b.author = :author and b.bookName = :bookName")
    Book searchByAuthorAndBookName(@Param("author") String author, @Param("bookName") String bookName);

}
