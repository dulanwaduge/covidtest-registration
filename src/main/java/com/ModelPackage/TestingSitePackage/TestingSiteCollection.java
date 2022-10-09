package com.ModelPackage.TestingSitePackage;

import com.ModelPackage.api.APIManager;
import com.ModelPackage.search.search;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class TestingSiteCollection implements search {
    private static TestingSiteCollection instance;
    private APIManager locations;
    private HttpResponse<String> response;
    private TestingSite[] SiteCollection;

    static {
        try {
            instance = new TestingSiteCollection();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private TestingSiteCollection() throws IOException, InterruptedException {
        //private constructor for singleton
        locations = new APIManager();
        response = locations.getSites();
    }

    public void UpdateSiteCollection() throws IOException, InterruptedException {
        //update the local collection to match server collection
        response = locations.getSites();
    }

    public static TestingSiteCollection getInstance() throws IOException, InterruptedException {
        if (instance==null){
            instance = new TestingSiteCollection();
        }
        return instance;
    }

    //FIND A WAY TO ABSTRACT AWAY ALL SEARCH FUNCTIONALITY INTO ITS OWN SET OF CLASSES/INTERFACES.
    public String search() throws JSONException, IOException, InterruptedException {

        //*************************** Search Main Menu BEGIN **********************
        System.out.println("********** Search Testing Sites **********\n");
        System.out.println("Please select a Search option");
        System.out.println("1. Search by Suburb");
        System.out.println("2. Search by Facility type");

        Scanner searchSelection = new Scanner(System.in);

        boolean isWrongAnswer;
        do {
            isWrongAnswer = false;
            System.out.println("\nMake a selection: ");
            switch (searchSelection.nextInt()) {
                case 1: {
                    //Search Suburb
                    Scanner suburbInput = new Scanner(System.in);
                    JSONArray suburbObject = new JSONArray(response.body());

                    JSONObject address;
                    JSONObject obj;
                    System.out.println("Enter name of Suburb: ");
                    String selection = suburbInput.next();

                    for (int i = 0; i < suburbObject.length(); i++) {
                        obj = suburbObject.getJSONObject(i);
                        address = obj.getJSONObject("address");
                        String addressSuburb = address.getString("suburb");

                        if (addressSuburb.contains(selection)) {
                            System.out.println("\n******** Testing sites near " + selection + " ********\n");
                            System.out.println("site ID: " + obj.get("id"));
                            System.out.println(obj.getString("name"));
                            System.out.println(obj.getString("phoneNumber"));
                            System.out.println(address.getString("unitNumber") + " " + address.getString("street") + " " + address.getString("suburb") +
                                    " " + address.getString("state") + " " + address.getString("postcode"));
                        }
                    }
                    break;
                }
                case 2: {
                    //Search Types - Drive Through, Walk-in, Clinics, GPs or Hospitals

                    JSONArray typeObject = new JSONArray(response.body());
                    String type = null;
                    JSONObject obj = null;
                    JSONObject additionalInfo = null;

                    System.out.println("\n 1. Drive Through \n 2. Walk-in \n 3. Clinic " +
                            "\n 4. GP \n 5. Hospital");

                    boolean isWrongAnswer2;
                    do {
                        isWrongAnswer2 = false;
                        System.out.println("Enter type of facility: \n");
                        Scanner typeInput = new Scanner(System.in);
                        Integer num = typeInput.nextInt();
                        switch (num) {
                            case 1: {
                                for (int i = 0; i < typeObject.length(); i++) {
                                    obj = typeObject.getJSONObject(i);
                                    type = obj.getString("name");
                                    if (type.contains("Drive")) {
                                        System.out.println("site ID: " + obj.get("id"));
                                        System.out.println(obj.getString("name"));
                                        System.out.println(obj.getString("phoneNumber"));
                                    }
                                }
                                break;
                            }
                            case 2: {
                                for (int i = 0; i < typeObject.length(); i++) {
                                    obj = typeObject.getJSONObject(i);
                                    additionalInfo = obj.getJSONObject("additionalInfo");
                                    String typeFromAdditionalInfo = additionalInfo.getString("type");
                                    if (typeFromAdditionalInfo.contains("Walk")) {
                                        System.out.println("site ID: " + obj.get("id"));
                                        System.out.println(obj.getString("name"));
                                        System.out.println(obj.getString("phoneNumber"));
                                    }
                                }
                                break;
                            }
                            case 3: {
                                for (int i = 0; i < typeObject.length(); i++) {
                                    obj = typeObject.getJSONObject(i);
                                    type = obj.getString("name");
                                    if (type.contains("Clinic")) {
                                        System.out.println("site ID: " + obj.get("id"));
                                        System.out.println(obj.getString("name"));
                                        System.out.println(obj.getString("phoneNumber"));
                                    }
                                }
                                break;
                            }
                            case 4: {
                                for (int i = 0; i < typeObject.length(); i++) {
                                    obj = typeObject.getJSONObject(i);
                                    type = obj.getString("name");
                                    if (type.contains("GP")) {
                                        System.out.println("site ID: " + obj.get("id"));
                                        System.out.println(obj.getString("name"));
                                        System.out.println(obj.getString("phoneNumber"));
                                    }
                                }
                                break;
                            }
                            case 5: {
                                for (int i = 0; i < typeObject.length(); i++) {
                                    obj = typeObject.getJSONObject(i);
                                    type = obj.getString("name");
                                    if (type.contains("Hospital")) {
                                        System.out.println("site ID: " + obj.get("id"));
                                        System.out.println(obj.getString("name"));
                                        System.out.println(obj.getString("phoneNumber"));
                                    }
                                }
                                break;
                            }
                            default: {
                                System.out.println("Choose valid input number");
                                isWrongAnswer2 = true;
                            }
                        }
                    } while (isWrongAnswer2);
                    break;
                }
                default: {
                    System.out.println("Choose 1 or 2");
                    isWrongAnswer = true;
                }
            }
        } while (isWrongAnswer);
            return "No testing site found.";
    }
}
//mov
