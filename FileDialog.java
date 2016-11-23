package dijkstrasalgorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileDialog 
{
    public static int[][] openFile(Stage parent)
    {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Open save file");
        File file = fileChooser.showOpenDialog(parent);

        List<String> list;
        char[][] items;

        if (file != null)
        {
            System.out.println("Name of opened file: " + file.getName());
            System.out.println("Path of opened file: " + file.getAbsolutePath());

            String fileName = file.getAbsolutePath();
            list = new ArrayList<>();

            try(BufferedReader br = Files.newBufferedReader(Paths.get(fileName)))
            {
                list = br.lines().collect(Collectors.toList());
            } 
            catch (IOException ex) {
                Logger.getLogger(FileDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
            //list.forEach(System.out::println); //output the file

            String[] savefile = new String[list.size()];
            savefile = list.toArray(savefile);
            //Seperate file into chars
            items = new char[savefile.length][savefile[0].length()]; //row, length
            for(int i = 0; i < savefile.length; i++)
            {
                for(int j = 0; j < savefile[0].length(); j++)
                {
                    items[i][j] = savefile[i].charAt(j);
                }
            }
        }
        else
        {
            System.out.println("File is not valid or wasn't found");
            return null;
        }

        //Convert chars to ints
        int[][] nodeScene = new int[items.length][items[0].length];
        for(int i = 0; i < items.length; i++) //rows
        {
            for(int j = 0; j < items[0].length; j++) //columns
            {
                nodeScene[i][j] = convertCharToInt(items[i][j]);
            }

        }

        return nodeScene;
    }
    
    public static void saveFile(int[][] nodeScene, Stage parent)
    {
        //Convert ints to chars
        char[][] values = new char[nodeScene.length][nodeScene[0].length];
        String[] output = new String[values.length];
        for(int i = 0; i < nodeScene.length; i++)
        {
            output[i] = "";
            for(int j = 0; j < nodeScene[0].length; j++)
            {
                values[i][j] = convertIntToChar(nodeScene[i][j]);
                output[i] += values[i][j];
            }
        }
        
        //Save
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", ".txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(parent);
        if(file != null)
        {
            try
            {
                FileWriter writer = new FileWriter(file);
                try (BufferedWriter bw = new BufferedWriter(writer)) {
                    for(int i = 0; i < output.length; i++)
                    {
                        bw.write(output[i]);
                        bw.newLine();
                        bw.flush();
                    }
                }
            System.out.println("Saved");
            }
            catch (IOException ex)
            {
                Logger.getLogger(FileDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static char convertIntToChar(int input)
    {
        char c = ' ';
        switch (input) {
            case 0:
                c = '0';
                break;
            case 1:
                c = '1';
                break;
            case 2:
                c = '2';
                break;
            case 3:
                c = '3';
                break;
            default:
                break;
        }
        return c;
    }
    
    private static int convertCharToInt(char c)
    {
        /*
         *  0 = neutral node
         *  1 = start node
         *  2 = end node
         *  3 = obstacle
         */
        int result;
        switch(c)
        {
            case '0':
                result = 0;
                break;
            case '1':
                result = 1;
                break;
            case '2':
                result = 2;
                break;
            case '3':
                result = 3;
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }
}