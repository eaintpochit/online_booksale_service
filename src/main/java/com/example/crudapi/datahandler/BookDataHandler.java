package com.example.crudapi.datahandler;

import com.example.crudapi.domain.Book;
import com.example.crudapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BookDataHandler {

    @Autowired
    BookRepository bookRepo;


    public Book valideSaveBook(String id, String author, String bookName) {

        Book bookById = bookRepo.searchById(id);
        Book bookByAuthorAndName = bookRepo.searchByAuthorAndBookName(author, bookName);

        if (bookById != null)
            return bookById;

        return bookByAuthorAndName;
    }

    public List<Book> showAllBook() {

        List<Book> bkList = bookRepo.findAll();
        return bkList == null ? null : bkList;

    }

    public Object showBookByUserInputData(String id, String bkName, String author) {

        Object objBook = null;

        if (!id.isEmpty())
            objBook = bookRepo.searchById(id);
        if (!bkName.isEmpty())
            objBook = bookRepo.searchByBookName(bkName);
        if (!author.isEmpty())
            objBook = bookRepo.searchByAuthor(author);

        return objBook == null ? null : objBook;
    }

    public Book prepareBookforpurchase(String bookId) {
        Book book = bookRepo.searchById(bookId);
        return book == null ? null : book;
    }
}
