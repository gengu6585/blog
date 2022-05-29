package com.zwj.blog.model.Vo;

import com.zwj.blog.exception.TipException;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * @Author:zengwenjie
 * @Date:2021/1/27 21:37
 */
@Import(TipException.class)
@Component
public class Student {
    private String name;
    private Book book;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Book {
        private String name;

        public Book(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "name='" + name + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
