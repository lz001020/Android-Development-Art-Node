package com.example.chapter_02.serializable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User  implements Parcelable {
    public int userId;
    private String name;
    private int age;

    public Book book;

    public User(int userId, String name, int age) {
        this.userId = userId;
        this.name = name;
        this.age = age;
    }

    /**
     * 几乎在所有情况下这个方法都应该返回0，仅当当前对象中存在文件描述符时，此方法返回1。
     * @return
     */
    public int describeContents() {

        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeParcelable(book, 0);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        userId = in.readInt();
        name = in.readString();
        age = in.readInt();
        // 由于book是另一个可序列化对象，
        // 所以它的反序列化过程需要传递当前线程的上下文类加载器,否则会报无法找到类的错误。
        book = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }
}

class Book implements Parcelable{

    String bookName;

    public Book(String bookName) {
        this.bookName = bookName;
    }

    protected Book(Parcel in) {
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
        parcel.writeString(bookName);
    }
}