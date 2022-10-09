package com.ModelPackage.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;


public class APIManager {
    //this is controller
    //Private API Key taken from system env variables.
    //private rooturl which all the http requests start with
    private String APIKey = System.getenv("myApiKey");
    private final String rootUrl = "https://fit3077.com/api/v2";

    //method to log the user in to the system, given a username and password
    //returns the response body if successful, status code if not successful
    public HttpResponse<String> userLogin(String username, String password) throws IOException, InterruptedException {
        String userLoginUrl = rootUrl + "/user" + "/login";

        String jsonString = "{" +
                "\"userName\":\"" + username + "\"," +
                "\"password\":\"" + password + "\"" +
                "}";
        //build the request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(userLoginUrl))
                .setHeader("Authorization", APIKey)
                .header("Content-Type", "application/json") // This header needs to be set when sending a JSON request body.
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    //method to get all users on server
    public HttpResponse<String> getUsers() throws IOException, InterruptedException {
        String userUrl = rootUrl + "/user";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(userUrl))
                .setHeader("Authorization", APIKey)
                .header("accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;

    }

    //Method to get all sites on server
    public HttpResponse<String> getSites() throws IOException, InterruptedException {
        String sitesUrl = rootUrl + "/testing-site";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(sitesUrl))
                .setHeader("Authorization", APIKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    //method to add booking to a user on server
    public HttpResponse<String> postBooking(String userId, String siteId) throws IOException, InterruptedException {
        LocalDateTime date = LocalDateTime.now();
        String bookingUrl = rootUrl + "/booking";
        String bookingString = "{\"customerId\":" + "\"" + userId + "\"" + ", \"testingSiteId\":" +
                "\"" + siteId + "\"" + ",\"startTime\":" + "\"" + date + "\"" + " ,\"notes\": \"string\",\"additionalInfo\": {}}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(bookingUrl))
                .setHeader("accept", "application/json")
                .header("Authorization", APIKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(bookingString))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> updateCurrentBooking(String bookingId, String userId, String siteId, String startTime) throws IOException, InterruptedException {
        String updateCurrentBookingUrl = rootUrl + "/booking/" + bookingId;
        String updateJsonPayloadString =
                "{\"customerId\":" + "\"" + userId + "\"" + ", \"testingSiteId\":" +
                        "\"" + siteId + "\"" + ",\"startTime\":" + "\"" + startTime + "\"" + " ,\"notes\": \"string\",\"additionalInfo\": {}}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(updateCurrentBookingUrl))
                .setHeader("accept", "application/json")
                .header("Authorization", APIKey)
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(updateJsonPayloadString))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    //gets all bookings
    public HttpResponse<String> getBookings() throws IOException, InterruptedException {
        String bookingUrl = rootUrl + "/booking/";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(bookingUrl))
                .setHeader("accept", "application/json")
                .header("Authorization", APIKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    //get covid tests of a given booking id
    public HttpResponse<String> getBookingCovidTest(String bookingId) throws IOException, InterruptedException {
        String bookingCovidTestUrl = rootUrl + "/booking/" + bookingId +
                "?fields=covidTests";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(bookingCovidTestUrl))
                .setHeader("accept", "application/json")
                .header("Authorization", APIKey)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
    //delete a booking given a bookingId
    public HttpResponse<String> deleteBooking(String bookingId) throws IOException, InterruptedException {
        String deleteUrl = rootUrl + "/booking/" + bookingId;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(deleteUrl))
                .setHeader("accept", "application/json")
                .header("Authorization", APIKey)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public HttpResponse<String> updateCovidTest(String covidId, String type) throws IOException, InterruptedException {
        String updateCovidTestUrl = rootUrl + "/covid-test/" + covidId;
        String updateString = "{\"type\":" + "\"" + type + "\"}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(updateCovidTestUrl))
                .setHeader("accept", "application/json")
                .header("Authorization", APIKey)
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(updateString))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
    //update a given covid test
    //this will handle our http requests.
    //facade/
    //handles all types of calls
}

