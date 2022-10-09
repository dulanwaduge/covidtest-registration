package com.ModelPackage.user;

import com.ModelPackage.booking.Booking;
import com.ModelPackage.TestingSitePackage.TestingSiteCollection;
import org.json.JSONException;

import java.io.IOException;
import java.util.Scanner;

public class Administrator implements UserRole{
    Booking booking = new Booking();

    public Administrator() throws JSONException, IOException, InterruptedException {
    }

    //The administrator role allows the user to book on behalf of other people aswell as search for their booking
    @Override
    public void roleAction(String name, String id) throws JSONException, IOException, InterruptedException {
        boolean isWrongAnswer;
        do {
            isWrongAnswer = false;
            System.out.println("**** Welcome "+ name + " ******\n");
            System.out.println("\n 1. Check Booking \n 2. New Booking \n 3. Modify Existing Booking (Phone Call)\n");
            System.out.println("Enter your selection: ");
            Scanner sc = new Scanner(System.in);
            Integer num = Integer.parseInt(sc.nextLine());

            //both cases require a booking object to proceed


            switch (num) {
                case 1:
                    //check booking
                    System.out.println("Enter Pin to check your Booking: ");
                    Scanner scan = new Scanner(System.in);
                    String pin = scan.next();
                    System.out.println("Enter CustomerId to check Booking: ");
                    String customerId = scan.next();
                    booking.checkPin(pin,customerId);
                    break;
                case 2:
                    //search to book
                    TestingSiteCollection.getInstance().search();

                    System.out.println("Enter site Id to Book at TestingSitePackage: ");
                    Scanner scan2 = new Scanner(System.in);
                    String siteId = scan2.next();
                    System.out.println("Enter Customer ID to Book at TestingSitePackage: ");
                    String customerId2 = scan2.next();
                    booking.makeBooking(customerId2,siteId);
                    break;
                case 3:
                    //booking modification through phone call

                    //get booking details by verifying bookingId and smsPin
                    Scanner phoneScanner = new Scanner(System.in);

                    System.out.println("\nPlease provide bookingID: ");
                    String inputBookingId = phoneScanner.next();

                    System.out.println("\nPlease provide your pin: ");
                    String inputSmsPin = phoneScanner.next();

                    booking.adminBookingCheck(inputBookingId, inputSmsPin);

                    //Modify the booking
                    System.out.println("\n1. Change booking venue and time");
                    System.out.println("2. Cancel booking");

                    int adminSelection = phoneScanner.nextInt();

                    if (adminSelection == 1){
                        TestingSiteCollection.getInstance().search();

                        System.out.println("Enter site Id to Book at TestingSitePackage: \n");
                        String updSiteId = phoneScanner.next();

                        System.out.println("Enter the time you want the appointment to be: \n");
                        String updStartTime = phoneScanner.next();

                        System.out.println("Enter customerId to verify: ");
                        String updCustomerId = phoneScanner.next();

                        booking.modifyBooking(inputBookingId, updCustomerId, updSiteId, updStartTime);
                    } else if (adminSelection == 2) {
                        booking.deleteBooking(id, inputBookingId);
                    } else {
                        System.out.println("Incorrect Selection");
                    }

                    break;
                default:
                    System.out.println("Enter valid selection");
                    isWrongAnswer = true;
            }
        } while (isWrongAnswer);
    }
}
