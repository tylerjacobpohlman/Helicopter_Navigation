/*
 * Name: Tyler Pohlman
 * Date: 03/07/2023
 * Class: IT2650
 * Purpose: This class is acts as a location, storing the name of the location, its respective x- and y-coordinates, and whether the location has gas.
 *
 */
package com.example.helicopter_navigation_program;

public class Location {

    /*
     * Attributes
     */
    private String location;
    private int xCoordinate;
    private int yCoordinate;
    private boolean hasGas;

    /*
     * Constructor
     */
    public Location(String location, int xCoordinate, int yCoordinate, boolean hasGas) {
        this.location = location;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.hasGas = hasGas;
    }

    /*
     * Getter and setter methods
     */
    public String getLocationName() { return location; }
    public int getXCoordinate() { return xCoordinate; }
    public int getYCoordinate() { return yCoordinate; }
    public boolean hasGas() { return hasGas; }

    //used to compare the data of two locations opposed to their memory locations
    //modification from terminal program to better display for GUI interface
    @Override
    public String toString() {
        String gasStatement;

        if(hasGas) {
            gasStatement = "This location has gas!";
        }
        else {
            gasStatement = "This location doesn't have gas!";
        }

        return "Location: " + location + '\n'
                + "X,Y Coordinate: (" + xCoordinate + ',' + yCoordinate + ")\n"
                + gasStatement;

    }
}
