package com.example.bookstore;

import android.os.Parcel;
import android.os.Parcelable;

public class mask implements Parcelable {

    private int id;
    private String name_book;
    private int price;
    private String picture;

    protected mask(Parcel in){
        id = in.readInt();
        name_book = in.readString();
        price = in.readInt();
        picture = in.readString();
    }

    public mask(int id, String name_book, int price, String picture ) {
        this.id = id;
        this.name_book = name_book;
        this.price = price;
        this.picture = picture;
    }

    public static final Creator<mask> CREATOR = new Creator<mask>() {
        @Override
        public mask createFromParcel(Parcel in) {
            return new mask(in);
        }

        @Override
        public mask[] newArray(int size) {
            return new mask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name_book);
        dest.writeInt(price);
        dest.writeString(picture);
    }


    public int getID() { return id; }

    public void setID(int ID) { this.id = id; }

    public String getName() { return name_book; }

    public void setName(String name) { name_book = name_book;}

    public int getPrice() { return price; }

    public void setPrice(int price) { price = price; }

    public String getImage() { return picture; }

    public void setImage(String image) { picture = picture; }
}