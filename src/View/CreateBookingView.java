package View;

import Controllers.BookingCreateController;
import Models.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by x on 19-05-2016.
 */
public class CreateBookingView
{

    public static TextField searchField = new TextField();
    public static ComboBox<User> userCBox;
    public static TextField description = new TextField();
    public static ComboBox<Activities> activitiesCBox;
    public static ComboBox<Time> time;
    public static ComboBox<Time> endTimeCBox;
    public static DatePicker datePicker;
    public static CreateBookingView booking = null;
    public void create()
    {
        Stage stage = new Stage();
        stage.setScene(new Scene(btnCreateBooking(stage), 600, 200));
        stage.show();
    }

    public static CreateBookingView getInstance()
    {
        if(booking == null)
        {
            booking = new CreateBookingView();
        }
        return booking;
    }

    public static GridPane btnCreateBooking(Stage stage)
    {
        stage.initModality(Modality.APPLICATION_MODAL);
        GridPane gridPane = new GridPane();
        //Customization Grid
        gridPane.setStyle("-fx-background-color: linear-gradient(white 25%, darkcyan 100%)");
        gridPane.setPadding(new Insets(20,0,0,60));
        gridPane.setHgap(40);
        gridPane.setVgap(3);


        //Add image to
        // TODO: 02/03/2016 Man kan prøve at lave Canvas her, men kan ikke lige overskue
        ImageView infoImageForActivities = new ImageView();
        Image image = new Image(LoginView.class.getResourceAsStream("../img/Infoicon.jpg"));
        infoImageForActivities.setImage(image);
        infoImageForActivities.setFitHeight(20);
        infoImageForActivities.setFitWidth(20);

        //Adding the setOnAction to the Image
        infoImageForActivities.setOnMouseClicked(event ->
        {
            if (activitiesCBox.getSelectionModel().getSelectedItem() != null)
            {
                Activities activities = activitiesCBox.getSelectionModel().getSelectedItem();
                BookingInfoView.getInstance().infoStage(activities);
            }
        });

        //removing the search field
//        searchField.setPromptText("Search Customer");
//        if (SessionModel.getInstance().getLoggedInUser().getRole() != UserRoleEnum.USER)
//        {
//            gridPane.add(searchField, 0,1);
//        }

        userCBox = new ComboBox<>();
        userCBox.setValue(SessionModel.getInstance().getLoggedInUser());
        userCBox.setMaxWidth(195);
        //If logged in as customer combobox is not available
        userCBox.setDisable(SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.USER);

        userCBox.setEditable(false);
        gridPane.add(userCBox, 0, 1);
        //Using the list from DB and uses the toString method for displaying the object as fname + lname
        userCBox.getItems().addAll(UserModel.getInstance().getUserList());

        /*Adding status combo box*/
        ComboBox<String> statusCbox = new ComboBox<>();
        statusCbox.setPromptText("Status");
        statusCbox.setMaxWidth(195);
        statusCbox.getItems().addAll(CreateBookingView.status());
        gridPane.add(statusCbox, 0, 2);

// removing activity boxes
//        //Adding the acitvityBox
//        activitiesCBox = new ComboBox<>();
//        activitiesCBox.setPromptText("Select Activity");
//        activitiesCBox.setMaxWidth(140);
//        gridPane.add(activitiesCBox, 1, 2);
//        //Using the list from DB and uses the toString method for displaying the object as fname + lname
//        activitiesCBox.getItems().addAll(ActivityModel.getInstance().getActivities());

        //Adding the InfoButton beside the activityBox
        gridPane.add(infoImageForActivities, 2, 2);

        // removing activity time boxes as well
//        time = new ComboBox<>();
//        time.setPromptText("Activity Starts ");
//        time.setMinWidth(140);
//        gridPane.add(time, 1, 5);
//
//        endTimeCBox = new ComboBox<>();
//        endTimeCBox.setPromptText("Activity Ends   ");
//        endTimeCBox.setMinWidth(140);
//        gridPane.add(endTimeCBox, 1, 6);

        datePicker = new DatePicker();
        datePicker.setPromptText("Select Date ");
        gridPane.add(datePicker, 0, 3);

        /*Adding time text field*/
        TextField timeField = new TextField() ;
        timeField.setPromptText("Time");
        gridPane.add(timeField, 0, 4);

        /*Adding address field*/
        Label laddress = new Label("Address : ") ;
        gridPane.add(laddress,1,1);
        TextField address = new TextField() ;
        gridPane.add(address, 1, 2);

        /*Adding description field*/
        Label ldescription = new Label("Description : ") ;
        gridPane.add(ldescription,1,3);
        TextField description = new TextField() ;
        gridPane.add(description, 1, 4);

        Button bookBtn = new Button("Book Activity");
        gridPane.add(bookBtn, 1, 12);

//        //Hack to avoid toString...
//        activitiesCBox.valueProperty().addListener((observable, oldValue, newValue) ->
//        {
//            time.getItems().clear();
//            Time starTime = newValue.getTime();
//            time.getItems().addAll(starTime);
//        });

        searchField.setOnKeyReleased(e-> BookingCreateController.search(userCBox,searchField));

        bookBtn.setOnAction(e ->
        {
            User selectedUser = userCBox.getSelectionModel().getSelectedItem();
            Date date = null;
            try
            {

                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(datePicker.getValue().toString());


                date = new Date(utilDate.getTime());

                date = new Date(utilDate.getTime());

                DateFormat formatter = new SimpleDateFormat("HH:mm");
                Time timeparse = new Time(formatter.parse(timeField.getText()).getTime());

                Booking book = new Booking(selectedUser.getID(), date, timeparse);

                book.setStatus(statusCbox.getSelectionModel().getSelectedItem());
                book.setDescription(description.getText());
                book.setAddress(address.getText());
                book.setUserID(selectedUser.getID());

                BookingCreateController.tryInsert(book);
                stage.close();

            } catch (Exception e1)
            {
                System.err.println("Could not parse date: " + e1);
                BookingCreateController.setBookingAlert("Error","Could not parse date!");
            }
            //convertMiliToHours();
            System.out.println(selectedUser.getID());

        });

        return gridPane;
    }

    protected static String convertMiliToHours()
    {
        String duration;

        System.out.println(time.getValue());
        long startTime = time.getValue().getTime();
        long endTime = endTimeCBox.getValue().getTime();

        long timeDuration = (endTime - startTime);

        int minutes = (int) ((timeDuration / (1000*60)) % 60);
        int hours = (int) ((timeDuration / (1000*60*60)) % 24);

        duration = Integer.toString(hours) + ":" + Integer.toString(minutes) + "Hours";

        System.out.println(duration);

        return duration;
    }

    protected static List<String> status()
    {
        List<String> status = new ArrayList<>();

        status.add("Afhentet");
        status.add("Leveret");

        return status;
    }

}
