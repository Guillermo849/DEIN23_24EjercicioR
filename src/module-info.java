module DEIN23_24EjercicioR {
	requires javafx.controls;
	requires java.desktop;
	requires javafx.web;
	requires javafx.fxml;
	requires javafx.swing;
	requires javafx.media;
	requires javafx.graphics;
	
//	opens model to javafx.base;
	opens application to javafx.graphics, javafx.fxml;
//	opens controllers to javafx.graphics, javafx.fxml;
}
