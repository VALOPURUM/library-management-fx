module com.library.librarymanagementui.librarymanagementfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.library.librarymanagementui.librarymanagementfx to javafx.fxml;
    exports com.library.librarymanagementui.librarymanagementfx;
    exports com.library.librarymanagementui.librarymanagementfx.controller;
    opens com.library.librarymanagementui.librarymanagementfx.controller to javafx.fxml;
    exports com.library.librarymanagementui.librarymanagementfx.service;
    opens com.library.librarymanagementui.librarymanagementfx.service to javafx.fxml;
}