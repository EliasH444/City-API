/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.bookresclient;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elias
 */
public class BookResClient {

    public static void main(String[] args) {

        ResourceConnector connector = new ResourceConnector();
        connector.addCity(new City("Ancona", "Italy", "Marche", 387));
        connector.addCity(new City("Rome", "Italy", "Lazio", -753));
        connector.addCity(new City("Athens", "Greece", "Attica", -3000));
        connector.addCity(new City("Pireaus", "Greece", "Attica", -2600));
        connector.addCity(new City("Messini", "Greece", "Messinia", 1867));
        connector.addCity(new City("Pylos", "Greece", "Messinia", 1833));
        //connector.addCity(new City("asd","Itasly ", "xs", 387 ));

        //connector.addCity(new City("Rome","Italy", "Lazio", 753 ));
        connector.deleteLocation("NeverLand", "Italy", "Marche", 387);
        ArrayList<City> cityList = connector.getAllCities();
        for (City b : cityList) {
            System.out.println(b.getName() + " " + b.getCountry() + " " + b.getState() + " " + b.getYear());
        }
        City cityLista = connector.getCityByCountry("Greece");
        System.out.println(cityLista.getName() + " " + cityLista.getCountry());
        City cities = connector.getCityByState("Lazio");
        System.out.println(cities.getName() + " " + cities.getCountry());
        


        List<City> citiess = connector.getCityBeforeDate(1);
        for (City city : citiess) {
            System.out.println(city.getName() + " " + city.getCountry() + " " + city.getState() + " " + city.getYear());
        }
       
    }
}
