package View;

import Controllers.ActivityCreateController;
import Models.Activities;
import Models.ActivityModel;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Time;

/**
 * Created by x on 15/05/2016.
 */
public class EditActivityView
{

    // TODO: status,description,date,user,address,time
    public static GridPane layout(Activities activities,Stage stage)
    {
        stage.initModality(Modality.APPLICATION_MODAL);

        GridPane gridCreatingActivity = new GridPane();
        //The instances we need to create an activity
        Label createLabel = new Label();
        Label nameLabel = new Label("Status");
        TextField nameField = new TextField(activities.getStatus());
        Label startTimeLabel = new Label("Time");
        ComboBox startTimeBox = new ComboBox();

        Label descriptionLabel = new Label("Description of the Activity");
        TextArea descriptionField = new TextArea(activities.getDescription());
        Button btnAcceptEdit = new Button("Accept Edit");

        //Action to confirm the input to create an activity
        btnAcceptEdit.setOnAction(event ->
        {
            String nameVal = "";
            int ageVal = 0;
            String status = "";
            String startTimeVal = "";
            Time start = null;
            String descriptionVal = "";
            try
            {
                //Converting all of the fields to Strings
            nameVal = nameField.getText();
         //   ageVal = Integer.parseInt(minAgeField.getText());
            startTimeVal = startTimeBox.getValue().toString()+":00";
            start = Time.valueOf(startTimeVal);
           // endTimeVal = endTimeBox.getValue().toString()+":00";
            descriptionVal = descriptionField.getText();
            }
            catch(Exception e){
                System.err.println("Could not parse values correctly: " + e);
            }

            //Validating the fields to insure that no mistake has been made
            if(new ActivityCreateController().validation(status, nameVal, descriptionVal))
            {
                // Status,description,date,user,address,time
                Activities activities1 = new Activities(activities.getStatus(), activities.getDescription(), activities.getDate(), activities.getUser(), activities.getAddress(), activities.getTime());
                ActivityModel.editActivity(activities1);
                stage.close();
            }
        });

        //Customizing the grid
        gridCreatingActivity.setAlignment(Pos.CENTER);
        gridCreatingActivity.setVgap(5);
        gridCreatingActivity.setStyle("-fx-background-color: linear-gradient(white 80%, #dda200 100%)");

        //Customizing createLabel
        createLabel.setText("Edit an Activity");
        createLabel.setFont(new Font("Cambria", 27));

        //Customizing the nameField
        nameField.setPromptText("Activityname...");


        //Customizing startTime and EndTime
        startTimeBox.setPromptText("Start Time");
        for(int i = 8; i < 23; i++)
        {
            for(int j = 0; j < 46; j = j + 15)
            {
                startTimeBox.getItems().addAll(setTime(i) + ":" + setTime(j));
            }
        }

        //Setting the start and end time BLAH
        String startTime = activities.getTime().toString().substring(0,5);
        startTimeBox.setValue(startTime);

        //Customizing the combobox'
        startTimeBox.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");
        startTimeBox.setPrefSize(130, 30);

        //Customizing the button
        btnAcceptEdit.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");

        //Adding a HBox to control the Confirm Button's position
        HBox hboxForBtnConfirm = new HBox();
        hboxForBtnConfirm.getChildren().add(btnAcceptEdit);
        hboxForBtnConfirm.setAlignment(Pos.CENTER_RIGHT);

        //Adding the attributes to the Grid
        gridCreatingActivity.add(createLabel, 0, 0);
        gridCreatingActivity.add(nameLabel, 0, 1);
        gridCreatingActivity.add(nameField, 0, 2);
        gridCreatingActivity.add(startTimeLabel, 0, 5);
        gridCreatingActivity.add(startTimeBox, 0, 6);
        gridCreatingActivity.add(descriptionLabel, 0, 7);
        gridCreatingActivity.add(descriptionField, 0, 8, 5, 1);
        gridCreatingActivity.add(hboxForBtnConfirm, 1, 9, 5, 1);

        return gridCreatingActivity;
    }

    protected static String setTime(int i)
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
