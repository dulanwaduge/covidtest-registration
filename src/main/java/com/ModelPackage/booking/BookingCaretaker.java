package com.ModelPackage.booking;

import java.util.Stack;

public class BookingCaretaker {
    private Booking originator;
    private Stack<BookingMemento> prevBookings;

    void undo(){
        BookingMemento m = prevBookings.pop();
        originator.restore(m);
        
    }
    void save(){
        BookingMemento m = originator.save();
        prevBookings.push(m);
    }
}
