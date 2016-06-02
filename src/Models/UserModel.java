package Models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Connection and retrivel of User information
 */
public class UserModel {

    private Connection con;
    private List<User> userList;
    private static UserModel user = null;
    private boolean foundUser;

    /**
     * @auth Troels, Max og Lucas måske
     */

    public static UserModel getInstance() {
        if (user == null) {
            user = new UserModel();
        }
        return user;
    }

    private UserModel() {
        try {
            DatabaseConnector dbConnector = DatabaseConnector.getInstance();
            con = dbConnector.getConnection();
        } catch (ClassNotFoundException eCNF) {
            eCNF.printStackTrace();
        } catch (SQLException eSQL) {
            eSQL.printStackTrace();
        }
        userList = getUserList();
    }

    public UserRoleEnum getRole(int role) {
        UserRoleEnum userRoleEnum;

        switch (role) {
            case 0:
                userRoleEnum = UserRoleEnum.USER;
                break;
            case 1:
                userRoleEnum = UserRoleEnum.Driver;
                break;
            default:
                userRoleEnum = UserRoleEnum.ADMIN;
                break;
        }
        return userRoleEnum;
    }

    public List<User> getUserList() {
        List<User> listReturn = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = con.createStatement();
            statement.executeQuery("SELECT * FROM users");
            rs = statement.getResultSet();
            while (rs.next()) {
                String fName = rs.getString("firstName");
                String lName = rs.getString("lastName");
                String eMail = rs.getString("eMail");
                String password = rs.getString("password");
                UserRoleEnum role = getRole(rs.getInt("role"));
                String addr = rs.getString("address");
                int phoneNo = rs.getInt("telephoneNumber");
                String subscription = rs.getString("subscriptionType");
                int ID = rs.getInt("ID");

                // User(String firstName, String lastName, String eMail,
                // ID, fName, lName, eMail, password, addr, subscription, tlfPhNr, role
                listReturn.add(new User(ID,
                        fName,
                        lName,
                        eMail,
                        password,
                        addr,
                        subscription,
                        phoneNo,
                        role
                ));
            }
        } catch (SQLException eSQL) {
            eSQL.printStackTrace();
        }
        return listReturn;
    }

    public int getUserId(String email, String pass) {
        Statement statement = null;
        ResultSet rs = null;
        int ID = 0;
        try {
            statement = con.createStatement();
            statement.executeQuery("SELECT * FROM users WHERE email ='" + email + "' AND password = '" + pass + "'");
            rs = statement.getResultSet();

            if (rs.next()) {
                ID = rs.getInt("ID");
            }
        } catch (SQLException eSQL) {
            eSQL.printStackTrace();
        }
        return ID;
    }

    public User getUser(int id) {
        Statement statement = null;
        ResultSet rs = null;
        User userReturn = null;
        try {
            statement = con.createStatement();
            statement.executeQuery("SELECT * FROM users WHERE id='" + id + "'");
            rs = statement.getResultSet();

            if (rs.next()) {
                String fName = rs.getString("firstName");
                String lName = rs.getString("lastName");
                String eMail = rs.getString("eMail");
                String password = rs.getString("password");
                String addr = rs.getString("address");
                String subscription = rs.getString("subscriptionType");
                int ID = rs.getInt("ID");
                UserRoleEnum role = getRole(rs.getInt("role"));
                int tlfPhNr = rs.getInt("telephoneNumber");


                userReturn = new User(ID, fName, lName, eMail, password, addr, subscription, tlfPhNr, role);
            }
        } catch (SQLException eSQL) {
            eSQL.printStackTrace();
        }
        return userReturn;
    }

    // Get user from userList
    public User getUserFromList(User usr) {
        User usrReturn = null;
        for (User ussr : userList) {
            if (ussr == usr) {
                usrReturn = ussr;
            } else {
                usrReturn = null;
            }
        }
        return usrReturn;
    }

    public void doReworkToUserList(User userRework) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getID() == userRework.getID()) {
                userList.remove(i);
                userList.add(i, userRework);
                break;
            }
        }

    }

    public void createUser(String firstName, String lastName, String eMail, String address, int telephoneNo, int role, String subcriptionType, String passWord) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO users(firstName, lastName, email, address, telephoneNumber, subscriptionType, role, password)" +
                    "VALUES ('" + firstName + "', '" + lastName + "', '" + eMail + "', '" + address + "', '" + telephoneNo + "', '" + role + "', '" + subcriptionType + "', '" + passWord + "')");

            System.out.println("Inserted user into database");
        } catch (SQLException e) {
            System.err.println("Cannot insert values into Users: " + e);
        }
    }


    public boolean doesUserExsists(String eMail) throws  SQLException {
        boolean boolReturn = false;
        Statement stmt = con.createStatement();
        String query = "SELECT eMail FROM users WHERE eMail = '"+ eMail + "'";
        System.out.println(query);

        try {
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next())
            {
                String checkUser = rs.getString(1);

                if (checkUser.equals(eMail))
                {
                    System.out.println("User with this email already exists!");
                    boolReturn = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boolReturn;
    }

    public void deleteUser(int id)
    {
        try{
            Statement st = con.createStatement();
            st.executeUpdate("DELETE FROM users WHERE ID = ('"+id+"')");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void updateUser(User user)
    {
        try {

            int userId = user.getID();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String email = user.getEMail();
            String address = user.getAddress();
            String password = user.getPassword();
            String subscriptionType = user.getSubscriptionType();
            int telephoneNumber = user.getTelephoneNumber();
            int role = user.getRole().getRole();
            String query =
                    "UPDATE `users` " +
                            "SET " +
                            "`firstName`=?," +
                            "`lastName`=?," +
                            "`eMail`=?," +
                            "`address`=?," +
                            "`telephoneNumber`=?," +
                            "`password`=?, " +
                            "`subscriptionType`=?, " +
                            "`role`=?, " +
                            "WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, firstName);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setInt(4,role);
            stmt.setString(5, address);
            stmt.setInt(6, telephoneNumber);
            stmt.setString(7, subscriptionType);
            stmt.setString(8, lastName);
            stmt.setInt(9,userId);


            stmt.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    }
