package com.ModelPackage.user;

import com.ModelPackage.booking.Booking;
import com.ModelPackage.TestingSitePackage.TestingSiteCollection;
import org.json.JSONException;

import java.io.IOException;
import java.util.Scanner;

public class Administerer implements UserRole {
    Booking booking = new Booking();

    public Administerer() throws JSONException, IOException, InterruptedException {
    }

    //Administerer role allows user to edit the test type of other users
    @Override
    public void roleAction(String name, String id) throws JSONException, IOException, InterruptedException {

        boolean isWrongAnswer;
        do {
            isWrongAnswer = false;
            System.out.println("**** Welcome "+ name + "******\n");
            System.out.println("\n 1. Check Booking \n 2. Search Sites to Book \n 3.Update Test Type\n");
            System.out.println("Enter selection: ");
            Scanner sc = new Scanner(System.in);
            Integer num = Integer.parseInt(sc.nextLine());

            //all cases require a booking object to proceed


            switch (num) {
                case 1:
                    //check booking
                    System.out.println("Enter Pin to check Booking: ");
                    Scanner scan = new Scanner(System.in);
                    String pin = scan.next();

                    booking.checkPin(pin,id);
                    break;
                case 2:
                    //search to book
                    TestingSiteCollection.getInstance().search();

                    System.out.println("Enter site Id to Book at TestingSitePackage: ");
                    Scanner scan2 = new Scanner(System.in);
                    String siteId = scan2.next();
                    booking.makeBooking(id,siteId);
                    break;
                case 3:
                    //Update Covid test
                    System.out.println("Enter booking Id of Customer: ");
                    Scanner scan3 = new Scanner(System.in);
                    String bookingId = scan3.next();
                    System.out.println("Do you have Symptoms? y/n");
                    String y_n = scan3.next();
                    switch (y_n){
                        case "y":

                            booking.setTestType(bookingId,"PCR");
                            break;
                        case "n":

                            booking.setTestType(bookingId,"RAT");
                            break;
                        default:
                            System.out.println("Incorrect selection");
                    }

                    break;
                default:
                    System.out.println("Enter valid selection");
                    isWrongAnswer = true;

            }
        } while (isWrongAnswer);
    }
}
