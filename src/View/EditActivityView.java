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
    public static GridPane layout(Activities activities,Stage stage)
    {
        stage.initModality(Modality.APPLICATION_MODAL);

        GridPane gridCreatingActivity = new GridPane();
        //The instances we need to create an activity
        Label createLabel = new Label();
        Label nameLabel = new Label("Activity Name");
        TextField nameField = new TextField(activities.getName());
        Label minAgeLabel = new Label("Requirement Age");
        TextField minAgeField = new TextField(String.valueOf(activities.getMinAge()));
        Label startTimeLabel = new Label("Start Time");
        ComboBox startTimeBox = new ComboBox();
        Label endTimeLabel = new Label("End Time");
        ComboBox endTimeBox = new ComboBox();
        Label descriptionLabel = new Label("Description of the Activity");
        TextArea descriptionField = new TextArea(activities.getDescription());
        Button btnAcceptEdit = new Button("Accept Edit");

        //Action to confirm the input to create an activity
        btnAcceptEdit.setOnAction(event ->
        {
            String nameVal = "";
            int ageVal = 0;
            String startTimeVal = "";
            Time start = null;
            String endTimeVal = "";
            Time stop = null;
            String descriptionVal = "";
            try
            {
                //Converting all of the fields to Strings
            nameVal = nameField.getText();
            ageVal = Integer.parseInt(minAgeField.getText());
            startTimeVal = startTimeBox.getValue().toString()+":00";
            start = Time.valueOf(startTimeVal);
            endTimeVal = endTimeBox.getValue().toString()+":00";
            stop = Time.valueOf(endTimeVal);
            descriptionVal = descriptionField.getText();
            }
            catch(Exception e){
                System.err.println("Could not parse values correctly: " + e);
            }

            //Validating the fields to insure that no mistake has been made
            if(new ActivityCreateController().validation(nameVal, minAgeField.getText(), descriptionVal))
            {
                Activities activities1 = new Activities(activities.getId(), nameVal, ageVal, start, stop, descriptionVal);
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

        //Customizing the minAgeField
        minAgeField.setPromptText("Enter the minimum age requirement");

        //Customizing startTime and EndTime
        startTimeBox.setPromptText("Start Time");
        endTimeBox.setPromptText("End Time");
        for(int i = 8; i < 23; i++)
        {
            for(int j = 0; j < 46; j = j + 15)
            {
                startTimeBox.getItems().addAll(setTime(i) + ":" + setTime(j));
                endTimeBox.getItems().addAll(setTime(i) + ":" + setTime(j));
            }
        }

        //Setting the start and end time BLAH
        String startTime = activities.getStartTime().toString().substring(0,5);
        String endTime = activities.getEndTime().toString().substring(0,5);
        startTimeBox.setValue(startTime);
        endTimeBox.setValue(endTime);

        //Customizing the combobox'
        startTimeBox.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");
        startTimeBox.setPrefSize(130, 30);
        endTimeBox.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");
        endTimeBox.setPrefSize(130, 32);

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
        gridCreatingActivity.add(minAgeLabel, 0, 3);
        gridCreatingActivity.add(minAgeField, 0, 4);
        gridCreatingActivity.add(startTimeLabel, 0, 5);
        gridCreatingActivity.add(startTimeBox, 0, 6);
        gridCreatingActivity.add(endTimeLabel, 1, 5);
        gridCreatingActivity.add(endTimeBox, 1, 6);
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
