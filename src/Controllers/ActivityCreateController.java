package Controllers;

import Models.ActivityModel;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by x on 15-5-2016.
 */
public class ActivityCreateController
{
    public static void submitButton(String name, int minAge, String startTime,String endTime, String description)
    {
        ActivityModel.addActivity(name, minAge, startTime, endTime, description);
    }

    public boolean validation(String name, String minAge,String description)
    {
     boolean check = false;

        for(char c : minAge.toCharArray())
        {
            if(Character.isDigit(c) && name.length() > 0 && description.length() > 0)
            {
                check = true;
            }
        }

        if(!check)
        {
            setActivityAlert("Fejl","Alder er ikke et tal");
        }

        return check;
    }
    public void confirm(TextField nameField, ComboBox startTimeBox, ComboBox endTimeBox, TextArea descriptionField, TextField minAgeField, Stage stage)
    {
        try{
            //Converting all of the fields to Strings
            String name = nameField.getText();
            String startTime = startTimeBox.getValue().toString();
            String endTime = endTimeBox.getValue().toString();
            String description = descriptionField.getText();

            //Validating the fields to insure that no mistake has been made
            if(validation(name, minAgeField.getText(),description))
            {
                int minAge = Integer.parseInt(minAgeField.getText());

                ActivityCreateController.submitButton(name, minAge, startTime, endTime, description);
                nameField.clear();
                minAgeField.clear();
                descriptionField.clear();
                startTimeBox.setValue("Start tid");
                endTimeBox.setValue("Slut tid");
                stage.close();
            }
        }
        catch(Exception e){
            System.err.println("Could not create user: " + e);
            ActivityCreateController.setActivityAlert("Error","Could not parse correctly!");
        }

    }

    public static void setActivityAlert(String titleText,String headerText)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleText);
        alert.setContentText("Please fill out all the fields...");
        alert.setHeaderText(headerText);
        alert.show();
    }
}
