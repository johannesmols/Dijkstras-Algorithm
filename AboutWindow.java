package dijkstrasalgorithm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutWindow 
{
    public static void display()
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //block user interaction with other windows
        window.setTitle("About");
        window.setMinWidth(250);
        
        Label info = new Label();
        info.setText("© Johannes Mols 2016");
        
        Button close = new Button();
        close.setText("Close");
        close.setMinWidth(200);
        close.setOnAction(e -> window.close());
        
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(5));
        layout.getChildren().addAll(info, close);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }
}
