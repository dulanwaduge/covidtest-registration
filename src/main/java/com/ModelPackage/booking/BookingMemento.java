package com.ModelPackage.booking;

public class BookingMemento{
    private Booking state;
    public BookingMemento(Booking state){
        this.state = state;
    }
    public Booking getState(){
        return this.state;
    }
}
