module ca.utoronto.utm.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens ca.utoronto.utm.assignment2 to javafx.fxml;
    exports ca.utoronto.utm.assignment2.scribble;
    exports ca.utoronto.utm.assignment2.paint;
    exports ca.utoronto.utm.assignment2.paint.controlPanels;
    exports ca.utoronto.utm.assignment2.paint.commandMenuBar;
    exports ca.utoronto.utm.assignment2.paint.shapes;


}