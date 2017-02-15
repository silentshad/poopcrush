package briochetranchecompany.poopcrush;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Pair;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by silentshad on 01/02/17.
 */

public class Board
{


    public Poop[][] grid; // grid wich will be displayed during the game
    Random rand; // generator of random int
    int width ;
    int height;
    int thresh = 3;
    int poop_count = 5;

    public Board()//int width, int height )
    {
        rand = new Random();
        this.width = 4;
        this.height = 5;

        grid = new Poop[width][height];
        for (int i =0 ; i<width; i++)
        {
            for (int j = 0; j<height; j++)
            {

                Poop poop = new Poop(Poop.TYPE.BASIC);
                // give the skin
                poop.skin = rand.nextInt(poop_count-1);
                grid[i][j] = poop ;

            }
        }
    }

    public boolean isvalid(int x, int y)
    {
        return ( x<width && x>= 0 && y<height && y >=0);
    }

    public boolean swapping(int x1, int y1, int x2 , int y2)
    {
        //Log.d(TAG, "swapping: "+ x1 +"|"+y1 +"   and    "+ x2+"|"+y2);
        Poop poop1 = grid[x1][y1];
        Poop poop2 = grid[x2][y2];


        if ( poop1.isMoveable() && poop2.isMoveable() && !poop1.getMoving() && !poop2.getMoving())
        {
            int a = x1-x2;
            int b = y1-y2;

            if( (Math.abs(a) == 1 && b==0) ) // vertical swap
            {
                grid[x1][y1] = poop2;
                grid[x2][y2] = poop1;
                if (swifth_score_check(x1,y1) || swifth_score_check(x2,y2)) {
                    poop2.setSwapV_offset((float) b);
                    poop1.setSwapV_offset((float) (-b));
                    return true;
                }
                else
                {
                    grid[x1][y1] = poop1;
                    grid[x2][y2] = poop2;
                    return  false;
                }
            }
            else if (Math.abs(b) == 1 && a==0) // horizontal swap
            {
                grid[x1][y1] = poop2;
                grid[x2][y2] = poop1;
                if (swifth_score_check(x1,y1) || swifth_score_check(x2,y2)) {
                    poop2.setSwapH_offset((float) a);
                    poop1.setSwapH_offset((float) (-a));
                    return true;
                }
                else {
                    grid[x1][y1] = poop1;
                    grid[x2][y2] = poop2;
                    return false;
                }
            }
            return  false;
        }
        return false;

    }

    public Poop get(int x, int y)
    {
        return grid[x][y];
    }

    public void get_neighbourH(int x, int y, ArrayList<Pair<Integer,Integer>> L) // add  unvisited neighbour to L
    {
        Poop poop = get(x, y);

        int i = 1; // line to the left
        while (isvalid(x - i, y)  && grid[x - i][y].skin == poop.skin && !get(x-i,y).getMoving()) {
            L.add(new Pair<>(x - i, y));
            i++;
        }

        i = 1; // line to the right;
        while (isvalid(x + i, y)  && grid[x + i][y].skin == poop.skin && !get(x+i,y).getMoving())
        {
            L.add(new Pair<>(x + i, y));
            i++;
        }
    }
    public void get_neighbourV(int x, int y, ArrayList<Pair<Integer,Integer>> L) // add  unvisited neighbour to L
    {
        Poop poop = get(x, y);

        int i = 1; // line to the top
        while(  isvalid(x,y-i) && grid[x][y-i].skin == poop.skin  && !get(x,y-i).getMoving())
        {
            L.add(new Pair<>(x, y-i));
            i++;
        }

        i = 1; // to the bottom
        while(  isvalid(x,y+i) && grid[x][y+i].skin == poop.skin && !get(x,y+i).getMoving())
        {
            L.add(new Pair<>(x, y+i));
            i++;
        }

    }

    public long score_and_destroy(int x, int y) // return 0 if not enough poop are touching
    {
        ArrayList<Pair<Integer,Integer>> neigh_listH = new ArrayList<>();
        ArrayList<Pair<Integer,Integer>> neigh_listV = new ArrayList<>();

        get_neighbourH(x, y, neigh_listH);
        get_neighbourV(x, y, neigh_listV);

        int  sizeH = neigh_listH.size();
        int  sizeV = neigh_listV.size();

        //Log.d(TAG, "score_pointH: " + sizeH);
       // Log.d(TAG, "score_pointV: " + sizeV);

        long score = 0;


        if ( sizeH>= thresh-1 ||  sizeV>= thresh-1)
        {
            Poop first = new Poop(Poop.TYPE.EMPTY);
            score += get(x,y).getPoint();
            first.skin = poop_count - 1;
            grid[x][y] = first;


            if (sizeH >= thresh - 1)
            {
                for (Pair<Integer, Integer> p : neigh_listH)
                {
                    score += get(p.first,p.second).getPoint();
                    Poop temp_poop = new Poop(Poop.TYPE.EMPTY);
                    temp_poop.skin = poop_count - 1;
                    grid[p.first][p.second] = temp_poop;

                }
            }
            if (sizeV >= thresh - 1)
            {
                for (Pair<Integer, Integer> p : neigh_listV)
                {
                    score += get(p.first,p.second).getPoint();
                    Poop temp_poop = new Poop(Poop.TYPE.EMPTY);
                    temp_poop.skin = poop_count - 1;
                    grid[p.first][p.second] = temp_poop;
                }


            }
            return score;
        }
        else
            return score;

    }

    public boolean swifth_score_check (int x, int y)
    {
         ArrayList<Pair<Integer,Integer>> neigh_listH = new ArrayList<>();
        ArrayList<Pair<Integer,Integer>> neigh_listV = new ArrayList<>();

        get_neighbourH(x, y, neigh_listH);
        get_neighbourV(x, y, neigh_listV);

        int  sizeH = neigh_listH.size();
        int  sizeV = neigh_listV.size();

        //Log.d(TAG, "swifth_score_check: " + (sizeH > thresh-1 || sizeV > thresh-1) );

        return sizeH >= thresh-1 || sizeV >= thresh-1;
    }

    public void defecate(int x, int y, float offset)
    {
        Poop poop = new Poop(Poop.TYPE.BASIC);
        // give the skin
        poop.skin = rand.nextInt(poop_count-1);
        poop.setOffset(offset);
        grid[x][y] = poop ;
    }

    public Pair<Integer,Integer> empty_check()
    {
       Pair<Integer,Integer>  empty_poop = null;
        boolean found = false;

        int  i = width -1;

       while( i>= 0 && !found )
        {
            int j = height -1;
            while(j>= 0 && !found )
            {

                //Log.d(TAG, "empty_check: " +j);
                if( get(i,j).type == Poop.TYPE.EMPTY)
                {
                    found = true;
                    empty_poop = new  Pair<>(i,j);
                }

                j--;
            }

            i--;
        }
        return empty_poop;
    }

    public void fall(int x, int y ,int count)
    // y is where the first poop needs to fall by count block

    {
        if (y >= 0)
        {
            for (int j = y; j >= 0; j--) {
                grid[x][j + count] = grid[x][j];
                get(x, j + count).setOffset(count);

            }
        }
    }

    public void  fill()
    {
        boolean full = false;
        while (!full)
        {
            Pair<Integer,Integer>  current = empty_check() ;
            if (current != null )
            {
                int j = current.second ;
                int count = 0;
                // count the number of empty block above the current one
                while (j >=0 && get(current.first,j).type == Poop.TYPE.EMPTY )
                {
                    count++;
                    j--;
                }
                fall(current.first, j, count);

                j = 0 ;
                while (j < height && get(current.first,j).type == Poop.TYPE.EMPTY )
                {
                    defecate( current.first, j , count);
                    j++;
                }
            }
            else
                full = true;
        }

    }

    public  void decrease_offset( float decrease)
    {
        for (int i = 0; i< width ; i++)
        {
            for (int j = 0 ; j< height ; j++)
            {
                Poop current = get(i,j);
                current.setOffset( current.getOffset() - decrease);
                current.setSwapV_offset( current.getSwapV_offset() - decrease/10);
                current.setSwapH_offset( current.getSwapH_offset() - decrease/10);
            }
        }
    }


}


