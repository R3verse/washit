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
    private TextField addressField;
    private TextField phoneNumberField;

    private DatePicker birthdayPicker;

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
        imv.setImage(image2);
        imv.setFitWidth(220);
        imv.setFitHeight(70);
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


        Label lastNameLbl = new Label("Lastname:");
        gridPane.add(lastNameLbl, 0, 2);


        lastNameField = new TextField();
        gridPane.add(lastNameField,1, 2);


        Label emailLbl = new Label("Email:");
        gridPane.add(emailLbl,0,3);


        emailField = new TextField();
        gridPane.add(emailField,1,3);


        Label addressLbl = new Label("Address:");
        gridPane.add(addressLbl, 0, 4);

        addressField = new TextField();
        gridPane.add(addressField, 1, 4);

        Label phoneNumberLbl = new Label("Phone nr.");
        gridPane.add(phoneNumberLbl, 0, 5);

        phoneNumberField = new TextField();
        gridPane.add(phoneNumberField, 1, 5);
        phoneNumberField.setPromptText("12345678");

       // Label birthdayLbl = new Label("Birthday:");
       // gridPane.add(birthdayLbl, 0, 6);

      //  birthdayPicker = new DatePicker();
      //  gridPane.add(birthdayPicker, 1, 6);

        ToggleGroup group = new ToggleGroup();

        employeeRadioBtn = new RadioButton("Driver");
        employeeRadioBtn.setToggleGroup(group);

        gridPane.add(employeeRadioBtn, 1, 8);

        customerRadioBtn = new RadioButton("Customer");
        customerRadioBtn.setToggleGroup(group);

        gridPane.add(customerRadioBtn, 1, 9);

        adminRadioBtn = new RadioButton("Admin");
        adminRadioBtn.setToggleGroup(group);

        gridPane.add(adminRadioBtn, 1, 10);

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



        Button backBtn = new Button("Go back");
        backBtn.setPrefWidth(100);
        gridPane.add(backBtn, 0, 12);

        backBtn.setOnAction(event ->{
            SceneSwitchHelper.setScene(new LoginView().getBorderPane(), 420, 340, "Login");
        });

        Button submitBtn = new Button("Submit");
        gridPane.add(submitBtn, 1, 12);
        submitBtn.setPrefWidth(190);

        Button closeBtn = new Button("Close");
        closeBtn.setPrefWidth(100);
        gridPane.add(closeBtn, 2, 12);
        closeBtn.setPrefWidth(100);

        closeBtn.setOnAction(event -> {
            if(SceneSwitchHelper.getPrimaryStage().isShowing()){
                SceneSwitchHelper.getPrimaryStage().close();
            }else{
                stage.close();
            }
        });

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
                        birthdayPicker.getEditor().getText(),
                        getRadioBtnValues(session.getIsGuest())//getUserNumber()
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
