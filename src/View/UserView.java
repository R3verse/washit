package View;

import Controllers.UserEditController;
import Controllers.UserViewController;
import Models.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by x on 7-05-2016.
 */
public class UserView{

    private TextField searchUserName = new TextField();
    private TextField searchEmail = new TextField();
    TableView<User> usersTableView;

    public BorderPane innerBorderPaneUsers()
    {
        BorderPane layout = new BorderPane();
        usersTableView = new TableView<>();


        //Image Magnifier
        ImageView imv = new ImageView();
        Image image = new Image(LoginView.class.getResourceAsStream("../img/MagnifierGlass.png"));
        imv.setImage(image);
        imv.setOnMouseClicked(e->
        {
            UserViewController.search(usersTableView, searchUserName,searchEmail);
        });
////////////////////////
        //Image Plussign
        ImageView imv2 = new ImageView();
        imv2.setFitHeight(30);
        imv2.setFitWidth(30);
        Image imagePlusSign = new Image(LoginView.class.getResourceAsStream("../img/Plus.png"));
        imv2.setImage(imagePlusSign);

        CreateUserView createUserView = new CreateUserView();
        //Image Plussign onMouseClick to handle Create Activity
        imv2.setOnMouseClicked(event ->
        {
            create();
        });

        //Two HBox' to the top menu
        HBox collector = new HBox();
        HBox createNewUser = new HBox();
        HBox searchUserHolder = new HBox();

        //Collecting the two HBox'
        collector.getChildren().addAll(createNewUser, searchUserHolder,imv);

        //Positioning of the HBox'
        createNewUser.setAlignment(Pos.CENTER_LEFT);
        createNewUser.setPrefWidth(400);
        searchUserHolder.setAlignment(Pos.CENTER_RIGHT);
        searchUserHolder.setSpacing(3);

        //Customizing the textfields

        searchUserName.setPromptText("Search User...");
        searchEmail.setPromptText("Search Email...");


        //Settings the HBox'
        createNewUser.getChildren().addAll(imv2);
        searchUserHolder.getChildren().addAll(searchUserName,searchEmail);
        createNewUser.setStyle("-fx-background-color: white");
        layout.setTop(collector);

        layout.setCenter(usersTableView);

        //ContextMenu as popup when right clicking a column on the ActivityTableView
        ContextMenu cMenu = new ContextMenu();
        cMenu.setStyle("-fx-background-color: #67db6e, \n" +
                "        linear-gradient(#7ebcea, #67db6e)," +
                "        linear-gradient(#67db6e, #7ebcea);" +
                "        -fx-font-weight: bold;" +
                "        -fx-background-radius: 6;" +
                "        -fx-text-fill: white");
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");
        cMenu.getItems().addAll(editItem, deleteItem);

        //The right click handling
        usersTableView.setOnMouseClicked(event ->
        {
            if(event.getButton() == MouseButton.SECONDARY){
                usersTableView.setContextMenu(cMenu);
            }
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                User user = usersTableView.getSelectionModel().getSelectedItem();
               edit(user);
            }
        });

        editItem.setOnAction(event ->
        {
            User user = usersTableView.getSelectionModel().getSelectedItem();
            edit(user);
        });

        deleteItem.setOnAction(event ->
        {
            User user = usersTableView.getSelectionModel().getSelectedItem();
            delete(user);
        });

        UserViewController controller = new UserViewController();

        controller.btnClickedToShowUsers(usersTableView);

        return layout;
    }
    private void delete(User user)
    {
        if(UserViewController.setConfirmAlert(user))
        {
            UserViewController userViewController = new UserViewController();
            userViewController.tryDeleteUser(user);
            usersTableView.setItems(CreateUserView.getData());// FIXME: 02-03-2016 Gør det fra den rigtige controller
        }
    }
    public void create(){
        Stage stage = new Stage();
        stage.setScene(new Scene(new CreateUserView().userCreateView(stage),450,600));
        stage.showAndWait();
        usersTableView.setItems(CreateUserView.getData());
    }
    private void edit(User user)
    {

        Stage stage = new Stage();
        stage.setScene(new Scene(new EditUserView().userEditView(user,stage),450,600));
        stage.showAndWait();
        usersTableView.setItems(CreateUserView.getData());
    }
}
