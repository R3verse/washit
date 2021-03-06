package View;

import Controllers.UserCreateController;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by x on 13/05/2016.
 */
public class CreateUserView
{
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

    private SessionModel session = SessionModel.getInstance();

    public static ObservableList<User> getData()
    {
        ObservableList<User> list = FXCollections.observableArrayList();
        list.addAll(UserModel.getInstance().getUserList());
        return list;
    }

    public BorderPane userCreateView(Stage stage)
    {

        UserCreateController ucc = new UserCreateController();
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();


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
        firstNameField.setText(firstNameField.getText());


        Label lastNameLbl = new Label("Lastname:");
        gridPane.add(lastNameLbl, 0, 2);


        lastNameField = new TextField();
        gridPane.add(lastNameField,1, 2);
        lastNameField.setText(lastNameField.getText());


        Label emailLbl = new Label("Email:");
        gridPane.add(emailLbl,0,3);


        emailField = new TextField();
        gridPane.add(emailField,1,3);
        emailField.setPromptText("example@email.com");
        emailField.setText(emailField.getText());



        Label addressLbl = new Label("Address:");
        gridPane.add(addressLbl, 0, 4);

        addressField = new TextField();
        gridPane.add(addressField, 1, 4);
        addressField.setText(addressField.getText());

        Label phoneNumberLbl = new Label("Phone nr.");
        gridPane.add(phoneNumberLbl, 0, 5);

        phoneNumberField = new TextField();
        gridPane.add(phoneNumberField, 1, 5);
        phoneNumberField.setPromptText("12345678");
        phoneNumberField.setText(phoneNumberField.getText()+"");

        Label passwordLabel = new Label("Password:");
        gridPane.add(passwordLabel, 0, 6);

        passwordField = new TextField();
        gridPane.add(passwordField, 1, 6);
        passwordField.setText(passwordField.getText());

        Label subscriptionLabel = new Label("Subscription:");
        gridPane.add(subscriptionLabel, 0, 7);

        subscriptionField = new TextField();
        gridPane.add(subscriptionField, 1, 7);
        subscriptionField.setPromptText("Small/Medium/Large");
        subscriptionField.setText(subscriptionField.getText());


        ToggleGroup group = new ToggleGroup();

        employeeRadioBtn = new RadioButton("Employee");
        employeeRadioBtn.setToggleGroup(group);

        gridPane.add(employeeRadioBtn, 1, 9);

        customerRadioBtn = new RadioButton("Costumer");
        customerRadioBtn.setToggleGroup(group);

        gridPane.add(customerRadioBtn, 1, 10);

        adminRadioBtn = new RadioButton("Admin");
        adminRadioBtn.setToggleGroup(group);

        if(SessionModel.getInstance().getLoggedInUser().getRole().toString().equalsIgnoreCase("USER"))
        {
            group.selectToggle(customerRadioBtn);
        }
        else if(SessionModel.getInstance().getLoggedInUser().getRole().toString().equalsIgnoreCase("Driver"))
        {
            group.selectToggle(employeeRadioBtn);
        }
        else if(SessionModel.getInstance().getLoggedInUser().getRole().toString().equalsIgnoreCase("ADMIN"))
        {
            group.selectToggle(adminRadioBtn);
        }else{}

        gridPane.add(adminRadioBtn, 1, 11);

        if(!session.isGuest() && session.getLoggedInUser().getRole() == UserRoleEnum.ADMIN){
        employeeRadioBtn.setVisible(true);
        customerRadioBtn.setVisible(true);
        adminRadioBtn.setVisible(true);
        }
        else if(!session.isGuest() && session.getLoggedInUser().getRole() == UserRoleEnum.Driver)
        {
            employeeRadioBtn.setVisible(true);
            customerRadioBtn.setVisible(true);
        }
        else{

            customerRadioBtn.setVisible(true);
            employeeRadioBtn.setVisible(true); // Never let anonymous users be admin from begnining!
            adminRadioBtn.setVisible(true);  // Never let anonymous users be admin from begnining!
        }


        Button submitBtn = new Button("Submit");
        gridPane.add(submitBtn, 1, 12);
        submitBtn.setPrefWidth(190);


        submitBtn.setOnAction(e ->
        {
            // check first if there is any RadioButtons selected, else output error.
            if(!customerRadioBtn.isSelected() && !employeeRadioBtn.isSelected() && !adminRadioBtn.isSelected())
        {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setContentText("No role is selected.");
            alert.setHeaderText("Please select a role type.");
            alert.show();

        }
            else
            {
                new UserCreateController().createUser(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        emailField.getText(),
                        addressField.getText(),
                        phoneNumberField.getText(),
                        getRadioBtnValues(session.getIsGuest()),//getUserNumber()
                        subscriptionField.getText()
                );

            }
        });

        if (stage != null) {
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.close();
        }

        return borderPane;
    }

    private int getRadioBtnValues(boolean isGuest)
    {

        int valueReturn = -1;

        if(!session.isGuest() && session.getLoggedInUser().getRole() == UserRoleEnum.ADMIN){
            employeeRadioBtn.setVisible(true);
            customerRadioBtn.setVisible(true);
            adminRadioBtn.setVisible(true);


            if (adminRadioBtn.isSelected()) {
                valueReturn = 2;
            }

        }
        else if(!session.isGuest() && session.getLoggedInUser().getRole() == UserRoleEnum.Driver)
        {
            employeeRadioBtn.setVisible(true);
            customerRadioBtn.setVisible(true);

            if (employeeRadioBtn.isSelected()) {
                valueReturn = 1;
            }
        }
        else{

            customerRadioBtn.setVisible(true);
            employeeRadioBtn.setVisible(false); // Never let anonymous users be admin from begnining!
            adminRadioBtn.setVisible(false);  // Never let anonymous users be admin from begnining!

            if (customerRadioBtn.isSelected()) {
                valueReturn = 0;

            }

        }

        return valueReturn;
    }
}
