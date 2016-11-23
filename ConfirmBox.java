package dijkstrasalgorithm;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.stage.Modality;

/*
 *  answer = 1 -> user pressed 'yes'
 *  answer = 2 -> user pressed 'no'
 *  answer = 3 -> user pressed 'cancel'
 */

public class ConfirmBox 
{
    static int answer;
    
    public static int display(String title, String message)
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //block user interaction with other windows
        window.setTitle(title);
        window.setMinWidth(250);
        
        Label label = new Label();
        label.setText(message);
        
        Button yesButton = new Button();
        yesButton.setText("Yes");
        yesButton.setMinWidth(80);
        yesButton.setOnAction(e -> {
            answer = 1;
            window.close();
                });
        
        Button noButton = new Button();
        noButton.setText("No");
        noButton.setMinWidth(75);
        noButton.setOnAction(e -> {
            answer = 2;
            window.close();
                });

        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setMinWidth(75);
        cancelButton.setOnAction(e -> {
            answer = 3;
            window.close();
                });
        
        GridPane grid = new GridPane();
        GridPane.setColumnSpan(label, GridPane.REMAINING);
        //grid.setGridLinesVisible(true);
        grid.setHgap(5);
        grid.setVgap(10);
        GridPane.setConstraints(label, 0, 0); //columns, rows
        GridPane.setConstraints(yesButton, 0, 1); 
        GridPane.setConstraints(noButton, 1, 1);
        GridPane.setConstraints(cancelButton, 2, 1);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(33.3d);
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(33.3d);
        ColumnConstraints column3 = new ColumnConstraints();
        column1.setPercentWidth(33.4d);
        grid.getColumnConstraints().addAll(column1, column2, column3);
        grid.getChildren().addAll(label, yesButton, noButton, cancelButton);
        grid.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(grid, 250, 75);
        window.setScene(scene);
        window.showAndWait();
        
        return answer;
    }
}