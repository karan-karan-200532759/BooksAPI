package com.example.demo;

// Model Book Class
public class Book {
    //private data members of the class
    private String key;
    private String ISBN;
    private String title;
   private String publishYear;
   private String publisher;
   private String authorName;
   private String description;

   private String imageUrl;

   // Getters-Setters of all the data members
    public String getKey() {
        return key;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }



    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  title + " published in " + publishYear;
    }

    // Parameterized Constructor
    public Book(String key, String ISBN, String title, String publishYear,
                 String publisher, String authorName, String description, String imageUrl) {
        this.key = key;
        this.ISBN = ISBN;
        this.title = title;
        this.publishYear = publishYear;

        this.publisher = publisher;
        this.authorName = authorName;
        this.description = description;
        this.imageUrl = imageUrl;
    }

}
