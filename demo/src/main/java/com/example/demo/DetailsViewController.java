package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

// Controller for second view (detailsview)
public class DetailsViewController {

    // Components used in the view
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblISBN;

    @FXML
    private Label lblAuthor;

    @FXML
    private Label lblPublisher;

    @FXML
    private Label lblYear;

    @FXML
    private Label lblDescription;

    @FXML
    private ImageView imageView;

    // book data member
    private Book book;

    // getter-setter
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    //initialization
    @FXML
    public void initialize() {
        //System.out.println("second");

        // set the data in the labels
        lblTitle.setText(book.getTitle());
        lblAuthor.setText(book.getAuthorName());
        lblDescription.setText(book.getDescription());
        lblISBN.setText(book.getISBN());
        lblPublisher.setText(book.getPublisher());
        lblYear.setText(book.getPublishYear());


        // show the image
        Image image = new Image(book.getImageUrl());
        imageView.setImage(image);
    }

    // Event for the back button
    @FXML
    protected  void moveBack(ActionEvent ae)
    {
        try {

            // Load the view
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(
                    "searchView.fxml"));

            Parent root = fxmlLoader.load();
            // set the scene
            Scene scene = new Scene(root, 650, 540);

            // set the stylesheet
            scene.getStylesheets().add("style.css");
            Node node = (Node) ae.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            stage.setScene(scene);

            stage.setTitle("Book LookUp!");
            root.requestFocus();;
            stage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
