module NewGraphFX {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens Controller to javafx.graphics, javafx.fxml;
	opens Main        to javafx.graphics, javafx.fxml;
}
