module com.example.helicopter_navigation_program {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.helicopter_navigation_program to javafx.fxml;
    exports com.example.helicopter_navigation_program;
}