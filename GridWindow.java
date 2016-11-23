//================================================================================
// 1. Variables (Line 15)
// 2. Starting method (Line 53)
// 3. Essential methods (Line 136)
// 4. Run Algorithm (Line 196)
// 5. File Handling Methods (Line 236)
// 6. Handling minor stuff (Line 308)
//================================================================================

package dijkstrasalgorithm;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class GridWindow
{
    //================================================================================
    // Variables   
    //================================================================================
    
    //Window, Scene, Layout
    private static Stage window;
    private static Scene scene;
    private static BorderPane borderPane; //layout object
    
    //Grid variables
    private static Rectangle[][] nodes; //visual representation of the current scene (nodeScene)
    private static int[][] nodeScene; //stores the whole current scene
    
    //Buttons
    private static Button[] topButtons; //0 = New File, 1 = Open File, 2 = Save File
    private static Button[] leftButtons; //0 = Run, 1 = Start Point, 2 = End Point, 3 = Obstacle, 4 = Clear
    private static CheckBox diagonalCheckBox;
    
    //Processing variables
    private static int selectedPaintMode;
    
    //Default values
    private static final int defaultRows = 20;
    private static final int defaultColumns = 30;
    
    //Colors
    private static final Color neutralColor = Color.LIGHTGRAY;
    private static final Color startColor = Color.GREEN;
    private static final Color endColor = Color.RED;
    private static final Color obstacleColor = Color.CHOCOLATE;
    private static final Color pathColor = Color.LIGHTSKYBLUE;
    
    
    
    //================================================================================
    // Starting method
    //================================================================================

    //Set everything up
    public static void open()
    {
        //Set window properties
        window = new Stage();
        window.setTitle("Dijkstras Pathfinding Algorithm");
        window.setMinWidth(750);
        window.setMinHeight(500);
        window.setWidth(750);
        window.setHeight(500);
        window.setOnCloseRequest(e -> {
            e.consume(); //own code handles close action
            closeProgram();
                });
        
        //Add menu buttons
        HBox topMenu = GridWindowLayout.addTopMenu();
        VBox leftMenu = GridWindowLayout.addLeftMenu();
        GridPane center = GridWindowLayout.addWorld(defaultRows, defaultColumns);
        
        //Variables to refer to the objects in the scene
        nodes = GridWindowLayout.getNodes();
        topButtons = GridWindowLayout.getTopButtons();
        leftButtons = GridWindowLayout.getLeftButtons();
        diagonalCheckBox = GridWindowLayout.getCheckBox();
        
        //Assing controls to certain parts of the window
        borderPane = new BorderPane();
        borderPane.setTop(topMenu);
        borderPane.setLeft(leftMenu);
        borderPane.setCenter(center);
        
        //Assing layout to scene and show the window
        scene = new Scene(borderPane);
        window.setScene(scene);
        window.show();
        
        //Event Handlers top
        topButtons[0].setOnAction(e -> newFile());
        topButtons[1].setOnAction(e -> openFile());
        topButtons[2].setOnAction(e -> saveFile());
        topButtons[3].setOnAction(e -> openAbout());
        
        //Event Handlers left
        leftButtons[0].setOnAction(e -> runAlgorithm());
        leftButtons[1].setOnAction(e -> selectedPaintMode = 1); //start
        leftButtons[2].setOnAction(e -> selectedPaintMode = 2); //end
        leftButtons[3].setOnAction(e -> selectedPaintMode = 3); //obstacle
        leftButtons[4].setOnAction(e -> selectedPaintMode = 4); //clear
        leftButtons[5].setOnAction(e -> clearAll());
        leftButtons[6].setOnAction(e -> displayUpdatedNodes(nodeScene)); //clear path
        
        //Random defaulting of check box
        diagonalCheckBox.setSelected(true);
        
        //this is the back-end matrix
        //init nodeScene, sets all nodes initially to zero
        nodeScene = new int[defaultRows][defaultColumns];
        for(int i = 0; i < nodeScene.length; i++) {
            for(int j = 0; j < nodeScene[0].length; j++) {
                nodeScene[i][j] = 0;
            }
        }
        
        //Rectangle click event handler
        for(int i = 0; i < nodes.length; i++) {
            for(int j = 0; j < nodes[0].length; j++) {
                final int i2 = i; //lambda expression variables must be final
                final int j2 = j;
                nodes[i][j].setOnMouseClicked(e -> {
                        nodeChange(nodeScene, i2, j2, selectedPaintMode);
                    }); 
            }
        }
    }
    
    
    
    //================================================================================
    // Essential methods
    //================================================================================
    
    //USE THIS AS A CALLER FOR CHANGE OF BACK-END MATRIX AND VISUAL UPDATE
    //It is recommended to only use this for a single node change because of performance reasons
    //To change a complete field use a two for-loops in which you set the nodeScene[i][j] to a value and call the method displayUpdatedNodes(nodeScene); afterwards
    private static void nodeChange(int[][] input, int row, int column, int paintMode)
    {
        nodeScene = GridWindowNodeChange.changeNodes(input, row, column, paintMode); //change values of the nodeScene depending on location and brush mode
        displayUpdatedNodes(nodeScene);
        System.out.println("Changed value at pos[" + row + "," + column + "], new value is " + paintMode);
    }
    
    //Convert an input matrix to a visual representation of this matrix
    /* - CALL THIS FUNCTION WHENEVER SOMETHING CHANGES IN THE BACK-END MATRIX - */
    /* - WHENEVER YOU WANT TO CHANGE A VALUE IN THE BACK-END MATRIX, CALL THE FUNCTION changeNodes() OF THE CLASS GridWindowNodeChange(nodeScene, row, column, newVal) - */
    private static void displayUpdatedNodes(int[][] input)
    {
        GridPane nodePane = GridWindowLayout.addWorld(input.length, input[0].length);
        nodes = GridWindowLayout.getNodes();
        
        //SET COLORS
        for(int i = 0; i < nodes.length; i++) {
            for(int j = 0; j < nodes[0].length; j++) {
                switch(input[i][j])
                {
                    case 0: //default node
                        nodes[i][j].setFill(neutralColor);
                        break;
                    case 1: //start node
                        nodes[i][j].setFill(startColor);
                        break;
                    case 2: //end node
                        nodes[i][j].setFill(endColor);
                        break;
                    case 3: //obstacle node
                        nodes[i][j].setFill(obstacleColor);
                        break;
                    default:
                        break;
                }
            }
        }
        
        //SET EVENT HANDLERS (must be declared every update)
        for(int i = 0; i < nodes.length; i++) {
            for(int j = 0; j < nodes[0].length; j++) {
                final int i2 = i; //lambda expression variables must be final
                final int j2 = j;
                nodes[i][j].setOnMouseClicked(e -> nodeChange(nodeScene, i2, j2, selectedPaintMode));
            }
        }
        
        //Apply changes
        borderPane.setCenter(nodePane);
    }
    
    
    
    //================================================================================
    // Run Algorithm
    //================================================================================
    
    /* RUN THE DIJKSTRAS PATHFINDING ALGORITHM */
    private static void runAlgorithm()
    {
        System.out.println("Checked: " + diagonalCheckBox.isSelected());
        clearPreviousPath();
        String pathSequence = RunAlgorithmGrid.run(nodeScene, diagonalCheckBox.isSelected());
        
        //Visually represent shortest path
        for(int i = 0; i < pathSequence.length(); i += 6) //6 chars are one coordinate
        {
            String subString = pathSequence.substring(i, i+6);
            paintSequencePath(subString);
        }
    }
    
    //Clear all path nodes by just loading in the back-end matrix which is unchanged by the algorithm
    private static void clearPreviousPath()
    {
        displayUpdatedNodes(nodeScene);
    }
    
    //this paints all parts of the path in blue, can be easily undone by redrawing the back-end matrix (nodeScene)
    private static void paintSequencePath(String coordinates) 
    {
        String row = coordinates.substring(0, 3);
        String column = coordinates.substring(3, 6);
        int int_row = Integer.parseInt(row);
        int int_column = Integer.parseInt(column);
        
        nodes[int_row][int_column].setFill(pathColor);
    }
    
    
    
    //================================================================================
    // File Handling Methods
    //================================================================================
    
    /* SAVE FILE */
    private static void saveFile()
    {
        FileDialog.saveFile(nodeScene, window); //need to give over the parent window
    }
    
    /* OPEN FILE */
    private static void openFile()
    {
        //set the current nodes to the nodes in the save file
        int[][] savefile = FileDialog.openFile(window);
        
        GridPane center = GridWindowLayout.addWorld(savefile.length, savefile[0].length);
        nodes = GridWindowLayout.getNodes();
        
        //Set Event Handlers - must be done every time
        for(int i = 0; i < nodes.length; i++) {
            for(int j = 0; j < nodes[0].length; j++) {
                final int i2 = i; //lambda expression variables must be final
                final int j2 = j;
                nodes[i][j].setOnMouseClicked(e -> nodeChange(nodeScene, i2, j2, selectedPaintMode));
            }
        }
        borderPane.setCenter(center); 
        
        //The method GridWindowNodeChange.changeNodes() is NOT called because it's to less efficient and it's only thought to be used for single node changes
        nodeScene = new int[savefile.length][savefile[0].length];
        for(int i = 0; i < savefile.length; i++) {
            for(int j = 0; j < savefile[0].length; j++) {
                nodeScene[i][j] = savefile[i][j];
            }
        }
        displayUpdatedNodes(nodeScene);
    }
    
    /* NEW FILE */
    private static void newFile()
    {
        int[] result = NewFile.newFile();
        if(result == null) //don't apply changes if no values received
            return;
        
        GridPane center = GridWindowLayout.addWorld(result[0], result[1]);
        nodes = GridWindowLayout.getNodes();
        
        //Event handlers
        for(int i = 0; i < result[0]; i++) {
            for(int j = 0; j < result[1]; j++) {
                final int i2 = i; //lambda expression variables must be final
                final int j2 = j;
                nodes[i][j].setOnMouseClicked(e -> nodeChange(nodeScene, i2, j2, selectedPaintMode));
            }
        }
        borderPane.setCenter(center);
        
        //The method GridWindowNodeChange.changeNodes() is NOT called because it's to less efficient and it's only thought to be used for single node changes
        nodeScene = new int[result[0]][result[1]];
        for(int i = 0; i < result[0]; i++) {
            for(int j = 0; j < result[1]; j++) {
                nodeScene[i][j] = 0; //new scene, everything on default
            }
        }
        displayUpdatedNodes(nodeScene);
    }
    
    
    
    //================================================================================
    // Handling minor stuff
    //================================================================================
    
    /* CLEAR ALL NODES */
    //This method doesn't call nodeChange() because it takes to much computational power, nodeScene is therefore just set to 0 at all indices
    private static void clearAll()
    {
        for(int i = 0; i < nodeScene.length; i++) {
            for(int j = 0; j < nodeScene[0].length; j++) {
                nodeScene[i][j] = 0;
            }
        }
        displayUpdatedNodes(nodeScene);
    }
    
    /* CLOSE THE PROGRAM PROPERLY */
    private static void closeProgram()
    {
        int result = ConfirmBox.display("Close", "Do you want to save and quit?");
        switch(result)
        {
            case 1: //save and close
                saveFile();
                window.close();
                break;
            case 2: //no, just close
                window.close();
                break;
            case 3: //cancel close action
                break;
            default: 
                break;
        }
    }
    
    /* OPEN ABOUT WINDOW */
    private static void openAbout()
    {
        AboutWindow.display();
    }
}