package View;

import Controllers.BookingCreateController;
import Controllers.BookingEditController;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by x on 19-05-2016.
 */
public class EditBookingView
{
    private static Stage stage;
    static ComboBox<Time> startTimeCBox = new ComboBox<>();
    static ComboBox<Time> endTimeCBox = new ComboBox<>();
    public static void edit(Booking booking)
    {

    }

    public static GridPane pushAndEdit(Booking booking, Stage stage)
    {

        stage.initModality(Modality.APPLICATION_MODAL);

        //Setting up the layout
        ComboBox<User> userCBox = new ComboBox();
        ComboBox<String> numUsersCbox = new ComboBox();
        numUsersCbox.setValue(String.valueOf(booking.getParticipants()));
        ComboBox<Activities> activitiesCBox = new ComboBox<>();
        //activitiesCBox.setValue();

        //startTimeCBox.setValue(booking.getStartTime().getTime());

        //endTimeCBox.setValue(booking.getEndTime().getTime());
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(booking.getDate().toLocalDate());


        // TODO: 02-03-2016 jesus fix mig 
        /*************************************TEST**************************/
        List<Activities> dumbShit = ActivityModel.getInstance().getActivities();
        Activities usedActivity = null;
        for(Activities a: dumbShit)
        {
            System.out.println(a.getId() +"  " + booking.getActivityID());
            if(a.getId() == booking.getActivityID())
            {
                usedActivity = a;
                activitiesCBox.setValue(a);
                break;
            }
        }
        ObservableList fukoff = FXCollections.observableArrayList(new ArrayList<Time>());
        fukoff.add(usedActivity.getStartTime());
        ObservableList fukinn = FXCollections.observableArrayList(new ArrayList<Time>());
        fukinn.add(usedActivity.getEndTime());
        //Never touch this please
        startTimeCBox.setItems(fukoff);
        startTimeCBox.setValue(usedActivity.getStartTime());
        endTimeCBox.setItems(fukinn);
        endTimeCBox.setValue(usedActivity.getEndTime());

        ObservableList<Activities> arrSut = FXCollections.observableArrayList();
        arrSut.addAll(ActivityModel.getInstance().getActivities());
        activitiesCBox.setItems(arrSut);

        List<User> userList = UserModel.getInstance().getUserList();
        for(User u: userList)
        {
            if(u.getID() == booking.getUserID())
            {
                userCBox.setValue(u);
                break;
            }
        }


        //userCBox.setValue(booking.getUserName());


        /*************************************TEST**************************/
        //Buttons
        Button btnEditBooking = new Button("Edit Activity");

        //Setting up the GridPane && Customization
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,5,5,60));
        gridPane.setHgap(40);
        gridPane.setVgap(3);
        gridPane.setStyle("-fx-background-color: linear-gradient(white 80%, #dda200 100%)");

        //Add image
        ImageView imageView = new ImageView();
        Image image = new Image(LoginView.class.getResourceAsStream("../img/adventure.png"));
        imageView.setImage(image);
        HBox pictureRegion = new HBox();
        pictureRegion.setPadding(new Insets(0, 0, 20, 0));
        pictureRegion.getChildren().add(imageView);


        userCBox.setPromptText("Select Customer");
        userCBox.setMaxWidth(195);
        //If logged in as customer combobox is not available
        userCBox.setDisable(SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.USER);

        userCBox.setEditable(false);
        gridPane.add(userCBox, 0, 1);
        //Using the list from DB and uses the toString method for displaying the object as fname + lname

        userCBox.getItems().addAll(UserModel.getInstance().getUserList());

        numUsersCbox.setPromptText("Participants");
        numUsersCbox.setMaxWidth(195);
        numUsersCbox.getItems().addAll(CreateBookingView.participants());
        gridPane.add(numUsersCbox, 0, 2);

        activitiesCBox.setPromptText("Select Activity");
        activitiesCBox.setMaxWidth(140);
        gridPane.add(activitiesCBox, 1, 2);
        //Using the list from DB and uses the toString method for displaying the object as fname + lname
        //activitiesCBox.getItems().addAll(String.valueOf(ActivityModel.getInstance().getActivities()));

        startTimeCBox.setPromptText("Activity Starts ");
        startTimeCBox.setMinWidth(140);
        gridPane.add(startTimeCBox, 1, 8);

        endTimeCBox.setPromptText("Activity Ends   ");
        endTimeCBox.setMinWidth(140);
        gridPane.add(endTimeCBox, 1, 9);

        datePicker.setPromptText("Select Date ");
        gridPane.add(datePicker, 0,8);

        gridPane.add(btnEditBooking, 1, 11);


        //Hack to avoid toString...
        activitiesCBox.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            startTimeCBox.getItems().clear();
            Time starTime = newValue.getStartTime();
            startTimeCBox.getItems().addAll(starTime);
            startTimeCBox.getSelectionModel().select(0);
        });

        activitiesCBox.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            endTimeCBox.getItems().clear();
            Time endTime = newValue.getEndTime();
            endTimeCBox.getItems().addAll(endTime);
            endTimeCBox.getSelectionModel().select(0);
        });
        //searchField.setOnKeyReleased(e-> BookingCreateController.search(userCBox,searchField));

        btnEditBooking.setOnAction(e ->
        {

            Date date = null;

            try
            {
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(datePicker.getValue().toString());

                date = new java.sql.Date(utilDate.getTime());

                booking.setUserID(userCBox.getValue().getID());
                booking.setActivityID(activitiesCBox.getValue().getId());
                booking.setDate(date);
                booking.setStartTime(startTimeCBox.getValue());
                booking.setEndTime(endTimeCBox.getValue());
                int participants = Integer.parseInt(numUsersCbox.getValue().split(" ")[0]);
                booking.setParticipants(participants);
                BookingEditController.tryUpdate(booking);
                stage.close();
                System.out.println("Done");

            }
            catch (Exception e1)
            {
                System.err.println("Failed to parse date: " + e1);
                BookingCreateController.setBookingAlert("Error", "You must fill out the date field");
            }
            convertMiliToHours();
        });

        return gridPane;
    }
    protected static String convertMiliToHours()
    {
        String duration;

        long startTime = startTimeCBox.getValue().getTime();
        long endTime = endTimeCBox.getValue().getTime();

        long timeDuration = (endTime - startTime);

        int minutes = (int) ((timeDuration / (1000*60)) % 60);
        int hours = (int) ((timeDuration / (1000*60*60)) % 24);

        duration = Integer.toString(hours) + ":" + Integer.toString(minutes) + "Hours";

        System.out.println(duration);

        return duration;
    }



}
