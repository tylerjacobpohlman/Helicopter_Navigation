/*
 * Name: Tyler Pohlman
 * Date: 03/07/2023
 * Class: IT2650
 * Purpose: This class acts as a helicopter, which has a given location, fuel amount, max fuel amount, and miles per gallon.
 *
 */
package com.example.helicopter_navigation_program;

public class Helicopter {
    /*
     * Class attributes
     */
    private Location currLocation;
    private double currFuel;
    private double maxFuelCapacity;
    private double milesPerGallon;

    /*
     * Constructor
     */
    public Helicopter(Location currLocation, double currFuel, double maxFuelCapacity, double milesPerGallon) {
        this.currLocation = currLocation;
        this.currFuel = currFuel;
        this.maxFuelCapacity = maxFuelCapacity;
        this.milesPerGallon = milesPerGallon;
    }

    /*
     * Getter and Setter methods
     */
    public Location getCurrLocation() { return currLocation; }
    public void setCurrLocation(Location currLocation) { this.currLocation = currLocation;}
    public double getCurrFuel() { return currFuel; }
    public void setCurrFuel(double currFuel) { this.currFuel = currFuel; }
    public double getMaxFuelCapacity() { return maxFuelCapacity; }
    public void setMaxFuelCapacity(double maxFuelCapacity) { this.maxFuelCapacity = maxFuelCapacity; }
    public double getMilesPerGallon() { return milesPerGallon; }
    public void setMilesPerGallon(double milesPerGallon) { this.milesPerGallon = milesPerGallon; }

    //logic for flying to another location is computed and method returns true if the helicopter is able to fly
    //otherwise, method doesn't change the attributes of the class and returns false
    public boolean flyTo(Location destination) {
        //checks if the Helicopter has enough fuel and, if so, subtracts the spent fuel from the currFuel
        //returns true if able to fly
        if( ableToFly(destination) ) {
            double fuelCapacity = currFuel;
            fuelCapacity = fuelCapacity - (distanceToNextLocation(destination) / milesPerGallon);
            currFuel = fuelCapacity;
            currLocation = destination;
            return true;
        }
        //otherwise, don't do anything and return false
        return false;
    }

    //restructuring of the original method, includes logic in helicopter class instead of main method
    //returns boolean to indicate whether the helicopter successfully refiled
    public boolean refuel() {
        //returns true if the current location has gas and the current fuel capacity isn't equal to the max fuel capacity
        if( currLocation.hasGas() && (currFuel != maxFuelCapacity) ) {
            currFuel = maxFuelCapacity;
            return true;
        }
        else {
            return false;
        }
    }

    /*
     * Private methods only available within the class itself
     */
    private double distanceToNextLocation(Location destination) {

        //uses Math class and distance formula to determine the total distance
        double distance = 0;
        double xPart = destination.getXCoordinate() - currLocation.getXCoordinate();
        double yPart = destination.getYCoordinate() - currLocation.getYCoordinate();
        distance = Math.sqrt( Math.pow(xPart, 2) + Math.pow(yPart, 2) );
        return distance;
    }
    private boolean ableToFly(Location destination) {
        return ( ( currFuel * milesPerGallon ) >= ( distanceToNextLocation(destination) ) );
    }

}
