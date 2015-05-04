import javafx.util.Pair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WorldModel
{
    private final int BLOB_RATE_SCALE = 4,
                      BLOB_ANIMATION_RATE_SCALE = 50,
                      BLOB_ANIMATION_MIN = 1,
                      BLOB_ANIMATION_MAX = 3,
                      ORE_CORRUPT_MIN = 20000,
                      ORE_CORRUPT_MAX = 30000,

                       QUAKE_STEPS = 10,
                      QUAKE_DURATION = 1100,
                      QUAKE_ANIMATION_RATE = 100,

                     VEIN_SPAWN_DELAY = 500,
                     VEIN_RATE_MIN = 8000,
                     VEIN_RATE_MAX = 17000;

    private ArrayList<Entity> entities;
    private OccupancyGrid background, occupancy;
    private int numRows, numCols;


    public WorldModel(int rows, int cols, Background _background)
    {
        background = new OccupancyGrid(numRows,numCols, _background);
        numRows = rows;
        numCols = cols;
        occupancy = new OccupancyGrid(numRows,numCols, null);
        entities = new ArrayList<>();
    }

    public boolean withinBounds(Point pt)
    {
        return (pt.getX() >= 0 && pt.getX() < numCols && pt.getY() >= 0 && pt.getY() < numRows );
    }

    public boolean isOccupied(Point pt)
    {
        return (withinBounds(pt) && (occupancy.getCell(pt) != null));
    }

    private Entity nearestEntity(ArrayList<Pair<Entity,Double>> EntityDistances)
    {
        //functions using pairs of keys[entities] and values[distances] <k,v>, a cheap but ultimately effective fix
        if (EntityDistances.size() > 0)
        {
            Pair<Entity, Double> pair = EntityDistances.get(0);

            for (Pair<Entity,Double> other : EntityDistances)
            {
                if (other.getValue() < pair.getValue())
                {
                    pair = other;
                }
            }
            return pair.getKey();
        }
        else
        {
            //return null in the absence of an entity, may need to be handled or changed later
            return null;
        }
    }

    private double distanceSquared(Point pt1, Point pt2)
    {
        return ( (pt1.getX() - pt2.getX()) * (pt1.getX() - pt2.getX()) +
                (pt1.getY() - pt2.getY()) * (pt1.getY() - pt2.getY()));
    }

    public Entity findNearest(Point pt, Entity type)
    {
        ArrayList<Pair<Entity,Double>> ofType = new ArrayList<>();

        for(Entity e : entities)
        {
            if (e.getClass() == type.getClass() )
            {
                ofType.add(new Pair<>(e, distanceSquared(pt, ((Actor)e).getPosition())));
            }
        }

        return nearestEntity(ofType);
    }

    public void addEntity(Entity entity)
    {
        Point pt = new Point( ((Actor)entity).getPosition().getX(),((Actor)entity).getPosition().getY() );
        if (withinBounds(pt))
        {
            Entity oldEntity = occupancy.getCell(pt);
            if (oldEntity != null)
            {
                //clear pending actions
            }
            occupancy.setCell(pt, entity);
            entities.add(entity);
        }
    }

    public Point[]  moveEntity(Entity entity, Point pt)
    {
        Point[] tiles = new Point[]{null, null};
        if (withinBounds(pt))
        {
            Point oldPt = ((Actor)entity).getPosition();
            occupancy.setCell(oldPt, null);
            tiles[0] = oldPt;
            occupancy.setCell(pt, entity);
            tiles[1] = pt;
            ((Actor) entity).setPosition(pt);
        }
        return tiles;
    }

    public void worldRemoveEntity(Entity entity)
    {
        worldRemoveEntityAt(((Actor) entity).getPosition());
    }

    private void worldRemoveEntityAt(Point pt)
    {
        if (withinBounds(pt) && occupancy.getCell(pt) != null)
        {
            Entity entity = occupancy.getCell(pt);
            ((Actor)entity).setPosition(new Point (-1,-1));
            entities.remove(entity);
            occupancy.setCell(pt, null);
        }
    }

    public Entity getBackground(Point pt)
    {
        if (withinBounds(pt))
        {
            return background.getCell(pt);
        }

        return null;
    }

    public void setBackground(Point pt, Entity bgnd)
    {
        if (withinBounds(pt))
        {
            background.setCell(pt,bgnd);
        }
    }

    public Entity getTileOccupant(Point pt)
    {
        if (withinBounds(pt))
        {
            return occupancy.getCell(pt);
        }

        return null;
    }

    //code ported from actions, would fit in a utility class but they're pretty much stray methods and don't get used elsewhere (I think)

    private int sign(int x)
    {
        if ( x < 0)
        {
            return -1;
        }
        else if (x > 0)
        {
            return  1;
        }

        return  0;
    }

    private boolean adjacent(Point pt1, Point pt2)
    {
        return ( ((pt1.getX() == pt2.getX()) && Math.abs(pt1.getY() - pt2.getY()) == 1) |
            (pt1.getY() == pt2.getY()) && Math.abs(pt1.getX() - pt2.getX()) == 1);
    }

    //end code ported from actions



}
