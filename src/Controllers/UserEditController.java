package Controllers;

import Models.User;
import Models.UserModel;
import javafx.scene.control.Alert;

/**
 * Created by x on 11-5-2016.
 */
public class UserEditController
{
    public boolean tryUpdate(User user)
    {
        boolean completed = false;
        try{
            UserModel.getInstance().updateUser(user);
            completed = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return completed;
    }
    public void setAlert(String titleText,String headerText)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleText);
        alert.setContentText("Please fill out all the fields...");
        alert.setHeaderText(headerText);
        alert.show();
    }
}
