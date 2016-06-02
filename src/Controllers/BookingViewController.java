package Controllers;
import Models.*;
import Models.Activities;
import Models.ActivityModel;
import Models.Booking;
import Models.BookingModel;
import View.CreateBookingView;
import View.EditBookingView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by x on 12/5/16.
 */
public class BookingViewController {

    public static ObservableList<Booking> setBookings()
    {
        ObservableList<Booking> data = FXCollections.observableArrayList();

        if (SessionModel.getInstance().getLoggedInUser() == null || SessionModel.getInstance().getLoggedInUser().getRole() != UserRoleEnum.USER)
        {
            data.setAll(BookingModel.getInstance().getBookings());
        }
        else if (SessionModel.getInstance().getLoggedInUser() != null)
        {
            data.setAll(BookingModel.getInstance().getBookings());
            System.out.println(data.size());
            for(int i = 0; i < data.size(); i++)
            {
                if (data.get(i).getUserID() != SessionModel.getInstance().getLoggedInUser().getID())
                {
                    data.remove(i);
                    i--;
                }
            }
        }

        return data;
    }

    public void btnClickedToShowBookings(TableView<Booking> tableView)
    {
        TableColumn idColumns = new TableColumn("Id");
        idColumns.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn status = new TableColumn("Status");
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn description = new TableColumn("Description");
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn userIdColumn = new TableColumn("User");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn address = new TableColumn("Address");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn timeColumn = new TableColumn("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));


        tableView.getColumns().addAll(idColumns, status, description,
                dateColumn, userIdColumn, address, timeColumn);

        tableView.setItems(setBookings());
    }

    public void search(TableView<Booking> bookingsTableView, TextField name, TextField user, TextField date)
    {
        List<Booking> bookings = BookingModel.getInstance().getBookings();
        List<Booking> bookingSearch = new ArrayList<>();

        String searchActivityName = name.getText();
        String searchUserName = user.getText();

        for (Booking b : bookings)

        {

            if (searchActivityName.length() != 0 && searchUserName.length() != 0)
            {
                /*
                if (b.getUserName().toLowerCase().contains(searchUserName.toLowerCase()) &&
                      b.getActivityName().toLowerCase().contains(searchActivityName.toLowerCase()))
                {
                    bookingSearch.add(b);
                }
                */
            }
            else if(searchActivityName.length()==0 && searchUserName.length()!=0)
            {
                if(b.getUserName().toLowerCase().contains(searchUserName.toLowerCase()))
                {
                    bookingSearch.add(b);
                }
            }

            else if(searchActivityName.length()!=0 && searchUserName.length()==0)
            {
                //   if(b.getActivityName().toLowerCase().contains(searchActivityName.toLowerCase()))
                {
                    bookingSearch.add(b);
                }
            }

            else
            {
                bookingSearch.add(b);
            }
        }

        ObservableList<Booking> bookingList = FXCollections.observableArrayList(bookingSearch);
        bookingsTableView.setItems(bookingList);
    }
    public boolean setConfirmAlert(Booking booking)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Slet booking");
        //  alert.setHeaderText("Er du sikker på at du vil slette "+booking.getActivityName()+" bookingen for " +booking.getUserName()+ "?");
        alert.setContentText("Den vil blive slettet permanent");

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
    public void create(TableView<Booking> bookingTableView)
    {
        Stage stage = new Stage();
        stage.setScene(new Scene(new CreateBookingView().btnCreateBooking(stage), 600, 240));
        stage.showAndWait();
        bookingTableView.setItems(BookingViewController.setBookings());
    }
    public void delete(TableView<Booking> bookingTableView)
    {
        if(setConfirmAlert(bookingTableView.getSelectionModel().getSelectedItem()))
        {
            BookingModel.getInstance().deleteBooking(bookingTableView.getSelectionModel().getSelectedItem().getID());
            ObservableList<Booking> data = FXCollections.observableArrayList(BookingModel.getInstance().getBookings());
            bookingTableView.setItems(data);
        }
    }
    public void edit(TableView<Booking> bookingTableView)
    {
        Booking booking = bookingTableView.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        stage.setScene(new Scene(EditBookingView.pushAndEdit(booking,stage), 600, 240));
        stage.showAndWait();
        bookingTableView.setItems(BookingViewController.setBookings());
    }

}
