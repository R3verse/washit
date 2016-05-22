package Models;

/**
 * Created by B on 24-02-2016.
 */
public class SessionModel
{
    private User loggedInUser;
    private boolean isGuest;
    private static  SessionModel sessionUserModel = null;

    private SessionModel()
    {
        this.loggedInUser = null;
        isGuest = isGuest();
    }

    public static SessionModel getInstance()
    {
        if(sessionUserModel == null)
        {
            sessionUserModel = new SessionModel();
        }
        return sessionUserModel;
    }

    public void setUserSession(User usr)
    {
        System.out.println("New user logged in: "+ usr);
        loggedInUser = usr;
    }

    public User getLoggedInUser()
    {

        return loggedInUser;
    }

    public boolean isGuest()
    {
        if(getLoggedInUser() != null && getLoggedInUser().getRole() == UserRoleEnum.ADMIN )
        {
            isGuest = false;
            System.out.println("false");
        }
        else
        {
            isGuest = true;
            System.out.println("Guest is using system!");
        }
        return isGuest;
    }
    public boolean getIsGuest()
    {
        return isGuest;
    }
}
