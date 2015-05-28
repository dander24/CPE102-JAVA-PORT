import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Animated extends Actor {

    private int animationRate;
    private int[][] pathing = null;
    private ArrayList<Point> nextMoves;

    public Animated(String name, Point position, List<PImage> pImages, int rate, int animationRate) {
        super(name, position, rate, pImages);
        this.animationRate = animationRate;
        nextMoves = new ArrayList<>();
    }

    public int getAnimationRate() {
        return animationRate;
    }

    public void setPathing(int[][] newPathing)
    {
        pathing = newPathing;
    }

    public void setPathingValue(Point point, int value)
    {
        pathing[point.getY()][point.getX()] = value;
    }

    public int getPathingValue(Point point)
    {
        if (pathing != null) {
            return pathing[point.getY()][point.getX()];
        }
        return 0;
    }

    public void addToMoves(Point point)
    {
        nextMoves.add(0,point);
    }

    public int getMovesListSize()
    {
        return nextMoves.size();
    }

    public void cleanMovesList()
    {
        while(nextMoves.size() > 0)
        {
            nextMoves.remove(0);
        }
    }

    public Point getNextMove()
    {
        return nextMoves.get(0);
    }

    public Point makeNextMove()
    {
        Point pointToReturn = nextMoves.get(0);
        nextMoves.remove(0);
        return pointToReturn;
    }
}
