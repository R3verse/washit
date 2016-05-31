package Models;
import java.sql.*;
import java.text.SimpleDateFormat;
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
                String addr = rs.getString("address");
                int ID = rs.getInt("ID");
                Date date = rs.getDate("birthday");
                UserRoleEnum role = getRole(rs.getInt("role"));
                int tlfPhNr = rs.getInt("telephoneNumber");
                // User(String firstName, String lastName, String eMail,
                // String address, int ID, int telephoneNumber, boolean isAdmin, Date birthday)
                listReturn.add(new User(fName,
                        lName,
                        eMail,
                        addr,
                        ID,
                        tlfPhNr,
                        role,
                        date
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
                String addr = rs.getString("address");
                int ID = rs.getInt("ID");
                Date date = rs.getDate("birthday");
                UserRoleEnum role = getRole(rs.getInt("role"));
                int tlfPhNr = rs.getInt("telephoneNumber");
                userReturn = new User(fName, lName, eMail, addr, ID, tlfPhNr, role, date);
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

    public void createUser(String firstName, String lastName, String eMail, String address, int telephoneNo, int role, Date birthday, String passWord) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO users(firstName, lastName, eMail, address, telephoneNumber, role, birthday, password)" +
                    "VALUES ('" + firstName + "', '" + lastName + "', '" + eMail + "', '" + address + "', '" + telephoneNo + "', '" + role + "', '" + birthday + "', '" + passWord + "')");

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


            Date date = null;
            try{
                System.out.println(user.getBirthday().toString());
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(user.getBirthday().toString());

                date = new Date(utilDate.getTime());

                date = new java.sql.Date(utilDate.getTime());


            }catch(Exception e){
                e.printStackTrace();
            }

            int userId = user.getID();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String email = user.getEMail();
            String adress = user.getAddress();
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
                            "`role`=?, " +
                            "`birthday`=? " +
                            "WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, adress);
            stmt.setInt(5, telephoneNumber);
            stmt.setInt(6,role);
            stmt.setDate(7,date);
            stmt.setInt(8,userId);


            stmt.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    }
