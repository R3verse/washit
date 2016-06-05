package View;

import Models.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Gruppeaflevering: Max, Memet & Thomas
 */
public class Template extends Application
{
    private BorderPane root = new BorderPane();
    private ActivityView av = new ActivityView();
    private SessionModel userSession = null;
    private static User loggedInUser;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setScene(new Scene(startScene(), 1000, 600));
        primaryStage.show();
    }
    public static void main(String[] args)
    {
        // ID, fName, lName, eMail, password, addr, subscription, tlfPhNr, role
        SessionModel.getInstance().setUserSession(new User(213, "asdf","sasdf","asdf","ADSFA","adwd","awdwdw", 2332, UserRoleEnum.ADMIN));
        launch(args);
    }

    public BorderPane startScene()
    {
        //The panes
        root = new BorderPane();
        VBox leftRootVBox = new VBox();
        VBox leftRootVBoxForButtons = new VBox();
        VBox leftRootVBoxForImage = new VBox();

        //The image to the first VBox
        ImageView imv = new ImageView();
        Image image2 = new Image(LoginView.class.getResourceAsStream("../img/Washa3.png"));
        imv.setFitHeight(70);
        imv.setFitWidth(210);

        imv.setImage(image2);

        //VBox color
        leftRootVBox.setStyle("-fx-background-color: linear-gradient(white 25%, darkcyan 100%)");

        //VBox Contraints
        leftRootVBox.setPrefWidth(150);

        Label loggedInAsLabel = new Label();

        showWelcomeMessage(loggedInAsLabel); // showing if Employee/Admin/User as label

        //Buttons & coloring & Size
        Button createUserbtn = new Button("Create User");
        createUserbtn.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");
        createUserbtn.setPrefSize(150, 30);

        CreateUserView ucv = new CreateUserView();


        Button btnBooking = new Button("Bookings");
        btnBooking.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");
        btnBooking.setPrefSize(150, 30);
        Button btnUser = new Button("Users");
        btnUser.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");
        btnUser.setPrefSize(150, 30);
        Button btnLogout = new Button("Logout");
        btnLogout.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");
        btnLogout.setPrefSize(150, 30);

        createUserbtn.setOnAction(event ->  {
            //root.setCenter(activityView.innerRootBorderPaneActivities()
            System.out.println("Opening create user... ");
       // SceneSwitchHelper.setScene(ucv.userCreateView(null), 420, 570, "Create User");
            Stage stage = new Stage();
            stage.setScene(new Scene(new CreateUserView().userCreateView(stage),450,600));
            stage.showAndWait();

        });

        btnLogout.setOnAction(logoutEvent -> {
            SceneSwitchHelper.setScene(new LoginView().getBorderPane(), 420, 340, "Login");
        });

        BookingView bookingView = new BookingView();

        //The button for Booking
        btnBooking.setOnAction(e -> root.setCenter(bookingView.innerBorderPaneBookings()));

        UserView userView = new UserView();

        //Setting center when the User button is clicked

        btnUser.setOnAction(event -> root.setCenter(userView.innerBorderPaneUsers()));

        //Adding to the ROOTVBOX
        leftRootVBox.getChildren().addAll(leftRootVBoxForImage, leftRootVBoxForButtons);

        //VBox for Image
        leftRootVBoxForImage.getChildren().add(imv);
        leftRootVBoxForImage.setPrefHeight(200);

        //VBox for Buttons && Validation on the role of the user
        if (SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.USER)
        {
            leftRootVBoxForButtons.getChildren().addAll(btnBooking, btnLogout);
        }
        if (SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.ADMIN)
        {
            leftRootVBoxForButtons.getChildren().addAll(loggedInAsLabel, createUserbtn, btnBooking, btnUser, btnLogout);
        }
        if (SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.Driver)
        {
            leftRootVBoxForButtons.getChildren().addAll(loggedInAsLabel, createUserbtn, btnBooking, btnLogout);
        }

        //VBox for Buttons
        leftRootVBoxForButtons.setAlignment(Pos.BASELINE_CENTER);

        leftRootVBoxForButtons.setSpacing(5);

        //Style & positions for root borderpane
        root.setStyle("-fx-background-color: white");
        root.setLeft(leftRootVBox);

        return root;
    }

    private void showWelcomeMessage(Label loggedInAsLabel)
    {
        if(SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.ADMIN)
        {
            loggedInAsLabel.setText("Welcome Administrator");

        }
       else if(SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.Driver)
        {
           loggedInAsLabel.setText("Welcome "+SessionModel.getInstance().getLoggedInUser().getRole());

        }
        else if(SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.USER)
        {
            loggedInAsLabel.setText("Welcome "+SessionModel.getInstance().getLoggedInUser().getRole());
        }else{
            // Not logged in as a valid user/customer/admin
        }
    }
}
