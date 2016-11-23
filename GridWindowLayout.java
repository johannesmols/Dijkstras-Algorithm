package dijkstrasalgorithm;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class GridWindowLayout 
{
    private static Rectangle[][] nodes;
    private static Button[] topButtons;
    private static Button[] leftButtons;
    private static CheckBox diagonal;
    
    public static HBox addTopMenu()
    {
        HBox topMenu = new HBox(10);
        topMenu.setPadding(new Insets(5));
        Button newFile = new Button();
        newFile.setText("New File");
        newFile.setMinWidth(100);
        Button openFile = new Button();
        openFile.setText("Open File");
        openFile.setMinWidth(100);
        Button saveFile = new Button();
        saveFile.setText("Save File");
        saveFile.setMinWidth(100);
        Button about = new Button();
        about.setText("About");
        about.setMinWidth(100);
        topMenu.getChildren().addAll(newFile, openFile, saveFile, about);
        
        topButtons = new Button[4];
        topButtons[0] = newFile;
        topButtons[1] = openFile;
        topButtons[2] = saveFile;
        topButtons[3] = about;
        
        return topMenu;
    }
    
    public static VBox addLeftMenu()
    {
        VBox leftMenu = new VBox(8);
        leftMenu.setPadding(new Insets(5));
        Button runButton = new Button();
        runButton.setText("Run");
        runButton.setMinWidth(100);
        Button startButton = new Button();
        startButton.setText("Set Start Point");
        startButton.setMinWidth(100);
        Button endButton = new Button();
        endButton.setText("Set End Point");
        endButton.setMinWidth(100);
        Button obstacleButton = new Button();
        obstacleButton.setText("Set Obstacle");
        obstacleButton.setMinWidth(100);
        Button clearButton = new Button();
        clearButton.setText("Clear field");
        clearButton.setMinWidth(100);
        Button clearAllButton = new Button();
        clearAllButton.setText("Clear all");
        clearAllButton.setMinWidth(100);
        Button clearPathButton = new Button();
        clearPathButton.setText("Clear Path");
        clearPathButton.setMinWidth(100);
        diagonal = new CheckBox();
        diagonal.setText("Diagonal");
        diagonal.setMinWidth(100);
        leftMenu.getChildren().addAll(runButton, startButton, endButton, obstacleButton, clearButton, clearAllButton, clearPathButton, diagonal);
        
        leftButtons = new Button[7];
        leftButtons[0] = runButton;
        leftButtons[1] = startButton;
        leftButtons[2] = endButton;
        leftButtons[3] = obstacleButton;
        leftButtons[4] = clearButton;
        leftButtons[5] = clearAllButton;
        leftButtons[6] = clearPathButton;
        
        return leftMenu;
    }
    
    public static GridPane addWorld(int rows, int columns)
    {
        GridPane world = new GridPane();
        world.setPadding(new Insets(5));
        
        nodes = new Rectangle[rows][columns];
        for(int i = 0; i < columns; i++)   {
            for(int j = 0; j < rows; j++)    {
                nodes[j][i] = new Rectangle(20, 20);    //j and i swapped because fuck oracle
                nodes[j][i].setFill(Color.LIGHTGREY); //default neutral color
                nodes[j][i].setStroke(Color.BLACK);
                nodes[j][i].setStrokeType(StrokeType.INSIDE);
                
                world.add(nodes[j][i], i, j);
            }
        }
        world.setGridLinesVisible(false);
        
        return world;
    }
    
    public static Rectangle[][] getNodes()
    {
        return nodes;
    }
    
    public static Button[] getTopButtons()
    {
        return topButtons;
    }
    
    public static Button[] getLeftButtons()
    {
        return leftButtons;
    }
    
    public static CheckBox getCheckBox()
    {
        return diagonal;
    }
}