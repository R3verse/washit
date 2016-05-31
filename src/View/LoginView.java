package View;

import Controllers.UserCreateController;
import Controllers.UserLoginController;
import Models.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by x on 13/05/2016.
 */
public class LoginView extends Application
{
    GridPane gridPane;
    BorderPane borderPane;
    Scene scene;

    Label emailLbl;
    Label passwordLbl;

    TextField emailField;
    PasswordField passwordField;

    Button loginBtn;

    Hyperlink accountHLink;

    ImageView imv;
    Image image2;
    HBox pictureRegion;

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        SceneSwitchHelper.setPrimaryStage(primaryStage);
        borderPane = getBorderPane();
        scene = new Scene(borderPane, 420, 340);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }


    public BorderPane getBorderPane()
    {
        borderPane = new BorderPane();
        gridPane = new GridPane();
        borderPane.setCenter(gridPane);
        borderPane.setStyle("-fx-background-color: white");

        gridPane.setPadding(new Insets(30, 0, 0, 60));
        gridPane.setHgap(20);
        gridPane.setVgap(10);

        //Add image
        imv = new ImageView();
        image2 = new Image(LoginView.class.getResourceAsStream("../img/Washa.png"));
        imv.setImage(image2);
        imv.setFitWidth(220);
        imv.setFitHeight(70);
        pictureRegion = new HBox();
        pictureRegion.getChildren().add(imv);
        gridPane.add(pictureRegion, 1,0);

        emailLbl = new Label("Email:");
        gridPane.add(emailLbl, 0, 1);

        emailField = new TextField();
        gridPane.add(emailField, 1, 1);

        emailField.setOnKeyPressed(e -> {

            if(e.getCode() == KeyCode.ENTER){
                ValidationModel.loginValidation(emailField.getText(), passwordField.getText());
            }
        });


        passwordLbl = new Label("Password:");
        gridPane.add(passwordLbl, 0, 2);

        passwordField = new PasswordField();
        gridPane.add(passwordField, 1, 2);

        passwordField.setOnKeyPressed(e -> {

            if(e.getCode() == KeyCode.ENTER){

                ValidationModel.loginValidation(emailField.getText(), passwordField.getText());
            }
        });

        loginBtn = new Button("Login");
        gridPane.add(loginBtn, 1, 3);
        loginBtn.setPrefWidth(100);

        accountHLink = new Hyperlink("Don't have an account?");
        CreateUserView ucv = new CreateUserView();
        //accountHLink.setOnAction(e -> );
        gridPane.add(accountHLink, 0, 7, 2, 1);

        //Just testing...
        loginBtn.setOnAction(e ->
        {
            if(new UserLoginController().doLogin(emailField.getText(),passwordField.getText()));
            {
                //primaryStage.setScene();
            }
        });

        accountHLink.setOnAction(e -> {
            System.out.println("Opening create user... ");
            SceneSwitchHelper.setScene(ucv.userCreateView(null), 420, 570, "Create User");
        });

        //gridPane.setGridLinesVisible(true);

        return borderPane;
    }


}
