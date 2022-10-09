package com.ModelPackage.user;

import com.ModelPackage.booking.Booking;
import com.ModelPackage.booking.HomeBooking;
import com.ModelPackage.TestingSitePackage.TestingSiteCollection;
import org.json.JSONException;

import java.io.IOException;
import java.util.Scanner;

public class Customer implements UserRole {
    //The customer role allows the user to book and search and check bookings
    public Booking booking = new Booking();
    public String name;
    public String id;

    public Customer() throws JSONException, IOException, InterruptedException {
    }

    @Override
    public void roleAction(String name, String id) throws JSONException, IOException, InterruptedException {
        this.name = name;
        this.id = id;

        boolean isWrongAnswer;
        do {
            isWrongAnswer = false;
            System.out.println("**** Welcome " + name + " ******\n");
            System.out.println("\n 1. Check Booking \n 2. Search Sites to Book \n 3. Profile \n");
            System.out.println("Enter selection: ");
            Scanner sc = new Scanner(System.in);
            Integer num = Integer.parseInt(sc.nextLine());

            //both cases require a booking object to proceed


            switch (num) {
                case 1:
                    //check booking
                    System.out.println("Enter Pin to check Booking: ");
                    System.out.println("Test");
                    Scanner scan = new Scanner(System.in);
                    String pin = scan.next();

                    booking.checkPin(pin, id);
                    break;
                case 2:
                    //search to book
                    TestingSiteCollection.getInstance().search();

                    System.out.println("Enter site Id to Book at TestingSitePackage: ");
                    Scanner scan2 = new Scanner(System.in);
                    String siteId = scan2.next();

                    System.out.println("1:On Site Test\n 2:Home Test\n ");
                    Integer loc = scan2.nextInt();
                    if (loc == 1) {
                        booking.makeBooking(id, siteId);
                    } else if (loc == 2) {
                        HomeBooking homeBooking = new HomeBooking();
                        homeBooking.makeBooking(id, "Home");
                    } else {
                        System.out.println("Incorrect Selection");
                    }
                    break;
                case 3:
                    System.out.println("1. Current Active Bookings \n2. Search using BookingID \n");
                    System.out.println("Enter your selection: ");

                    Scanner scanner = new Scanner(System.in);
                    int selection = scanner.nextInt();

                    if (selection == 1) {
                        //get all bookings made by user{id}
                        booking.getBookingByUserId(id);
                        System.out.println("\n1. Change booking venue and time \n2. Cancel booking ");
                        System.out.println("Enter your selection: ");

                        int modifyBookingSelection = scanner.nextInt();

                        System.out.println("Enter bookingId to modify booking: ");
                        String bookingId = scanner.next();

                        if (modifyBookingSelection == 1) {
                            TestingSiteCollection.getInstance().search();

                            System.out.println("Enter site Id to Book at TestingSitePackage: \n");
                            String updSiteId = scanner.next();

                            System.out.println("Enter the time you want the appointment to be: ");
                            String updStartTime = scanner.next();

                            booking.modifyBooking(bookingId, id, updSiteId, updStartTime);
                        } else if (modifyBookingSelection == 2) {
                            booking.deleteBooking(id, bookingId);
                        } else {
                            System.out.println("Incorrect Selection");
                        }
                    }

                    else if (selection == 2) {
                        System.out.println("Enter bookingID: ");
                        String bookingId = scanner.next();
                        //validate bookingId and display booking details
                        booking.checkBookingId(bookingId, id);

                        System.out.println("\n1. Change booking venue and time \n2. Cancel booking ");
                        System.out.println("Enter your selection: ");

                        int modifyBookingSelection = scanner.nextInt();

                        if (modifyBookingSelection == 1) {
                            TestingSiteCollection.getInstance().search();

                            System.out.println("Enter site Id to Book at TestingSitePackage: \n");
                            String updSiteId = scanner.next();

                            System.out.println("Enter the time you want the appointment to be: ");
                            String updStartTime = scanner.next();

                            booking.modifyBooking(bookingId, id, updSiteId, updStartTime);
                        } else if (modifyBookingSelection == 2) {
                            booking.deleteBooking(id, bookingId);
                        } else {
                            System.out.println("Incorrect Selection");
                        }
                    }
                    break;
                default:
                    System.out.println("Enter valid selection");
                    isWrongAnswer = true;
            }
        } while (isWrongAnswer);
    }
    /*
    public String menu1Action(String num){
            int val = Integer.parseInt(num);
            switch (val){
                case 1:
                    return "Enter Pin to check Booking: ";

                case 2:

                case 3:
            }

    }
    */
}
