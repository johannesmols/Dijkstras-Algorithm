package dijkstrasalgorithm;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.stage.Modality;

public class NewFile 
{
    private static int rows;
    private static int columns;
    
    private static TextField txtRows;
    private static TextField txtColumns;
    
    private static int[] result = new int[2];
    
    private static Stage window;
    
    public static int[] newFile()
    {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //block user interaction with other windows
        window.setTitle("New File");
        window.setMinWidth(250);
        window.setOnCloseRequest(e -> {
                e.consume();
                close(false); //don't apply changes
            });
        
        Label rowInput = new Label();
        rowInput.setText("Rows:");
        
        Label columnInput = new Label();
        columnInput.setText("Columns:");
        
        txtRows = new TextField(); //doesn't allow anything other than number
        txtRows.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            txtRows.setText(newValue.replaceAll("[^\\d]", ""));
        });
        
        txtColumns = new TextField(); //doesn't allow anything other than number
        txtColumns.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            txtColumns.setText(newValue.replaceAll("[^\\d]", ""));
        });
        
        Button okButton = new Button();
        okButton.setMinWidth(150);
        okButton.setText("Okay");
        okButton.setOnAction(e -> {
            close(true); //apply changes
        });
        
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        GridPane.setConstraints(rowInput, 0, 0);
        GridPane.setConstraints(columnInput, 0, 1);
        GridPane.setConstraints(txtRows, 1, 0);
        GridPane.setConstraints(txtColumns, 1, 1);
        GridPane.setConstraints(okButton, 1, 2);
        
        grid.getChildren().addAll(rowInput, columnInput, txtRows, txtColumns, okButton);
        grid.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(grid, 250, 100);
        window.setScene(scene);
        window.showAndWait();
        
        return result;
    }
    
    private static void close(boolean apply)
    {
        if(apply)
        {
            if(!((txtRows.getText().equals("") && txtColumns.getText().equals("")) && (txtRows.getText() != null && txtColumns.getText() != null)))
            {
                result[0] = Integer.parseInt(txtRows.getText());
                result[1] = Integer.parseInt(txtColumns.getText());
            }
        }
        window.close();
    }
}