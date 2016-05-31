package View;

import Controllers.ActivityCreateController;
import Controllers.ActivityViewController;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by x on 15-05-2016.
 */
public class CreateActivityView
{

    private static Stage stage;
    private ActivityCreateController activityCreateController = new ActivityCreateController();

    public GridPane createActivityPane(Stage thisStage)
    {
        stage = thisStage;
        stage.initModality(Modality.APPLICATION_MODAL);
        GridPane gridCreatingActivity = new GridPane();
        //The instances we need to create an activity
        Label createLabel = new Label();
        Label nameLabel = new Label("Aktivitetsnavn");
        TextField nameField = new TextField();
        Label minAgeLabel = new Label("Minimumsalder");
        TextField minAgeField = new TextField();
        Label startTimeLabel = new Label("Start Tid");
        ComboBox startTimeBox = new ComboBox();
        Label endTimeLabel = new Label("Slut tid");
        ComboBox endTimeBox = new ComboBox();
        Label descriptionLabel = new Label("Beskrivelse af aktivitet");
        TextArea descriptionField = new TextArea();
        Button btnConfirm = new Button("Confirm");

        //Action to confirm the input to create an activity
        btnConfirm.setOnAction(event ->
        {
           activityCreateController.confirm(nameField,startTimeBox,endTimeBox,descriptionField,minAgeField,stage);
        });

        //Customizing the grid
        gridCreatingActivity.setAlignment(Pos.CENTER);
        gridCreatingActivity.setVgap(5);
        gridCreatingActivity.setStyle("-fx-background-color: linear-gradient(white 20%, #dda200 100%)");

        //Customizing createLabel
        createLabel.setText("Opret en aktivitet");
        createLabel.setFont(new Font("Cambria", 32));

        //Customizing the nameField
        nameField.setPromptText("Skriv aktivitetsnavnet");

        //Customizing the minAgeField
        minAgeField.setPromptText("Indtast minimums alderen");

        //Customizing startTime and EndTime
        startTimeBox.setPromptText("Start tid");
        endTimeBox.setPromptText("Slut tid");
        for(int i = 8; i < 23; i++)
        {
            for(int j = 0; j < 46; j = j + 15)
            {
                startTimeBox.getItems().addAll(setTime(i) + ":" + setTime(j));
                endTimeBox.getItems().addAll(setTime(i) + ":" + setTime(j));
            }
        }


        //Customizing the combobox'
        startTimeBox.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");
        startTimeBox.setPrefSize(100, 30);
        endTimeBox.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");
        endTimeBox.setPrefSize(100, 32);

        //Customizing the button
        btnConfirm.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");

        //Adding a HBox to control the Confirm Button's position
        HBox hboxForBtnConfirm = new HBox();
        hboxForBtnConfirm.getChildren().add(btnConfirm);
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
