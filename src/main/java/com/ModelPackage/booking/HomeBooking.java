package com.ModelPackage.booking;

import org.json.JSONException;

import java.io.IOException;

public class HomeBooking  extends Booking{
    //allows for setting the TestingSitePackage of a booking as home.
    //Also provides a qrcode and a url
    private String Qrcode;
    private String Url;

    public HomeBooking() throws IOException, InterruptedException, JSONException {
        this.generateQR();
        this.generateUrl();
    }
    public void generateQR(){
        this.Qrcode = "";
    }
    public void generateUrl(){
        this.Url = "";
    }

    public String getQrcode() {
        return this.Qrcode;
    }
    public String getUrl(){
        return this.Url;
    }
}
