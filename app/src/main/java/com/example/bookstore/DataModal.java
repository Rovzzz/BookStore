package com.example.bookstore;

public class DataModal {
    private String picture;
    private String name_book;
    private int price;
    public DataModal(String picture, String name_book, int price){

        this.picture=picture;
        this.name_book=name_book;
        this.price=price;
    }
    public void setImage(String picture)
    {
        this.picture=picture;
    }
    public void setName(String name_book ){this.name_book=name_book;}
    public void setPrice (int price)
    {
        this.price=price;
    }
    public String getImage()
    {
        return picture;
    }
    public String getName()
    {
        return name_book;
    }
    public int getPrice()
    {
        return price;
    }
}
