package com.ModelPackage.booking;


import com.ModelPackage.api.APIManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Objects;

public class Booking{
    private String bookingId;
    private String smsPin;
    private String userGivenName;
    private String location;
    private String time;
    private Booking state;


    //method to update server with a new booking
    public Booking() throws IOException, InterruptedException, JSONException {

    }

    public void makeBooking(String userId, String siteId) throws IOException, InterruptedException, JSONException {
        APIManager booking = new APIManager();
        HttpResponse<String> response = booking.postBooking(userId, siteId);

        if (response.statusCode() == 201) {
            System.out.println("Booking Successful");
            System.out.println("Pin: " + this.getPin(response.body()));

            JSONObject bookingObj = new JSONObject(response.body());
            //set attributes
            this.setBookingInfo(bookingObj);
        } else {
            System.out.println(response.body());
        }
    }

    //Resident able to modify (TestingSitePackage and time) a booking by giving their bookingId
    public void modifyBooking(String bookingId, String userId, String siteId, String time) throws IOException, InterruptedException, JSONException {
        APIManager modBooking = new APIManager();
        HttpResponse<String> updateBookingResponse = modBooking.updateCurrentBooking(bookingId, userId, siteId, time);

        if (updateBookingResponse.statusCode() == 200) {
            System.out.println("Your Booking has been changed! \n");
            System.out.println("Pin: " + this.getPin(updateBookingResponse.body()));

            JSONObject updBookingObject = new JSONObject(updateBookingResponse.body());
            this.setUpdatedBookingInfo(updBookingObject);
        } else {
            System.out.println(updateBookingResponse.body());
        }
    }

    //Get all bookings for specific user
    public void getBookingByUserId(String userId) throws IOException, InterruptedException, JSONException {
        APIManager bookingByUserIdManager = new APIManager();
        HttpResponse<String> bookingByUserIdResponse = bookingByUserIdManager.getBookings();
        JSONArray bookingByUserIdArray = new JSONArray(bookingByUserIdResponse.body());

        //loop through all items in bookingByUserIdArray
        for (int i = 0; i < bookingByUserIdArray.length(); ++i) {
            JSONObject bookingByUserIdObject = bookingByUserIdArray.getJSONObject(i);

            String bookingUserId = bookingByUserIdObject.getJSONObject("customer").getString("id");
            String locationName = bookingByUserIdObject.getJSONObject("testingSite").getString("name");
            String bookingTime = bookingByUserIdObject.getString("startTime");

            if (Objects.equals(bookingUserId, userId)) {
                System.out.println("\nBookingID: " + bookingByUserIdObject.getString("id"));
                System.out.println("Venue: " + locationName);
                System.out.println("Time: " + bookingTime + "\n");
            }
        }
    }

    //Method to return a pin of a booking, giving the corresponding string to cast to a JSONOBJECT
    public String getPin(String response) throws JSONException {
        JSONObject booking = new JSONObject(response);
        String pin = booking.getString("smsPin");
        return pin;
    }

    //Method to set attributes of a booking object. jsonObject MUST be a booking object
    public void setBookingInfo(JSONObject bookingObj) throws JSONException {
        //set attributes
        this.bookingId = bookingObj.getString("id");
        this.smsPin = bookingObj.getString("smsPin");
        this.userGivenName = bookingObj.getJSONObject("customer").getString("givenName");
        this.location = bookingObj.getJSONObject("testingSite").getString("name");
        this.time = bookingObj.getString("startTime");
    }

    //method  to set attributes of UPDATED booking object
    public void setUpdatedBookingInfo(JSONObject updBookingObj) throws JSONException {
        this.smsPin = updBookingObj.getString("smsPin");
        this.location = updBookingObj.getJSONObject("testingSite").getString("name");
        this.time = updBookingObj.getString("startTime");
    }

    //Method to check bookingId against server and find corresponding booking
    public void checkBookingId(String bookingId, String userId) throws IOException, InterruptedException, JSONException {
        APIManager bookingObj = new APIManager();
        HttpResponse<String> response = bookingObj.getBookings();
        JSONArray bookingArray = new JSONArray(response.body());

        //loop through all bookings in jsonarray
        for (int i = 0; i < bookingArray.length(); ++i) {
            JSONObject singleBooking = bookingArray.getJSONObject(i);
            String bookingCheckId = singleBooking.getString("id");
            String bookingUser = singleBooking.getJSONObject("customer").getString("id");
            if (Objects.equals(bookingCheckId, bookingId)) {
                if (Objects.equals(bookingUser, userId)) {
                    this.setBookingInfo(singleBooking);
                    System.out.println("Booking found");
                    System.out.println("Booking for " + this.userGivenName);
                    System.out.println(" id " + this.bookingId);
                    System.out.println(" at " + this.location);
                    System.out.println(" at " + this.time);
                }
            }
        }
    }

    //Method to check pin against server and find corresponding booking
    //requires pin of booking, and the userid of whoever is looking for the booking.
    public String checkPin(String pin, String userId) throws IOException, InterruptedException, JSONException {
        APIManager bookings = new APIManager();
        HttpResponse<String> response = bookings.getBookings();
        JSONArray bookingsObject = new JSONArray(response.body());
        //loop through all bookings in jsonarray
        for (int i = 0; i < bookingsObject.length(); ++i) {
            JSONObject singleBooking = bookingsObject.getJSONObject(i);
            String bookingPin = singleBooking.getString("smsPin");
            String bookingUser = singleBooking.getJSONObject("customer").getString("id");
            if (Objects.equals(bookingPin, pin)) {
                if (Objects.equals(bookingUser, userId)) {
                    this.setBookingInfo(singleBooking);
                    System.out.println("Booking found");
                    System.out.println("Booking for " + this.userGivenName);
                    System.out.println(" id " + this.bookingId);
                    System.out.println(" at " + this.location);
                    System.out.println(" at " + this.time);

                    return "<html>Booking found<br/>Booking for " + this.userGivenName +"<br/>"+
                            " id " + this.bookingId + " at " + this.location + " at " + this.time+ "<html>";
                }
            }
        }
        return "booking not found";
    }
    public void adminBookingCheck(String bookingId, String smsPin) throws IOException, InterruptedException, JSONException {
        APIManager manager = new APIManager();
        HttpResponse<String> response = manager.getBookingCovidTest(bookingId);
        JSONObject covidTestObject = new JSONObject(response.body());

        String bookingIdString = covidTestObject.getString("id");
        String smsPinString = covidTestObject.getString("smsPin");

        LocalDateTime currentDateTime = LocalDateTime.now();
        String bookingDateTime = covidTestObject.getString("startTime");

        if(Objects.equals(bookingIdString, bookingId)){
            if (Objects.equals(smsPinString, smsPin)){
                System.out.println("\nBooking found for: " + covidTestObject.getJSONObject("customer").getString("givenName"));
                System.out.println("BookingID: " + bookingIdString + "\n");
            }
        }
    }
    //when given a booking id, it will get the covid test of that booking,
    //and allow you to set its type
    public void setTestType(String bookingId, String testType) throws IOException, InterruptedException, JSONException {
        APIManager covidTests = new APIManager();
        HttpResponse<String> response = covidTests.getBookingCovidTest(bookingId);

        //process JSON object
        System.out.println(response.body());
        JSONObject bookingCovidTest = new JSONObject(response.body());
        JSONArray covidTest = bookingCovidTest.getJSONArray("covidTests");
        JSONObject testDetails = covidTest.getJSONObject(0);
        String covidId = testDetails.getString("id");
        System.out.println(covidId);

        String type = testType;
        //update the pulled covidtest
        covidTests.updateCovidTest(covidId, type);
        //could use a builder pattern here to make this look nicer
    }

    //delete a booking using bookingId
    public void deleteBooking(String userId, String bookingId) throws IOException, InterruptedException {
        APIManager deleteBookingManager = new APIManager();
        HttpResponse<String> response = deleteBookingManager.deleteBooking(bookingId);

        if (response.statusCode() == 204) {
            System.out.println("Booking successfully deleted!");
        } else {
            System.out.println(response.body());
        }
    }
    public BookingMemento save(){
        return null;
    }
    public void restore(BookingMemento m){

    }

}
