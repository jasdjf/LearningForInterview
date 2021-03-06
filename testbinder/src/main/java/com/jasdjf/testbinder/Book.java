package com.jasdjf.testbinder;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    private int bookId;
    private String bookName;
    //private User user;

    public Book(int bookId,String bookName){
        this.bookId = bookId;
        this.bookName = bookName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public int describeContents() {
        return 0;//仅当当前对象中存在文件描述符时返回1，其余情况皆返回0
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
        //dest.writeParcelable(user,0);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>(){

        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel source){
        bookId = source.readInt();
        bookName = source.readString();
        //user = source.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
