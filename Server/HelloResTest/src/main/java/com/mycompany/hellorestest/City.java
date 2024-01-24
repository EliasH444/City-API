
package com.mycompany.hellorestest;

public class City {

    private String country;
    private String state;
    private int year;
    private String name;

    public City(String name,String country, String state, int year) {
        this.country = country;
        this.state = state;
        this.year = year;
        this.name = name;
    }

    public City() {

    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        /*if (year < 1) {
            this.year = 1;
        } else {
            this.year = year;
        }*/this.year = year;
    }

    public boolean equals(City book) {
        return this.country.equals(book.getCountry())
                && this.state.equals(book.getState())
                && this.year == book.getYear();
    }

}
