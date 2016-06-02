package View;

import Controllers.UserCreateController;
import Controllers.UserEditController;
import Models.SessionModel;
import Models.User;
import Models.UserRoleEnum;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by x on 11-05-2016.
 */
public class EditUserView{
    private static EditUserView eUV;

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextField phoneNumberField;

    private TextField passwordField;
    private TextField addressField;
    private TextField subscriptionField;

    private RadioButton employeeRadioBtn;
    private RadioButton customerRadioBtn;
    private RadioButton adminRadioBtn;
    private Stage stage;

    public static EditUserView getInstance(){

        if(eUV == null)
        {
            eUV = new EditUserView();
        }
        return eUV;

    }

    public BorderPane userEditView(User user,Stage stage)
    {
        stage.initModality(Modality.APPLICATION_MODAL);

        UserCreateController ucc = new UserCreateController();
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();

        SessionModel session = SessionModel.getInstance();
        borderPane = new BorderPane();
        gridPane = new GridPane();
        borderPane.setCenter(gridPane);

        borderPane.setStyle("-fx-background-color: white");

        borderPane.setStyle("-fx-background-color: linear-gradient(white 25%, darkcyan 100%)");

        //Add image
        ImageView imv = new ImageView();
        Image image2 = new Image(LoginView.class.getResourceAsStream("../img/Washa3.png"));
        imv.setFitWidth(210);
        imv.setFitHeight(70);
        imv.setImage(image2);
        HBox pictureRegion = new HBox();
        pictureRegion.setPadding(new Insets(0, 0, 20, 0));
        pictureRegion.getChildren().add(imv);
        gridPane.add(pictureRegion, 1,0);

        gridPane.setPadding(new Insets(40, 0, 0, 60));
        gridPane.setHgap(20);
        gridPane.setVgap(10);

        Label firstNameLbl = new Label("Firstname:");
        gridPane.add(firstNameLbl, 0, 1);

        firstNameField = new TextField();
        gridPane.add(firstNameField, 1, 1);
        firstNameField.setText(user.getFirstName());


        Label lastNameLbl = new Label("Lastname:");
        gridPane.add(lastNameLbl, 0, 2);


        lastNameField = new TextField();
        gridPane.add(lastNameField,1, 2);
        lastNameField.setText(user.getLastName());


        Label emailLbl = new Label("Email:");
        gridPane.add(emailLbl,0,3);


        emailField = new TextField();
        gridPane.add(emailField,1,3);
        emailField.setPromptText("example@email.com");
        emailField.setText(user.getEMail());


        Label addressLbl = new Label("Address:");
        gridPane.add(addressLbl, 0, 4);

        addressField = new TextField();
        gridPane.add(addressField, 1, 4);
        addressField.setText(user.getAddress());

        Label phoneNumberLbl = new Label("Phone nr.");
        gridPane.add(phoneNumberLbl, 0, 5);

        phoneNumberField = new TextField();
        gridPane.add(phoneNumberField, 1, 5);
        phoneNumberField.setPromptText("12345678");
        phoneNumberField.setText(user.getTelephoneNumber()+"");

        Label passwordLabel = new Label("Password:");
        gridPane.add(passwordLabel, 0, 6);

        passwordField = new TextField();
        gridPane.add(passwordField, 1, 6);
        passwordField.setText(user.getPassword());

        Label subscriptionLabel = new Label("Subscription:");
        gridPane.add(subscriptionLabel, 0, 7);

        subscriptionField = new TextField();
        gridPane.add(subscriptionField, 1, 7);
        subscriptionField.setPromptText("Small/Medium/Large");
        subscriptionField.setText(user.getSubscriptionType());


        ToggleGroup group = new ToggleGroup();

        employeeRadioBtn = new RadioButton("Employee");
        employeeRadioBtn.setToggleGroup(group);

        gridPane.add(employeeRadioBtn, 1, 9);

        customerRadioBtn = new RadioButton("Costumer");
        customerRadioBtn.setToggleGroup(group);

        gridPane.add(customerRadioBtn, 1, 10);

        adminRadioBtn = new RadioButton("Admin");
        adminRadioBtn.setToggleGroup(group);

        if(user.getRole().toString().equalsIgnoreCase("USER"))
        {
            group.selectToggle(customerRadioBtn);
        }
        else if(user.getRole().toString().equalsIgnoreCase("Driver"))
        {
        group.selectToggle(employeeRadioBtn);
        }
        else if(user.getRole().toString().equalsIgnoreCase("ADMIN"))
        {
        group.selectToggle(adminRadioBtn);
        }else{}

        gridPane.add(adminRadioBtn, 1, 11);


        if(!session.isGuest() && SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.Driver)
        {
                customerRadioBtn.setVisible(true);
                employeeRadioBtn.setVisible(true); // Never let anonymous users be admin from begnining!
                adminRadioBtn.setVisible(false);  // Never let anonymous users be admin from begnining!

        }else if(!session.isGuest() && SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.ADMIN)
        {
            customerRadioBtn.setVisible(true);
            employeeRadioBtn.setVisible(true); // Never let anonymous users be admin from begnining!
            adminRadioBtn.setVisible(true);  // Never let anonymous users be admin from begnining!

        }

        Button submitBtn = new Button("Submit");
        gridPane.add(submitBtn, 1, 12);
        submitBtn.setPrefWidth(200);

        submitBtn.setOnAction(e ->
        {
            user.setFirstName(firstNameField.getText());
            user.setLastName(lastNameField.getText());
            user.seteMail(emailField.getText());
            user.setPassword(passwordField.getText());
            user.setAddress(addressField.getText());
            user.setTelephoneNumber(Integer.parseInt(phoneNumberField.getText()));

            UserRoleEnum uRE = null;
            if(employeeRadioBtn.isSelected()){
                uRE = UserRoleEnum.Driver;
            }else if(customerRadioBtn.isSelected()){
                uRE = UserRoleEnum.USER;
            }else if(adminRadioBtn.isSelected()){
                uRE = UserRoleEnum.ADMIN;
            }else{
                uRE = user.getRole();
            }
            user.setRole(uRE);
            UserEditController uEC = new UserEditController();

            try{

            if(uEC.tryUpdate(user)){
                stage.close();
            }
            }catch(Exception e2){
                System.err.println("Couldn't parse: " + e2);
                uEC.setAlert("Error","Couldn't parse date correctly!");
            }
        });

        return borderPane;
    }

    private int getRadioBtnValues(boolean isGuest)
    {
        int valueReturn = -1;
        if(!isGuest)
        {
            if (employeeRadioBtn.isSelected()) {
                valueReturn = 1;
            }
            else if (adminRadioBtn.isSelected()) {
                valueReturn = 2;

            }
            else if (customerRadioBtn.isSelected()) {
                valueReturn = 0;

            }
            else
            {
                System.out.println("Error");
            }
        }
        else
        {
            valueReturn = 0;
        }
        return valueReturn;
    }
}

