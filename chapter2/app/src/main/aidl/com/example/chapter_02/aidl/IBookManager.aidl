package com.example.chapter_02.aidl;

import com.example.chapter_02.aidl.Book;


interface IBookManager {
    List<Book> getBookList();

    void addBook(in Book book);
}
