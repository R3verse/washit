package Controllers;

import Models.SceneSwitchHelper;
import Models.SessionModel;
import Models.UserModel;
import View.CreateUserView;
import View.LoginView;
import javafx.scene.control.Alert;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCreateController
{
    public UserCreateController()
    {

    }

    public void createUser(String fName, String lName, String email, String address, String phone, String bday, int type)
    {
        java.sql.Date bDate = null;
        int phoneNo = -1;
        boolean failed = false;
        try
        {
            java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(bday);
            bDate = new java.sql.Date(utilDate.getTime());
            phoneNo = Integer.parseInt(phone);
        }
        catch (Exception e)
        {
            System.out.println("Error couldnt parse date or phoneNo");
            setAlert("***Error***", "Error couldnt parse date or phoneNo", "You might not have filled your information in.");
            failed = true;
        }

        if(!failed) {
            System.out.println(fName + " " + lName + " " + email + " " + address + " " + phoneNo + " " + type + " " + bDate + " " + "1234");

                if (phoneNo == -1 && bDate == null) {
                    System.out.println("invalid number and date");
                    setAlert("***Error***", "invalid number and date", "Number and Date fields!");
                } else if (bDate == null) {
                    System.out.println("Invalid birthday");
                    setAlert("***Error***", "invalid birthday", "birthday field!");
                } else if (phoneNo == -1) {
                    System.out.println("Invalid phoneNo");
                    setAlert("***Error***", "invalid phone number", "Phone field!");
                } else if (!validatePhoneNo(phone)) {
                    // show popup
                } else if (!validateEmail(email)) {
                    // show popup
                }

                else if(!validateNames(fName) || !validateNames(lName))
                {
                    // show popup
                }
                else {
                    // TODO: 25-02-2016 1234 er standardkode, skal laves om
                    try {
                        if (!UserModel.getInstance().doesUserExsists(email)) {
                            UserModel.getInstance().createUser(fName, lName, email, address, phoneNo, type, bDate, "1234");
                            setAlert("Success!", "User was successfully created!", "Username: " + email + "\nPassword: 1234");
                            System.out.println("Success");
                        } else {
                            setAlert("Error!", "A user with this email already exists!", "email field!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (SessionModel.getInstance().getLoggedInUser() == null) {
                        SceneSwitchHelper.setScene(new LoginView().getBorderPane(), 420, 340, "Login");
                    }
                }

        }
    }

    public static void setAlert(String titleText, String headerText, String errorType)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleText);
        alert.setContentText(errorType);
        alert.setHeaderText(headerText);
        alert.show();
    }


    private static boolean validatePhoneNo(String phoneNo) {
        Pattern p = Pattern.compile("^(?!\\s*$)[0-9\\s]{8}$"); // match excatly 8 numbers
        Matcher m = p.matcher(phoneNo);
        if (m.find() && m.group().equals(phoneNo)) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setContentText("Please enter a valid phone number.");
            alert.setHeaderText("");
            alert.show();

            return false;
        }
    }

    private static boolean validateEmail(String eMail) {
        Pattern p = Pattern.compile("[a-zA-Z0-0][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+"); // match an email address
        Matcher m = p.matcher(eMail);
        if (m.find() && m.group().equals(eMail)) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setContentText("Please enter a valid email.");
            alert.setHeaderText("");
            alert.show();

            return false;
        }
    }


    private static boolean validateNames(String name) {
        Pattern p = Pattern.compile("[^-?0-9]+"); // match an email address
        Matcher m = p.matcher(name);
        if (m.find() && m.group().equals(name)) {
            return true;
        } else {
            setAlert("ERROR!", "Not allowed having numbers.", "Please input a correct name\nthat does not contain any numbers!");

            return false;
        }
    }

}