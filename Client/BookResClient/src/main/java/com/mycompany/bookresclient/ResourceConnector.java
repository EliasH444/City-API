/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bookresclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 * @author neal
 */
public class ResourceConnector {

    private final Client client = Client.create();
    private WebResource baseURI = client.resource("http://localhost:8080/webapi/myresource");

    public void addCity(City city) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ClientResponse response = baseURI.type(MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class, mapper.writeValueAsString(city));
            if (response.getStatus() != 200) {
                System.out.println("POST Failed with code: " + response.getStatus());
            }
        } catch (IOException e) {
            System.out.println("Conversion to JSON failed");
        }
    }

    public City getCityByCountry(String country) {
        WebResource webTarget = client.resource("http://localhost:8080/webapi/myresource").path(country);
        ClientResponse response = webTarget.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            System.out.println("GET Failed with code: " + response.getStatus());
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        City city = null;
        try {
            city = mapper.readValue(response.getEntity(String.class),
                    new TypeReference<City>() {
                    });
        } catch (IOException e) {
            System.out.println("City not found");
        } finally {
            return city;
        }
    }


    public ArrayList<City> getAllCities() {
        ClientResponse response = baseURI.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            System.out.println("GET Failed with code: " + response.getStatus());
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<City> cityList = null;
        try {
            cityList
                    = mapper.readValue(response.getEntity(String.class),
                            new TypeReference<ArrayList<City>>() {
                    });
        } catch (Exception e) {
            System.out.println("Cities not found");
        } finally {
            return cityList;
        }
    }
    
    /*public synchronized void deleteLocation(Book book) {
        ObjectMapper mapper = new ObjectMapper();
        try {
             WebResource baseURInew = client.resource("http://localhost:8080/webapi/myresource/" + author + "/" + title + "/" + year
");
            ClientResponse response = baseURInew.type(MediaType.APPLICATION_JSON)
                    .delete(ClientResponse.class, mapper.writeValueAsString(book));
            if (response.getStatus() != 200) {
                System.out.println("DELETE Failed with code: " + response.getStatus());
            }
        } catch (IOException e) {
            System.out.println("Conversion to JSON failed");
        }
    }*/
    public City getCityByState(String state) {
        WebResource webTarget = client.resource("http://localhost:8080/webapi/myresource/country/state").path(state);
        ClientResponse response = webTarget.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            System.out.println("GET Failed with code: " + response.getStatus());
            return null;
        }
        //return response.getEntity(Book.class);
        ObjectMapper mapper = new ObjectMapper();
        City city = null;
        try {
            city = mapper.readValue(response.getEntity(String.class),
                    new TypeReference<City>() {
            });
        } catch (IOException e) {
            System.out.println("City not found");
        } finally {
            return city;
        }
    }
    public synchronized void deleteLocation(String name,String country, String state,int year) {
    ObjectMapper mapper = new ObjectMapper();
    String url = "http://localhost:8080/webapi/myresource/" +name+"/"+ country + "/" + state + "/" + year;
    WebResource baseURI = client.resource(url);
    ClientResponse response = baseURI.type(MediaType.APPLICATION_JSON)
            .delete(ClientResponse.class);
    if (response.getStatus() != 200) {
        System.out.println("DELETE Failed with code: " + response.getStatus());
    }
}
    public List<City> getCityBeforeDate(int year) {
    String url = "http://localhost:8080/webapi/myresource";
    WebResource baseURI = client.resource(url).queryParam("before", Integer.toString(year));
    ClientResponse response = baseURI.type(MediaType.APPLICATION_JSON).put(ClientResponse.class);
    if (response.getStatus() != 200) {
        System.out.println("GET Failed with code: " + response.getStatus());
        return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    List<City> filteredCities = null;
    try {
        filteredCities = mapper.readValue(response.getEntity(String.class),
                new TypeReference<List<City>>() {});
    } catch (IOException e) {
        System.out.println("Error parsing JSON response");
    }
    return filteredCities;
}


}

