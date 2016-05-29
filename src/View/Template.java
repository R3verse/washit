package View;

import Models.SceneSwitchHelper;
import Models.SessionModel;
import Models.User;
import Models.UserRoleEnum;
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

import java.util.Date;

/**
 * Created by AlexanderFalk on 23/02/2016.
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
        SessionModel.getInstance().setUserSession(new User("asdf","sasdf","asdf","ADSFA",1337,80085, UserRoleEnum.ADMIN,new Date()));
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
        Image image2 = new Image(LoginView.class.getResourceAsStream("../img/adventure.png"));

        imv.setImage(image2);

        //VBox color
        leftRootVBox.setStyle("-fx-background-color: linear-gradient(white 20%, #dda200 100%)");

        //VBox Contraints
        leftRootVBox.setPrefWidth(150);

        Label loggedInAsLabel = new Label();

        showWelcomeMessage(loggedInAsLabel); // showing if Employee/Admin/User as label

        //Buttons & coloring & Size
        Button btnActivity = new Button("Activities");
        btnActivity.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");
        btnActivity.setPrefSize(150, 30);
        Button btnBooking = new Button("Bookings");
        btnBooking.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");
        btnBooking.setPrefSize(150, 30);
        Button btnUser = new Button("Users");
        btnUser.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");
        btnUser.setPrefSize(150, 30);
        Button btnLogout = new Button("Logout");
        btnLogout.setStyle("-fx-background-color: linear-gradient(white 00%, #67db6e 100%)");
        btnLogout.setPrefSize(150, 30);

        ActivityView activityView = new ActivityView();

        //Setting center when the Activity button is clicked
        btnActivity.setOnAction(event ->
            root.setCenter(activityView.innerRootBorderPaneActivities())
        );

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
            leftRootVBoxForButtons.getChildren().addAll(loggedInAsLabel, btnActivity, btnBooking, btnUser, btnLogout);
        }
        if (SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.EMPLOYEE)
        {
            leftRootVBoxForButtons.getChildren().addAll(loggedInAsLabel, btnActivity, btnBooking, btnLogout);
        }

        //VBox for Buttons
        leftRootVBoxForButtons.setAlignment(Pos.BASELINE_CENTER);

        leftRootVBoxForButtons.setSpacing(5);

        //Style & positions for root borderpane
        root.setStyle("-fx-background-color: white");
        root.setLeft(leftRootVBox);

        return root;
    }

    private void showWelcomeMessage(Label loggedInAsLabel) {
        if(SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.ADMIN)
        {
            loggedInAsLabel.setText("Welcome Administrator");

        }
        else if(SessionModel.getInstance().getLoggedInUser().getRole() == UserRoleEnum.EMPLOYEE)
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
