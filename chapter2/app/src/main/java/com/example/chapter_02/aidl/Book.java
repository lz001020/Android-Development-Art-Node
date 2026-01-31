package com.example.chapter_02.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Book implements Parcelable {
    public int bookId;
    public  String bookName;

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(bookId);
        parcel.writeString(bookName);
    }


    private  final IBookManager.Stub mBinder = new IBookManager.Stub() {
        List<Book> mBookList = Collections.synchronizedList(new ArrayList<>());
        @Override
        public List<Book> getBookList() throws RemoteException {
            synchronized (mBookList) {
                return  mBookList;
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (mBookList) {
                if (!mBookList.contains(book)) {
                    mBookList.add(book);
                }
            }
        }
    };
}
