package Controllers;

import Models.Booking;
import Models.BookingModel;

/**
 * Created by AlexanderFalk on 29/02/2016.
 */
public class BookingEditController
{
    public static void tryUpdate    (Booking booking)
    {
        BookingModel.getInstance().updateBooking(booking);
    }
}

