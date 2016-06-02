package Controllers;

import Models.User;
import Models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by x on 11-5-2016.
 */
public class UserViewController{
    public ObservableList<User> setUsers()
    {
        ObservableList<User> data = FXCollections.observableArrayList();
        data.setAll(UserModel.getInstance().getUserList());
        return data;
    }

    public void btnClickedToShowUsers(TableView<User> tableView)
    {
        TableColumn idColumns = new TableColumn("Id");
        idColumns.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn usernameColumn = new TableColumn("User");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn eMailColumn = new TableColumn("Email");
        eMailColumn.setCellValueFactory(new PropertyValueFactory<>("eMail"));

        TableColumn passwordIdColumn = new TableColumn("Password");
        passwordIdColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn roleColumn = new TableColumn("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn addressColumn = new TableColumn("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn phoneColumn = new TableColumn("PhoneNo");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneNumber"));

        TableColumn subscriptionColumn = new TableColumn("Subscription");
        subscriptionColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionType"));





        tableView.getColumns().addAll(idColumns, usernameColumn, eMailColumn, passwordIdColumn, roleColumn, addressColumn, phoneColumn, subscriptionColumn);

        tableView.setItems(setUsers());
    }

    public static void search(TableView<User> userTableView, TextField name, TextField email)
    {
        //TODO : ************ MAKE THIS WORK PLS ;^) ************
        List<User> Users = UserModel.getInstance().getUserList();
        List<User> userSearch = new ArrayList<>();

        String searchEmail = email.getText();
        String searchUserName = name.getText();

        for (User u : Users)
        {
            if (searchEmail.length() != 0 && searchUserName.length() != 0)
            {
                if (u.getUserName().toLowerCase().contains(searchUserName.toLowerCase()) &&
                        u.getEMail().toLowerCase().contains(searchEmail.toLowerCase()))
                {
                    userSearch.add(u);
                }
            }
            else if(searchEmail.length()==0 && searchUserName.length()!=0)
            {
                if(u.getUserName().toLowerCase().contains(searchUserName.toLowerCase()))
                {
                    userSearch.add(u);
                }
            }

            else if(searchEmail.length()!=0 && searchUserName.length()==0)
            {
                if(u.getEMail().toLowerCase().contains(searchEmail.toLowerCase()))
                {
                    userSearch.add(u);
                }
            }
            else
            {
                userSearch.add(u);
            }
        }

        ObservableList<User> userList = FXCollections.observableArrayList(userSearch);
        userTableView.setItems(userList);
    }
    public void tryDeleteUser(User user)
    {
        UserModel.getInstance().deleteUser(user.getID());
    }
    public static boolean setConfirmAlert(User user)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Slet user");
        alert.setHeaderText("Er du sikker på at du vil slette" + user.getUserName() +"?");
        alert.setContentText("Personen vil blive slettet permanent");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
