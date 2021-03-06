package View;

import Controllers.ActivityViewController;
import Controllers.BookingViewController;
import Models.Activities;
import Models.ActivityModel;;
import Models.Booking;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by x on 16/05/2016.
 */
public class ActivityView
{

    private TextField searchActivitiesName = new TextField();
    private TextField searchActivitiesAge = new TextField();
    private TextField searchDate = new TextField();
    private TextField searchBookId = new TextField();
    private TextField searchAddress = new TextField();


    private TableView<Booking> activitiesTableView;
    private BookingViewController activityViewController = new BookingViewController();


    //TODO: - Skal kunne ændre + oprette --> Status,description,date,user,address,time

    public BorderPane innerRootBorderPaneActivities()
    {
        BorderPane layout = new BorderPane();
        activitiesTableView = new TableView<>();
        activityViewController.btnClickedToShowBookings(activitiesTableView);


        //Image Magnifier
        ImageView imv = new ImageView();
        Image image = new Image(LoginView.class.getResourceAsStream("../img/MagnifierGlass.png"));
        imv.setImage(image);
        imv.setOnMouseClicked(e->
        {
            activityViewController.search(activitiesTableView, searchActivitiesName, searchActivitiesAge, searchDate, searchBookId, searchAddress);
        });

        //Image Plussign
        ImageView imv2 = new ImageView();
        imv2.setFitHeight(30);
        imv2.setFitWidth(30);
        Image imagePlusSign = new Image(LoginView.class.getResourceAsStream("../img/Plus.png"));
        imv2.setImage(imagePlusSign);

        //Create activity from image click
        imv2.setOnMouseClicked(event ->activityViewController.create(activitiesTableView));

        //Two HBox' to the top menu
        HBox collector = new HBox();
        HBox createNewActivityHolder = new HBox();
        HBox searchActivitiesHolder = new HBox();

        //Collecting the two HBox'
        collector.getChildren().addAll(createNewActivityHolder, searchActivitiesHolder);

        createNewActivityHolder.setAlignment(Pos.CENTER_LEFT);
        createNewActivityHolder.setPrefWidth(450);
        searchActivitiesHolder.setAlignment(Pos.CENTER_RIGHT);
        searchActivitiesHolder.setSpacing(3);


        searchActivitiesName.setPromptText("Search Booking...");
        searchActivitiesAge.setPromptText("Search age...");

        //Settings the HBox'
        createNewActivityHolder.getChildren().addAll(imv2);
        searchActivitiesHolder.getChildren().addAll(searchActivitiesName, searchActivitiesAge, imv);
        createNewActivityHolder.setStyle("-fx-background-color: white");
        layout.setTop(collector);

        layout.setCenter(activitiesTableView);

        //ContextMenu as popup when right clicking a column on the ActivityTableView
        ContextMenu cMenu = new ContextMenu();
        cMenu.setStyle("-fx-background-color: linear-gradient(white 25%, darkcyan 100%)");
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");
        cMenu.getItems().addAll(editItem, deleteItem);

        activitiesTableView.setOnMouseClicked(event ->
        {
            if (event.getButton() == MouseButton.SECONDARY)
            {
                activitiesTableView.setContextMenu(cMenu);
            }

            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                activityViewController.editStart(activitiesTableView);
            }
        });


        editItem.setOnAction(event ->
        {
            activityViewController.editStart(activitiesTableView);

        });

        deleteItem.setOnAction(event ->
        {
            activityViewController.delete(activitiesTableView);
        });

        return layout;
    }
}
