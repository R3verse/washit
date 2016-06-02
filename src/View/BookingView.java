package View;

import Controllers.BookingViewController;
import Models.Booking;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Created by x on 14/05/2016.
 */

public class BookingView {

    private TextField searchBookingName = new TextField();
    private TextField searchUserName = new TextField();
    private TextField searchDate = new TextField();
    private TextField searchBookId = new TextField();

    public TextField getSearchAddress() {
        return searchAddress;
    }

    public TextField getSearchUserName() {
        return searchUserName;
    }

    public TextField getSearchDate() {
        return searchDate;
    }

    private TextField searchAddress = new TextField();

    public static BookingView bookingView = null;

    TableView<Booking> bookingsTableView;
    private BookingViewController bookingViewController = new BookingViewController();

    public static BookingView getInstance()
    {
        if(bookingView == null)
        {
            bookingView = new BookingView();
        }
        return bookingView;
    }

    public BorderPane innerBorderPaneBookings()
    {
        BorderPane layout = new BorderPane();

        bookingsTableView = new TableView<>();

        //Image Magnifier
        ImageView imv = new ImageView();
        Image image = new Image(LoginView.class.getResourceAsStream("../img/MagnifierGlass.png"));
        imv.setImage(image);

        // TextField bookId, TextField address
        imv.setOnMouseClicked(e->
        {
            bookingViewController.search(bookingsTableView, searchBookingName, searchUserName, searchDate, searchBookId, searchAddress);
        });

        //Image Plussign
        ImageView imv2 = new ImageView();
        imv2.setFitHeight(30);
        imv2.setFitWidth(30);
        Image imagePlusSign = new Image(LoginView.class.getResourceAsStream("../img/Plus.png"));
        imv2.setImage(imagePlusSign);


        //Image Plussign onMouseClick to handle Create Activity
        imv2.setOnMouseClicked(event ->
        {
            bookingViewController.create(bookingsTableView);
        });

        //Two HBox' to the top menu
        HBox collector = new HBox();
        HBox createNewActivityHolder = new HBox();
        HBox searchActivitiesHolder = new HBox();

        //Collecting the two HBox'
        collector.getChildren().addAll(createNewActivityHolder, searchActivitiesHolder);

        //Positioning of the HBox'
        createNewActivityHolder.setAlignment(Pos.CENTER_LEFT);
        createNewActivityHolder.setPrefWidth(400);
        searchActivitiesHolder.setAlignment(Pos.CENTER_RIGHT);
        searchActivitiesHolder.setSpacing(3);

        //Customizing the textfields
        searchBookingName.setPromptText("Search Booking...");
        searchUserName.setPromptText("Search User...");
        searchDate.setPromptText("Search Date...");

        //Settings the HBox'
        createNewActivityHolder.getChildren().addAll(imv2);
        searchActivitiesHolder.getChildren().addAll(searchBookingName, searchUserName, searchDate, imv);
        createNewActivityHolder.setStyle("-fx-background-color: white");
        layout.setTop(collector);

        layout.setCenter(bookingsTableView);

        //ContextMenu as popup when right clicking a column on the ActivityTableView
        ContextMenu cMenu = new ContextMenu();
        cMenu.setStyle("-fx-background-color: #67db6e, \n" +
                "        linear-gradient(#7ebcea, #67db6e)," +
                "        linear-gradient(#67db6e, #7ebcea);" +
                "        -fx-font-weight: bold;" +
                "        -fx-background-radius: 6;" +
                "        -fx-text-fill: white");
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");
        cMenu.getItems().addAll(editItem, deleteItem);

        //The right click handling
        bookingsTableView.setOnMouseClicked(event ->
        {
            if (event.getButton() == MouseButton.SECONDARY)
            {
                bookingsTableView.setContextMenu(cMenu);
            }

            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                bookingViewController.edit(bookingsTableView);
            }
        });

        editItem.setOnAction(event ->
        {
            bookingViewController.edit(bookingsTableView);
        });

        deleteItem.setOnAction(event ->
        {
            bookingViewController.delete(bookingsTableView);
        });

       BookingViewController controller = new BookingViewController();

       controller.btnClickedToShowBookings(bookingsTableView);

        return layout;
    }
}

