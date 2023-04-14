/*
 * Name: Tyler Pohlman
 * Date: 03/07/2023
 * Class: IT2650
 * Purpose: The "main" for the JavaFX GUI program.
 *
 */
package com.example.helicopter_navigation_program;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class HelicopterNavigationApplication extends Application {
    //created as a property of the entire class, fixing issue within lambda expression
    Helicopter givenHeli;

    //this method is unlike a main method in the traditional sense where lines of code are executed only once
    //I'm not entirely sure what's going on with the backend of the method, but loops seem pointless here
    @Override
    public void start(Stage stage) throws IOException {

        //creates a new AnchorPane, which positions items using coordinates
        AnchorPane givenPane = new AnchorPane();

        //creates a new scene using the pane
        Scene givenScene = new Scene(givenPane, 800, 600);

        /*
         * ITEMS USED THROUGHOUT ALL SCENES
         * moved here so any file exceptions can be displayed on the GUI
         */
        //errorLabel, position and addition to pane
        Label errorLabel = new Label("");
        AnchorPane.setLeftAnchor(errorLabel, 500.0);
        AnchorPane.setTopAnchor(errorLabel, 500.0);
        givenPane.getChildren().add(errorLabel);
        //userFeedbackLabel, position, and addition to the pane
        Label userFeedbackLabel = new Label("");
        AnchorPane.setLeftAnchor(userFeedbackLabel, 500.0);
        AnchorPane.setTopAnchor(userFeedbackLabel, 500.0);
        givenPane.getChildren().add(userFeedbackLabel);
        //creates and sets the position for the list view of locations
        ListView<Location> givenLocations = new ListView<>();
        AnchorPane.setLeftAnchor(givenLocations, 20.0);
        AnchorPane.setTopAnchor(givenLocations, 80.0);

        /*
         * FILE READING
         */
        try {
            // creates the three objects in order to open the file, read from the file, and store it results
            //in the BufferedReader
            File givenFile = new File("locations.csv");
            FileReader fr = new FileReader(givenFile);
            BufferedReader br = new BufferedReader(fr);
            //used to store the given line of the file
            //the first line is automatically stored in the file
            String line = br.readLine();
            //chose instead of do-while because file may be empty, so trying to parse null causes an error
            while (line != null) {
                //creates an array, storing each respective attribute as defined in the file
                String[] locationAttributes = line.split("%");
                //used to store the separate components of the array
                String location;
                int xCoordinate;
                int yCoordinate;
                boolean hasGas;
                //sets the variables to the respective components of the line
                location = locationAttributes[0];
                //the limitation with parsing is that blank spaces between the delimiters will cause errors
                xCoordinate = Integer.parseInt(locationAttributes[1]);
                yCoordinate = Integer.parseInt(locationAttributes[2]);
                hasGas = Boolean.parseBoolean(locationAttributes[3]);
                //adds the location the arraylist
                givenLocations.getItems().add(new Location(location, xCoordinate, yCoordinate, hasGas));
                //increments to the next line
                line = br.readLine();
            }
            //closes the buffered reader and file reader
            br.close();
            fr.close();
            //if no file with the given name "locations.csv"
        } catch (FileNotFoundException e) {
            errorLabel.setText("Error: Cannot find 'locations.csv'!\nMake sure the file is named correctly or " +
                    "present in the given directory.");
        }
        // if there's a problem when trying to read the file
        catch (IOException f) {
            errorLabel.setText("Error: Cannot read file!\nCheck the integrity of 'locations.csv' or make another " +
                    "'locations.csv' file.");
        }

        /*
         * ITEMS USED FOR INTRODUCTION SCENE
         */
        //introductionLabel and position
        Label introductionLabel = new
                Label("Welcome to the helicopter navigation system! Please enter the MPG and maximum fuel " +
                "capacity into their respective fields.\nClick DONE when done.");
        AnchorPane.setLeftAnchor(introductionLabel, 40.0);
        AnchorPane.setTopAnchor(introductionLabel, 20.0);
        //maxFuelCapacityLabel and position
        Label maxFuelCapacityLabel = new Label("Enter the maximum fuel capacity of the helicopter below:");
        AnchorPane.setLeftAnchor(maxFuelCapacityLabel, 20.0);
        AnchorPane.setTopAnchor(maxFuelCapacityLabel, 60.0);
        //maxFuelCapacityField and position
        TextField maxFuelCapacityField = new TextField();
        AnchorPane.setLeftAnchor(maxFuelCapacityField, 20.0);
        AnchorPane.setTopAnchor(maxFuelCapacityField, 80.0);
        //milesPerGallonLabel and position
        Label milesPerGallonLabel = new Label("Enter the miles per gallon (MPG) of the helicopter:");
        AnchorPane.setLeftAnchor(milesPerGallonLabel, 20.0);
        AnchorPane.setTopAnchor(milesPerGallonLabel, 120.0);
        //milesPerGallonField and position
        TextField milesPerGallonField = new TextField();
        AnchorPane.setLeftAnchor(milesPerGallonField, 20.0);
        AnchorPane.setTopAnchor(milesPerGallonField, 140.0);
        //setupButton and position
        Button setupButton = new Button("DONE");
        AnchorPane.setLeftAnchor(setupButton, 20.0);
        AnchorPane.setTopAnchor(setupButton, 180.0);

        /*
         * ITEMS USED FOR MAIN MENU SCENE
         */
        //currLocLabel and position
        Label currLocLabel = new Label("");
        AnchorPane.setLeftAnchor(currLocLabel, 20.0);
        AnchorPane.setTopAnchor(currLocLabel, 0.0);
        //currFuelLabel and position
        Label currFuelLabel = new Label("");
        AnchorPane.setLeftAnchor(currFuelLabel, 20.0);
        AnchorPane.setTopAnchor(currFuelLabel, 20.0);
        //mainMenuLabel and position
        Label mainMenuLabel = new Label("MAIN MENU:\nPlease select from the following options and click " +
                "ENTER when done...");
        AnchorPane.setLeftAnchor(mainMenuLabel, 20.0);
        AnchorPane.setTopAnchor(mainMenuLabel, 80.0);
        //menuOptionsList and position
        ListView<String> menuOptionsList = new ListView<>();
        menuOptionsList.getItems().add("Choose a destination");
        menuOptionsList.getItems().add("Refuel");
        menuOptionsList.getItems().add("Add a destination");
        menuOptionsList.getItems().add("Remove a destination");
        AnchorPane.setLeftAnchor(menuOptionsList, 20.0);
        AnchorPane.setTopAnchor(menuOptionsList, 120.0);
        //mainMenuButton and position
        Button mainMenuButton = new Button("ENTER");
        AnchorPane.setLeftAnchor(mainMenuButton, 20.0);
        AnchorPane.setBottomAnchor(mainMenuButton, 40.0);

        /*
         * ITEMS USED FOR DESTINATIONS MENU
         */
        //destinationMenuLabel and position
        Label destinationMenuLabel = new Label("DESTINATIONS MENU:\n" +
                "Please select from the following destinations...");
        AnchorPane.setLeftAnchor(destinationMenuLabel, 20.0);
        AnchorPane.setTopAnchor(destinationMenuLabel, 40.0);
        //goBackButton and position
        Button goBackButton = new Button("GO BACK");
        AnchorPane.setLeftAnchor(goBackButton, 20.0);
        AnchorPane.setBottomAnchor(goBackButton, 80.0);
        //destinationMenuButton and position
        Button destinationMenuButton = new Button("ENTER");
        AnchorPane.setLeftAnchor(destinationMenuButton, 100.0);
        AnchorPane.setBottomAnchor(destinationMenuButton, 80.0);

        /*
         * ITEMS USED FOR REMOVE DESTINATION MENU
         */
        Label removeDestinationMenuLabel = new Label("REMOVE DESTINATIONS MENU:" +
                "\nPlease select which of the following destinations to remove...");
        AnchorPane.setLeftAnchor(removeDestinationMenuLabel, 20.0);
        AnchorPane.setTopAnchor(removeDestinationMenuLabel, 40.0);
        //goBackButton and position
        Button removeDestionGoBackButton = new Button("GO BACK");
        AnchorPane.setLeftAnchor(removeDestionGoBackButton, 20.0);
        AnchorPane.setBottomAnchor(removeDestionGoBackButton, 80.0);
        //destinationMenuButton and position
        Button removeDestinationMenuButton = new Button("ENTER");
        AnchorPane.setLeftAnchor(removeDestinationMenuButton, 100.0);
        AnchorPane.setBottomAnchor(removeDestinationMenuButton, 80.0);

        /*
         * ITEMS USED FOR ADD DESTINATION MENU
         */
        Label addDestinationMenuLabel = new Label("ADD DESTINATIONS MENU:" +
                "\nPlease enter the location name, x-y coordinates, and whether the location has gas.");
        AnchorPane.setLeftAnchor(addDestinationMenuLabel, 20.0);
        AnchorPane.setTopAnchor(addDestinationMenuLabel, 40.0);
        //addDestionGoBackButton and position
        Button addDestionGoBackButton = new Button("GO BACK");
        AnchorPane.setLeftAnchor(addDestionGoBackButton, 20.0);
        AnchorPane.setBottomAnchor(addDestionGoBackButton, 20.0);
        //addDestinationMenuButton and position
        Button addDestinationMenuButton = new Button("ENTER");
        AnchorPane.setLeftAnchor(addDestinationMenuButton, 100.0);
        AnchorPane.setBottomAnchor(addDestinationMenuButton, 20.0);
        //addLocationLabel and position
        Label addLocationLabel = new Label("Enter the name of the location:");
        AnchorPane.setLeftAnchor(addLocationLabel, 20.0);
        AnchorPane.setTopAnchor(addLocationLabel, 80.0);
        //addLocationField and position
        TextField addLocationField = new TextField();
        AnchorPane.setLeftAnchor(addLocationField, 20.0);
        AnchorPane.setTopAnchor(addLocationField, 100.0);
        //addXYCoordinateLabel and position
        Label addXYCoordinateLabel = new Label("Enter the x,y coordinates (x,y) as two integer " +
                "separated by a comma:");
        AnchorPane.setLeftAnchor(addXYCoordinateLabel, 20.0);
        AnchorPane.setTopAnchor(addXYCoordinateLabel, 140.0);
        //addXYCoordinateField and position
        TextField addXYCoordinateField = new TextField();
        AnchorPane.setLeftAnchor(addXYCoordinateField, 20.0);
        AnchorPane.setTopAnchor(addXYCoordinateField, 160.0);
        Label addGasLabel = new Label("Check the box if the destination has gas. Leave the box unchecked if not.");
        AnchorPane.setLeftAnchor(addGasLabel, 20.0);
        AnchorPane.setTopAnchor(addGasLabel, 200.0);
        //addXYCoordinateField and position
        CheckBox addGasBox = new CheckBox();
        AnchorPane.setLeftAnchor(addGasBox, 20.0);
        AnchorPane.setTopAnchor(addGasBox, 220.0);

        /*
         * INTRODUCTION SCENE
         */
        //all the items for the introduction scene are added
        givenPane.getChildren().addAll(introductionLabel, maxFuelCapacityLabel, maxFuelCapacityField,
                milesPerGallonLabel, milesPerGallonField, setupButton);
        //when the "ENTER" button is pressed, the following code is executed
        setupButton.setOnAction(actionEvent -> {
            //tries grabbing the information from the two text fields as doubles
            try {
                double maxFuel = Double.parseDouble(maxFuelCapacityField.getText());
                double milesPerGallon = Double.parseDouble(milesPerGallonField.getText());
                //the error label is set blank
                errorLabel.setText("");
                //a new helicopter object is created using the given doubles, and the first location object
                // as the current location
                givenHeli = new Helicopter(givenLocations.getItems().get(0), maxFuel, maxFuel, milesPerGallon);
                //the label about the attributes of the helicopter are initially set after the helicopter
                // object is created
                currLocLabel.setText("Current Location: " + givenHeli.getCurrLocation().getLocationName());
                currFuelLabel.setText("Current Fuel Capacity: " + givenHeli.getCurrFuel());
                //if successful, the introduction scene is removed and the main menu scene is set
                givenPane.getChildren().removeAll(introductionLabel, maxFuelCapacityLabel, maxFuelCapacityField,
                        milesPerGallonLabel, milesPerGallonField, setupButton);
                givenPane.getChildren().addAll(mainMenuLabel, menuOptionsList, mainMenuButton, currLocLabel,
                        currFuelLabel);

            }
            //induced when the file is empty
            catch (IndexOutOfBoundsException e) {
                errorLabel.setText("Error: Input file was empty");
            }
            //errors can only result if the text fields are empty, or any other character besides numbers are entered
            catch (NumberFormatException e) {
                errorLabel.setText("Error: Invalid input in text boxes");
            }
        });

        /*
         * MAIN MENU SCENE
         */
        //when the "ENTER" button is pressed on the main menu
        mainMenuButton.setOnAction(actionEvent -> {
            //resets the error label and userFeedbackLabel
            errorLabel.setText("");
            userFeedbackLabel.setText("");
            //stores which menu choice was selected
            String choice = menuOptionsList.getSelectionModel().getSelectedItem();
            try {
                //if the choice is null--i.e., no choice is selected--then a NullPointerException is produced
                switch (choice) {
                    case "Choose a destination":
                        //the main menu scene is removed and the next scene is set
                        givenPane.getChildren().removeAll(mainMenuLabel, menuOptionsList, mainMenuButton,
                                currLocLabel, currFuelLabel);
                        givenPane.getChildren().addAll(destinationMenuLabel, givenLocations,
                                destinationMenuButton, goBackButton);
                        break;
                    case "Refuel":
                        //returns false and refuels if the location has fuel and the current fuel capacity
                        // isn't equal to the max fuel capacity
                        //otherwise, the helicopter is refueled and the body of the if statement isn't executed
                        if (!(givenHeli.refuel())) {
                            errorLabel.setText("Error: Unable to refuel");
                        }
                        //if the helicopter refueled, update the currFuelCapacityLabel and userFeedbackLabel
                        else {
                            currFuelLabel.setText("Current Fuel Capacity: " + Math.round(givenHeli.getCurrFuel()));
                            userFeedbackLabel.setText("Refueling successful!");
                        }
                        break;
                    case "Add a destination":
                        givenPane.getChildren().removeAll(mainMenuLabel, menuOptionsList, mainMenuButton,
                                currLocLabel, currFuelLabel);
                        givenPane.getChildren().addAll(addDestinationMenuLabel, addDestionGoBackButton,
                                addDestinationMenuButton, addLocationLabel, addLocationField, addXYCoordinateLabel,
                                addXYCoordinateField, addGasLabel, addGasBox);
                        break;
                    case "Remove a destination":
                        //the main menu scene is removed and the next scene is set
                        givenPane.getChildren().removeAll(mainMenuLabel, menuOptionsList, mainMenuButton,
                                currLocLabel, currFuelLabel);
                        int x = 5;
                        givenPane.getChildren().addAll(removeDestinationMenuLabel, givenLocations,
                                removeDestinationMenuButton, removeDestionGoBackButton);
                        break;
                    default:
                        errorLabel.setText("Error: Please select an option");
                        break;
                }
            }
            //induced when a choice isn't selected
            catch (NullPointerException e) {
                errorLabel.setText("Error: No option selected");
            }
        });

        /*
         * DESTINATION MENU SCENE
         */
        destinationMenuButton.setOnAction(actionEvent -> {
            //resets the error label and user feedback label
            errorLabel.setText("");
            userFeedbackLabel.setText("");
            //stores which location choice was selected
            Location choice = givenLocations.getSelectionModel().getSelectedItem();

            //I tried using a switch statement with my choice as the switch, but the developers of Java don't like
            //me to have nice things

            //if no location choice is selected
            if(choice == null) {
                errorLabel.setText("Error: No option selected");
            }
            //determines if the selected location is the current location of the helicopter
            else if (choice == givenHeli.getCurrLocation()) {
                errorLabel.setText("Error: Already in selected location");
            }
            //check if userHeli isn't able to fly to the given choice location
            //otherwise, the flyTo() method is executed and the body of this if statement is skipped
            else if (!(givenHeli.flyTo(choice))) {
                errorLabel.setText("Error: Unable to fly to " + choice.getLocationName() + ".\n" +
                        "The helicopter doesn't have enough gas!");
            }
            //if the helicopter is able to fly to the choice destination and has done so
            else {
                //the location and fuel labels for in the main menu are updated
                currLocLabel.setText("Current Location: " + givenHeli.getCurrLocation().getLocationName());
                currFuelLabel.setText("Current Fuel Capacity: " + Math.round(givenHeli.getCurrFuel()));
                //the scene is set to the main menu
                givenPane.getChildren().removeAll(destinationMenuLabel, givenLocations,
                        destinationMenuButton, goBackButton);
                givenPane.getChildren().addAll(mainMenuLabel, menuOptionsList, mainMenuButton,
                        currLocLabel, currFuelLabel);
                //user feedback label updated
                userFeedbackLabel.setText("Welcome to " + givenHeli.getCurrLocation().getLocationName() + '!');
            }
        });
        //pressing the "GO BACK" button removes all the items for the destination menu scene
        // and adds back the items for the main menu scene
        goBackButton.setOnAction(actionEvent -> {
            givenPane.getChildren().removeAll(destinationMenuLabel, givenLocations,
                    destinationMenuButton, goBackButton);
            givenPane.getChildren().addAll(mainMenuLabel, menuOptionsList, mainMenuButton,
                    currLocLabel, currFuelLabel);

        });

        /*
         * REMOVE DESTINATION MENU SCENE
         */
        removeDestinationMenuButton.setOnAction(actionEvent -> {
            //resets the error label and userFeedbackLabel
            errorLabel.setText("");
            userFeedbackLabel.setText("");
            //stores which location choice was selected
            Location choice = givenLocations.getSelectionModel().getSelectedItem();
            //if a choice isn't selected
            if (choice == null) {
                errorLabel.setText("Error: No option selected");
            } else if (choice == givenHeli.getCurrLocation()) {
                errorLabel.setText("Error: Cannot remove current location");
            } else {
                //removes the selected location from the arraylist
                givenLocations.getItems().remove(choice);
                //the scene is set to the main menu
                givenPane.getChildren().removeAll(removeDestinationMenuLabel, givenLocations,
                        removeDestinationMenuButton, removeDestionGoBackButton);
                givenPane.getChildren().addAll(mainMenuLabel, menuOptionsList, mainMenuButton,
                        currLocLabel, currFuelLabel);
                //user feedback label updated
                userFeedbackLabel.setText("Successfully removed " + choice.getLocationName() + "!");
            }
        });
        //pressing the "GO BACK" button removes all the items for the destination menu scene
        // and adds back the items for the main menu scene
        removeDestionGoBackButton.setOnAction(actionEvent -> {
            givenPane.getChildren().removeAll(removeDestinationMenuLabel, givenLocations,
                    removeDestinationMenuButton, removeDestionGoBackButton);
            givenPane.getChildren().addAll(mainMenuLabel, menuOptionsList, mainMenuButton,
                    currLocLabel, currFuelLabel);

        });

        /*
         * ADD DESTINATION MENU SCENE
         */
        addDestinationMenuButton.setOnAction(actionEvent -> {
            try {
                //sets location equal to location of the box
                String location = addLocationField.getText();
                //creates a temporary string to store input for xy coordinate
                String xyCoordinate = addXYCoordinateField.getText();
                //parses the string
                String[] xyArray = xyCoordinate.split(",");
                //sets the first part equal to the x coordinate
                int xCoordinate = Integer.parseInt(xyArray[0]);
                //sets the second part equal to the y coordinate
                int yCoordinate = Integer.parseInt(xyArray[1]);
                //sets gas equal to the checkbox
                boolean hasGas = addGasBox.isSelected();
                //adds a new location to the array using the given parameters
                givenLocations.getItems().add(new Location(location, xCoordinate, yCoordinate, hasGas));
                //goes back to the main menu
                givenPane.getChildren().removeAll(addDestinationMenuLabel, addDestionGoBackButton,
                        addDestinationMenuButton, addLocationLabel, addLocationField, addXYCoordinateLabel,
                        addXYCoordinateField, addGasLabel, addGasBox);
                givenPane.getChildren().addAll(mainMenuLabel, menuOptionsList, mainMenuButton,
                        currLocLabel, currFuelLabel);
            } catch (NumberFormatException e) {
                errorLabel.setText("Error: Invalid input in text boxes!\n" +
                        "Either boxes are blank or xy coordinate was inputted\n" +
                        "incorrectly. Make sure coordinates are enter as whole\n" +
                        "numbers, with no spaces and only separated\n" +
                        "by a comma");
            }
        });
        //pressing the "GO BACK" button removes all the items for the destination menu scene
        // and adds back the items for the main menu scene
        addDestionGoBackButton.setOnAction(actionEvent -> {
            givenPane.getChildren().removeAll(addDestinationMenuLabel, addDestionGoBackButton,
                    addDestinationMenuButton, addLocationLabel, addLocationField, addXYCoordinateLabel,
                    addXYCoordinateField, addGasLabel, addGasBox);
            givenPane.getChildren().addAll(mainMenuLabel, menuOptionsList, mainMenuButton,
                    currLocLabel, currFuelLabel);

        });

        /*
         * FILE WRITING
         */
        //
        stage.setOnCloseRequest(event -> {
            try {
                FileWriter fw = new FileWriter("locations.csv");
                for (int i = 0; i < givenLocations.getItems().size(); i++) {
                    fw.write(givenLocations.getItems().get(i).outputToLine());
                }
                fw.close();
            } catch (IOException e) {
                //the program is already closed out, so the only place to throw an error is in the terminal
                System.out.println("Error: Unable to write file!");
            }
        });

        stage.setScene(givenScene);
        stage.setTitle("Helicopter Navigation System");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}