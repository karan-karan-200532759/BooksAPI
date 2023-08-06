package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Controller for the SearchView
public class SearchController {
    // Components used in View
    @FXML
    private Label lblWelcome;
    @FXML
    private Label lblCounter;
    @FXML
    private Label lblTitle;
    @FXML
    private TextField txtTitle;

    @FXML
    private Button buttonFetchInfo;

    @FXML
    private ListView listBooks;


    // Event on the click of item in the listview
    @FXML
    protected void showDetails(MouseEvent event) {
        try {
            // Get selected item from list
            Book book = (Book) listBooks.getSelectionModel().getSelectedItem();

            // Check if its null or not
            if (book != null) {

                // Load the next view
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(
                        "detailsView.fxml"));

                // Controller object
                DetailsViewController detailsViewController = new DetailsViewController();
                detailsViewController.setBook(book);
                fxmlLoader.setController(detailsViewController);

                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root, 650, 600);
                scene.getStylesheets().add("style.css");
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();

                // Close the current view
                stage.close();

                // navigate to details view
                stage.setScene(scene);
                stage.setTitle("Detailed Book View!");
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Event on the click of button
    @FXML
    protected void fetchBooks(ActionEvent event) {

        // Fetch title entered by the user
        String queryTitle = txtTitle.getText();

        // Check if its empty
        if (queryTitle.equals("")) {

            // Show alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please enter the book name!");
            alert.showAndWait();
        } else {
            try {

                lblCounter.setText("Loading..Please wait!");

                // prepare the uri
                String uri = "https://openlibrary.org/search.json?title=" + queryTitle;

                // replace the spaces
                String newUrlString = uri.replaceAll(" ", "%20");
                System.out.println(newUrlString);

                // Establish the connection
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(newUrlString))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Fetch the response
                String jsonString = response.body();

                // Create json object
                JSONObject json = new JSONObject(jsonString);

                // Fetch the key "docs"
                JSONArray booksArray = json.getJSONArray("docs");
                System.out.println(booksArray.length());

                // Show the total records received
                lblCounter.setText("Books Found: " + booksArray.length());

                // If no record found
                if (booksArray.length() == 0) {

                    // Show alert box

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Not found!");
                    alert.setHeaderText("No book found with the given title!");
                    alert.showAndWait();
                } else {

                    // Loop over the records
                    for (int i = 0; i < booksArray.length(); i++) //i<booksArray.length(); i++)
                    {
                        // Fetch the array object
                        JSONObject bookObject = booksArray.getJSONObject(i);
                        String key = bookObject.getString("key");
                        if (key.charAt(key.length() - 1) != 'W') {
                            continue;
                        }
                        String title = bookObject.getString("title");
                        String publishYear = "Not Available";
                        if (bookObject.has("first_publish_year")) {
                            publishYear = "" + bookObject.getInt("first_publish_year");
                        }
                        String isbn = "Not Available";
                        if (bookObject.has("isbn")) {
                            isbn = bookObject.getJSONArray("isbn").getString(0);
                        }
                        String publisher = "Not Available";
                        if (bookObject.has("publisher")) {
                            publisher = bookObject.getJSONArray("publisher").getString(0);

                        }
                        String author = "Not Available";
                        if (bookObject.has("author_name")) {
                            author = bookObject.getJSONArray("author_name").getString(0);
                        }
                        // Fetch the description
                        String description = getDescription(key);

                        // Prepare the image URL
                        String imageUrl = "https://covers.openlibrary.org/b/isbn/" + isbn
                                + "-L.jpg";

                        // Create an object of book class
                        Book book = new Book(key, isbn, title, publishYear, publisher,
                                author, description, imageUrl);

                        // Add the book object to listview
                        listBooks.getItems().add(book);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }

    // Fetch the description again from API using key
    String getDescription(String key) throws Exception {

        // Prepare the uril
        String uri = "https://openlibrary.org" + key + ".json";
        // System.out.println(uri);

        // Establish the connection
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        String jsonString = response.body();

        // Prepare json object
        JSONObject json = new JSONObject(jsonString);
        String description = "Not Available";

        // Fetch the description
        if (json.has("description")) {
            Object desc = json.get("description");
            if (desc instanceof String) {
                // description
                description = json.getString("description");
            } else {
                JSONObject descObject = json.getJSONObject("description");
                description = descObject.getString("value");
            }
        }
        //System.out.println("Desc: " + description);
        return description;
    }
}
