package View;

import Controllers.ActivityCreateController;
import Controllers.ActivityViewController;
import Controllers.UserEditController;
import Models.Booking;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Created by x on 15-05-2016.
 */
public class CreateActivityView
{

    private static Stage stage;
    private ActivityCreateController activityCreateController = new ActivityCreateController();


    //TODO: Status,description,date,user,address,time
    public GridPane createActivityPane(Stage thisStage)
    {
        stage = thisStage;
        stage.initModality(Modality.APPLICATION_MODAL);
        GridPane gridCreatingActivity = new GridPane();
        //The instances we need to create an activity
        Label createLabel = new Label();

        Label status = new Label("Status");
        TextField statusField = new TextField();

        Label description = new Label("Description");
        TextField descriptionField = new TextField();

        Label date = new Label("Date");
        DatePicker datePicker = new DatePicker();
        Date dateField = null;

        try{
            System.out.println(Booking.getDate().toString());
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(datePicker.getValue().toString());
            dateField = new Date(utilDate.getTime());

            Booking.setDate(dateField);



        }catch(Exception e2){
            System.err.println("Couldn't parse date: " + e2);
        }
        final Date finalDateField = dateField;

        Label user = new Label("User");
        TextField userField = new TextField();

        Label address = new Label("Address");
        TextField addressField = new TextField();

        Label time = new Label("Time");
        TextField timeFiled = new TextField();

        Button btnConfirm = new Button("Confirm");

        //Action to confirm the input to create an activity

        btnConfirm.setOnAction(event ->
        {
           activityCreateController.confirm(statusField,descriptionField,finalDateField,userField,addressField,timeFiled,stage);
        });

        //Customizing the grid
        gridCreatingActivity.setAlignment(Pos.CENTER);
        gridCreatingActivity.setVgap(5);
        gridCreatingActivity.setStyle("-fx-background-color: linear-gradient(white 20%, #dda200 100%)");

        //Customizing createLabel
        createLabel.setText("Opret en aktivitet");
        createLabel.setFont(new Font("Cambria", 32));

        //Customizing the button
        btnConfirm.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");

        //Adding a HBox to control the Confirm Button's position
        HBox hboxForBtnConfirm = new HBox();
        hboxForBtnConfirm.getChildren().add(btnConfirm);
        hboxForBtnConfirm.setAlignment(Pos.CENTER_RIGHT);


        //TODO: Status,description,date,user,address,time
        //Adding the attributes to the Grid
        gridCreatingActivity.add(createLabel, 0, 0);
        gridCreatingActivity.add(status, 0, 1);
        gridCreatingActivity.add(statusField, 0, 2);
        gridCreatingActivity.add(description, 0, 3);
        gridCreatingActivity.add(descriptionField, 0, 4);
        gridCreatingActivity.add(date, 0, 5);
        gridCreatingActivity.add(datePicker, 0, 6);
        gridCreatingActivity.add(user, 1, 5);
        gridCreatingActivity.add(userField, 1, 6);
        gridCreatingActivity.add(address, 0, 7);
        gridCreatingActivity.add(addressField, 0, 8, 5, 1);
        gridCreatingActivity.add(time, 0, 9, 5, 1);
        gridCreatingActivity.add(timeFiled, 0, 10, 5, 1);
        gridCreatingActivity.add(hboxForBtnConfirm, 1, 11, 5, 1);

        return gridCreatingActivity;
    }

    public static String setTime(int i)
    {
        String valueReturn;
        if(i < 10)
        {
            valueReturn = "0" + i;
        }
        else
        {
            valueReturn = i + "";
        }

        return valueReturn;
    }

}
