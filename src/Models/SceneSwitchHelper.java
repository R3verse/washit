package Models;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by B on 24-02-2016.
 */
public class SceneSwitchHelper
{
    private static Stage primaryStage;

    public static Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage stage)
    {
        primaryStage = stage;
    }

    public static void setScene(Parent container, int width, int height, String title)
    {
        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(container,width,height));
        primaryStage.centerOnScreen();
    }
}
