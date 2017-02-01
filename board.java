package briochetranchecompany.poopcrush;



/**
 * Created by silentshad on 01/02/17.
 */

public class board {

    public void initBoard(int x, int y)
    {

        poop.PoopType[][]  grid = new poop.PoopType[x][y];
        for(int i =0; i < x;i++)
        {
            for(int j = 0;j<y;j++)
            {
                grid[i][j]= poop.PoopType.none;
            }
        }

    }
    /*public void setCase(int[][] grid,int x, int y)
    {

    }*/
}


