package Controllers;
import Models.DatabaseConnector;
import Models.SceneSwitchHelper;
import Models.SessionModel;
import Models.UserModel;
import View.Template;
import javafx.scene.control.Alert;

/**
 * Gruppeaflevering: Max, Thomas & Memet
 */
public class UserLoginController
{
    private DatabaseConnector dbc = DatabaseConnector.getInstance();
    public void setAlert(String titleText,String headerText)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleText);
        alert.setContentText("Username or password missing...");
        alert.setHeaderText(headerText);
        alert.show();
    }
    public boolean doLogin(String email, String pass)
    {
        boolean switchScene = false;
        UserModel userModel = UserModel.getInstance();
        if(userModel.getUserId(email,pass)>=1)
        {
            SessionModel.getInstance().setUserSession(UserModel.getInstance().getUser(userModel.getUserId(email,pass)));
            System.out.println(SessionModel.getInstance().getLoggedInUser().getEMail());
            SceneSwitchHelper helper = new SceneSwitchHelper();
            SceneSwitchHelper swh = new SceneSwitchHelper();
            swh.setScene(new Template().startScene(),1000,600,"Main");
        }
        else
        {
            setAlert("fejl","fejl");
        }
        return switchScene;
    }
}
