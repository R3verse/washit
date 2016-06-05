package View;

import Controllers.BookingCreateController;
import Controllers.BookingEditController;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        //numUsersCbox.setValue(String.valueOf(booking.getParticipants()));
        ComboBox<Booking> activitiesCBox = new ComboBox<>();
        //activitiesCBox.setValue();

        //time.setValue(booking.getStartTime().getTime());

        //endTimeCBox.setValue(booking.getEndTime().getTime());
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(booking.getDate().toLocalDate());


        // TODO: 02-03-2016 jesus fix mig 
        /*************************************TEST**************************/
        //List<Activities> dumbShit = ActivityModel.getInstance().getActivities();
        List<Booking> dumbShit = BookingModel.getInstance().getBookings();
        Booking usedActivity = null;
        for(Booking a: dumbShit)
        {

            if(a.getID() == booking.getID())
            {
                usedActivity = a;
                activitiesCBox.setValue(a);
                break;
            }

        }
        ObservableList fukoff = FXCollections.observableArrayList(new ArrayList<Time>()); // wow this is great ;)

        ObservableList fukinn = FXCollections.observableArrayList(new ArrayList<Time>());

        //Never touch this please
        startTimeCBox.setItems(fukoff);


        ObservableList<Booking> arrSut = FXCollections.observableArrayList();
        arrSut.addAll(BookingModel.getInstance().getBookings());
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
        Button btnEditBooking = new Button("Edit Booking");

        //Setting up the GridPane && Customization
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,5,5,60));
        gridPane.setHgap(40);
        gridPane.setVgap(3);
        gridPane.setStyle("-fx-background-color: linear-gradient(white 25%, darkcyan 100%)");

        //Add image
        ImageView imageView = new ImageView();
        Image image = new Image(LoginView.class.getResourceAsStream("../img/adventure.png"));
        imageView.setImage(image);
        HBox pictureRegion = new HBox();
        pictureRegion.setPadding(new Insets(0, 0, 20, 0));
        pictureRegion.getChildren().add(imageView);


        userCBox.setPromptText("Select User");
        userCBox.setMaxWidth(195);
        //If logged in as customer combobox is not available
        userCBox.setDisable(SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.USER);

        userCBox.setEditable(false);
        gridPane.add(userCBox, 0, 1);
        //Using the list from DB and uses the toString method for displaying the object as fname + lname

        userCBox.getItems().addAll(UserModel.getInstance().getUserList());

        numUsersCbox.setPromptText("Status");
        numUsersCbox.setMaxWidth(195);
        numUsersCbox.getItems().addAll(CreateBookingView.status());
        String status = booking.getStatus();
        int i = 0 ;
        for(String s :  CreateBookingView.status()){
            if(s == status)
                break ;
            else
                i++ ;
        }
        numUsersCbox.getSelectionModel().select(status);
        gridPane.add(numUsersCbox, 0, 2);

        activitiesCBox.setPromptText("Select Activity");
        activitiesCBox.setMaxWidth(140);
        //gridPane.add(activitiesCBox, 1, 2); // removing activities box
        //Using the list from DB and uses the toString method for displaying the object as fname + lname
        //activitiesCBox.getItems().addAll(String.valueOf(ActivityModel.getInstance().getActivities()));

        startTimeCBox.setPromptText("Activity Starts ");
        startTimeCBox.setMinWidth(140);
//        gridPane.add(startTimeCBox, 1, 8); // removing activity time

        endTimeCBox.setPromptText("Activity Ends   ");
        endTimeCBox.setMinWidth(140);
//        gridPane.add(endTimeCBox, 1, 9); // removing activity end time

        datePicker.setPromptText("Select Date ");
        gridPane.add(datePicker, 0,3);

        /*Adding time text field*/
        TextField time = new TextField() ;
        time.setText(booking.getTime().toString());
        gridPane.add(time, 0, 4);

        /*Adding address field*/
        Label laddress = new Label("Address : ") ;
        gridPane.add(laddress,1,1);
        TextField address = new TextField() ;
        address.setText(booking.getAddress());
        gridPane.add(address, 1, 2);

        /*Adding description field*/
        Label ldescription = new Label("Description : ") ;
        gridPane.add(ldescription,1,3);
        TextField description = new TextField() ;
        description.setText(booking.getDescription());
        gridPane.add(description, 1, 4);

        gridPane.add(btnEditBooking, 1, 11);


        //Hack to avoid toString...
        activitiesCBox.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            startTimeCBox.getItems().clear();
            startTimeCBox.getSelectionModel().select(0);
        });

        activitiesCBox.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            endTimeCBox.getItems().clear();
            endTimeCBox.getSelectionModel().select(0);
        });
        //searchField.setOnKeyReleased(e-> BookingCreateController.search(userCBox,searchField));

        btnEditBooking.setOnAction(e ->
        {

            Date date = null;

            try
            {
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(datePicker.getValue().toString());


                date = new Date(utilDate.getTime());

                date = new Date(utilDate.getTime());

                DateFormat formatter = new SimpleDateFormat("HH:mm");
                Time timeparse = new Time(formatter.parse(time.getText()).getTime());

                booking.setUserID(userCBox.getValue().getID());
                booking.setAddress(address.getText());
                booking.setDescription(description.getText());
                booking.setStatus(numUsersCbox.getSelectionModel().getSelectedItem());

                booking.setTime(timeparse);
                booking.setDate(date);
              //  booking.setActivityID(activitiesCBox.getValue().getId());
                //  booking.setStartTime(time.getValue());
                //  booking.setEndTime(endTimeCBox.getValue());
                //int participants = Integer.parseInt(numUsersCbox.getValue().split(" ")[0]);
                //  booking.setParticipants(status);
                BookingEditController.tryUpdate(booking);
                stage.close();
                System.out.println("Done");

            }
            catch (Exception e1)
            {
                System.err.println("Failed to parse date: " + e1);
                BookingCreateController.setBookingAlert("Error", "You must fill out the date field");
            }
            //convertMiliToHours();
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
