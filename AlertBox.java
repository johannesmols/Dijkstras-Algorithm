package dijkstrasalgorithm;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.stage.Modality;

public class AlertBox 
{
    public static void display(String title, String message)
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //block user interaction with other windows
        window.setTitle(title);
        window.setMinWidth(250);
        
        Label label = new Label();
        label.setText(message);
        
        Button closeButton = new Button();
        closeButton.setText("Okay");
        closeButton.setOnAction(e -> window.close());
        
        VBox layout = new VBox(30); //column distance 10 between objects
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}