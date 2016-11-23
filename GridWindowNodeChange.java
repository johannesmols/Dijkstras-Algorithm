package dijkstrasalgorithm;

public class GridWindowNodeChange 
{
    //This is only supposed to change single nodes, although it can be used to update a whole set of nodes, which is really unefficient though
    public static int[][] changeNodes(int[][] input, int row, int column, int newValue)
    {
        int[][] output = new int[input.length][input[0].length];
        for(int i = 0; i < output.length; i++) {
            for(int j = 0; j < output[0].length; j++) {
                output[i][j] = input[i][j];
            }
        }
        
        switch(newValue)
        {
            case 1: //start value
                output = removeStartOrEndNode(output, true); //remove other start point before adding a new one
                output[row][column] = 1;
                break;
            case 2: //end value
                output = removeStartOrEndNode(output, false); //remove other end point before adding a new one
                output[row][column] = 2;
                break;
            case 3: //obstacle
                output[row][column] = 3;
                break;
            case 4: //clear
                output[row][column] = 0;
                break;
            default:
                break;
        }
        
        return output;
    }
    
    private static int[][] removeStartOrEndNode(int[][] input, boolean startOrEnd)
    {
        int[][] output = new int[input.length][input[0].length];
        for(int i = 0; i < output.length; i++) {
            for(int j = 0; j < output[0].length; j++) {
                output[i][j] = input[i][j];
                //check if node is start or end
                if(startOrEnd)
                {
                    if(output[i][j] == 1) //if start point, set to zero
                        output[i][j] = 0;
                }
                else
                {
                    if(output[i][j] == 2) //if end point, set to zero
                        output[i][j] = 0;   
                }
            }
        }
        return output;
    }
}