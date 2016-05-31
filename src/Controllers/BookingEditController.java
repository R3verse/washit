package Controllers;

import Models.Booking;
import Models.BookingModel;

/**
 * Created by x on 19/05/2016.
 */
public class BookingEditController
{
    public static void tryUpdate    (Booking booking)
    {
        BookingModel.getInstance().updateBooking(booking);
    }
}

