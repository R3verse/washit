package Controllers;

import Models.ActivityModel;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * Created by x on 15-5-2016.
 */
public class ActivityCreateController
{
    public static void submitButton(String status, String description, String date, String user, String address, String time)
    {
        ActivityModel.addActivity(status, description, date, user, address, time);
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

    // status,description,date,user,address,time
    public void confirm(TextField statusField, TextField descriptionField, Date dateField, TextField userField, TextField addressField, TextField timeField, Stage stage)
    {
        try{
            //Converting all of the fields to Strings
            String status = statusField.getText();
            String description = descriptionField.getText();
            String date = dateField.toString();
            String user = userField.getText();
            String address = addressField.getText();
            String time = timeField.toString();

/*** TODO: Need to validate ...

            //Validating the fields to insure that no mistake has been made
            if(validation(name, minAgeField.getText(),description))
            {
                int minAge = Integer.parseInt(minAgeField.getText());

                ActivityCreateController.submitButton(status, description, date, user, address, time);
                statusField.clear();
                minAgeField.clear();
                descriptionField.clear();
                startTimeBox.setValue("Start tid");
                endTimeBox.setValue("Slut tid");
                stage.close();
            }
            */
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
