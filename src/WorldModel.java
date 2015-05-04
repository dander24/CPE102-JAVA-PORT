import javafx.util.Pair;

import java.lang.reflect.Type;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        numRows = rows;
        numCols = cols;
        background = new OccupancyGrid(numRows,numCols, _background);
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

    public Entity findNearest(Point pt, Entity ent)
    {
        ArrayList<Pair<Entity,Double>> ofType = new ArrayList<>();

        for(Entity e : entities)
        {
            if (e.getClass() == ent.getClass() )
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

    public Point nextPosition(Point EntityPt, Point DestinationPt)
    {
        int horiz = sign(DestinationPt.getX() - EntityPt.getX());
        Point newPt = new Point(EntityPt.getX()+horiz, EntityPt.getY());

        if (horiz == 0 | isOccupied(newPt))
        {
            int vert = sign(DestinationPt.getY() - EntityPt.getY());
            newPt = new Point(EntityPt.getX(), EntityPt.getY() + vert);

            if (vert == 0 | isOccupied(newPt))
            {
                newPt = new Point (EntityPt.getX(), EntityPt.getY());
            }

        }
        return newPt;
    }

    public Point blobNextPosition(Point EntityPt, Point DestinationPt)
    {
        int horiz = sign(DestinationPt.getX() - EntityPt.getX());
        Point newPt = new Point(EntityPt.getX()+horiz, EntityPt.getY());

        if (horiz == 0 | getTileOccupant(newPt).getClass() != Ore.class)
        {
            int vert = sign(DestinationPt.getY() - EntityPt.getY());
            newPt = new Point(EntityPt.getX(), EntityPt.getY() + vert);

            if (vert == 0 | getTileOccupant(newPt).getClass() != Ore.class)
            {
                newPt = new Point (EntityPt.getX(), EntityPt.getY());
            }

        }
        return newPt;
    }

    public Pair<Point[], Boolean> minerToOre(Entity entity, Entity ore)
{
    Point entityPoint = ((Actor)entity).getPosition();
    if (ore.getClass() != Ore.class)
    {
        return new Pair<>(new Point[]{entityPoint},false);
    }
    Point orePoint = ((Ore)entity).getPosition();

    if (adjacent(entityPoint,orePoint))
    {
        ((Miner)entity).setResourceCount(1 + ((Miner) entity).getResourceCount());;
        worldRemoveEntity(ore);
        return new Pair<>(new Point[]{orePoint}, true);
    }

    else
    {
        Point newPt = nextPosition(entityPoint, orePoint);
        return new Pair<>(moveEntity(entity,newPt),false);
    }
}
    public Pair<Point[], Boolean> minerToSmith(Entity entity, Entity smith)
    {
        Point entityPoint = ((Actor)entity).getPosition();
        if (smith.getClass() != Blacksmith.class)
        {
            return new Pair<>(new Point[]{entityPoint},false);
        }
        Point smithPoint = ((Blacksmith)entity).getPosition();

        if (adjacent(entityPoint,smithPoint))
        {
            ((Blacksmith)entity).setResourceCount(((Miner) entity).getResourceCount() +
                    ((Blacksmith) entity).getResourceCount());
            ((Miner)entity).setResourceCount(0);
            return new Pair<>(new Point[]{smithPoint}, true);
        }

        else
        {
            Point newPt = nextPosition(entityPoint, smithPoint);
            return new Pair<>(moveEntity(entity,newPt),false);
        }
    }

    public Pair<Point[], Boolean> blobToVein(Entity entity, Entity vein)
    {
        Point entityPoint = ((Actor)entity).getPosition();
        if (vein.getClass() != Vein.class)
        {
            return new Pair<>(new Point[]{entityPoint},false);
        }
        Point veinPoint = ((Vein)vein).getPosition();

        if (adjacent(entityPoint,veinPoint))
        {
            worldRemoveEntity(vein);
            return new Pair<>(new Point[]{veinPoint}, true);
        }

        else
        {
            Point newPt = blobNextPosition(entityPoint, veinPoint);
            Entity oldEntity = getTileOccupant(newPt);

            if (oldEntity.getClass() == Ore.class)
            {
                removeEntity(oldEntity);
            }
            return new Pair<>(moveEntity(entity,newPt),false);
        }
    }

    public Point findOpenAround(Point pt, int distance)
    {
        for (int i = -distance; i < distance + 1; i++)
        {
            for (int j = -distance; j < distance + 1 ; j++)
            {
              Point newPt = new Point(pt.getX() + j, pt.getY() + i);

                if (withinBounds(newPt) && !isOccupied(newPt))
                {
                    return newPt;
                }

            }
        }

        return null;
    }

    private void removeEntity(Entity entity)
    {
        //for action in pending actions, remove actions
        worldRemoveEntity(entity);
    }

    public OreBlob createBlob(String name, Point pt, int rate, int ticks)
    {
        Random rand = new Random();
        OreBlob b = new OreBlob(name, pt, rate, rand.nextInt((BLOB_ANIMATION_MAX * BLOB_ANIMATION_RATE_SCALE) -
                BLOB_ANIMATION_MIN) + BLOB_ANIMATION_MIN );
        //schedule
        return b;
    }

    public Ore createOre(String name, Point pt, int ticks)
    {
        Random rand = new Random();
        Ore o = new Ore(name, pt, rand.nextInt(ORE_CORRUPT_MAX-ORE_CORRUPT_MIN) + ORE_CORRUPT_MIN);
        //schedule
        return o;
    }

    public Quake createQuake(Point pt, int ticks)
    {
        Quake q = new Quake("quake", pt, QUAKE_ANIMATION_RATE);
        //schedule
        return q;
    }

    public Vein createVein(String name, Point pt, int ticks)
    {
        Random rand = new Random();
        Vein v = new Vein("vein" + name, pt, rand.nextInt(VEIN_RATE_MAX - VEIN_RATE_MIN) + VEIN_RATE_MIN, 1);
        //schedule
        return v;
    }




}
