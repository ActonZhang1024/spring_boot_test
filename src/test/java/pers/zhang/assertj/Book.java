package pers.zhang.assertj;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/22 9:20 下午
 * @Version 1.0
 */
public class Book {

    private String name;
    private String author;
    private Double price;

    public Book(String name, String author, Double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
