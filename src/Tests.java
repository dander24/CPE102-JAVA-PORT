import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.Before;

public class Tests
{

    @Test
    public void testBackground()
    {
        Background b = new Background("back");
        assertEquals(b.getName(), "back");
        assertEquals(b.getSelfString(), "Unknown");
    }

    @Test public void testMiners()
    {
        Point p = new Point(1,1);
        MinerFull m = new MinerFull("m1",p, 5, 5, 5);
        MinerNotFull n = new MinerNotFull("m2", p, 3, 3, 3);

        assertEquals(m.getName(),"m1");
        assertEquals(m.getSelfString(), "Unknown");
        assertEquals(m.getPosition(), p);
        assertEquals(m.getAnimationRate(), 5);
        assertEquals(m.getRate(), 5);
        assertEquals(m.getResourceLimit(), 5);
        assertEquals(m.getResourceCount(), 0);

        assertEquals(n.getName(),"m2");
        assertEquals(n.getSelfString(), "minerm211333");
        assertEquals(n.getPosition(), p);
        assertEquals(n.getAnimationRate(), 3);
        assertEquals(n.getRate(), 3);
        assertEquals(n.getResourceLimit(), 3);
        assertEquals(n.getResourceCount(), 0);

    }

    @Test public void testObstacle()
    {
        Point p = new Point(1,1);
        Obstacle o = new Obstacle("obs",p);

        assertEquals(o.getName(), "obs");
        assertEquals(o.getPosition(),p);
        assertEquals(o.getSelfString(),"obstacleobs11");
    }

    @Test public void testOccupancyGrid()
    {
        Background b = new Background("back");
        OccupancyGrid o = new OccupancyGrid(2,2,b);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(o.getCell(new Point(j, i)), b);
            }
        }
        Point p = new Point(1,1);
        MinerFull m = new MinerFull("m1",p, 5, 5, 5);

        o.setCell(new Point(1, 1), m);
        assertEquals(o.getCell(p), m);

    }

    @Test public void testOre()
    {
        Point p = new Point(2,2);
        Ore o = new Ore("ore", p , 2);

        assertEquals(o.getSelfString(),"oreore222");
        assertEquals(o.getName(), "ore");
        assertEquals(o.getPosition(), p);
        assertEquals(o.getRate(),2);
    }

    @Test public void testOreBlob()
    {
        Point p = new Point(2,2);
        OreBlob o = new OreBlob("blob",p,1,2);

        assertEquals(o.getSelfString(),"Unknown");
        assertEquals(o.getName(), "blob");
        assertEquals(o.getPosition(), p);
        assertEquals(o.getRate(),1);
        assertEquals(o.getAnimationRate(),2);

    }

    @Test public void testPoint()
    {
        Point p = new Point(5,6);

        assertEquals(p.getX(), 5);
        assertEquals(p.getY(), 6);
    }

    @Test public void testQuake()
    {
        Point p = new Point(5,6);
        Quake q = new Quake("quake", p , 2);

        assertEquals(q.getAnimationRate(),2);
        assertEquals(q.getName(),"quake");
        assertEquals(q.getPosition(),p);
        assertEquals(q.getSelfString(), "Unknown");

    }

    @Test public  void testVein()
    {
        Point p = new Point(5,6);
        Vein v = new Vein("vein", p, 2,1);

        assertEquals(v.getSelfString(),"veinvein5621");
        assertEquals(v.getName(), "vein");
        assertEquals(v.getPosition(),p);
        assertEquals(v.getRate(),2);
        assertEquals(v.getResourceDistance(),1);
    }

    //world model tests from here
    //the following world will be used for all tests, and unless there are any mistakes it will be reset to it's
    //default state with the completion of every test

    private WorldModel world = new WorldModel(5,5, new Background("background"));

    //contains grid x,y|0-4

    //these test cases test several related functions in conjunction
    @Test public void testBoundsOccupied()
    {
        Point pt = new Point(1,1);
        assertTrue(world.withinBounds(pt));
        assertTrue(!world.isOccupied(pt));
    }

    @Test public void testNearestEntity()
    {

        //adds ore, tests nearest, moves it, tests again, removes it, and tests previous ore locations vacated
        world.addEntity(world.createOre("o1", new Point(1,2),2));
        world.addEntity(world.createOre("o2", new Point(0, 0), 2));
        Ore o3 = new Ore("o3", new Point(4, 4), 2);
        world.addEntity(o3);


        assertEquals(world.getTileOccupant(new Point(1, 2)).getName(), "o1");
        assertEquals(world.getTileOccupant(new Point(0, 0)).getName(), "o2");
        assertEquals(world.getTileOccupant(new Point(4, 4)), o3);

        assertEquals(world.findNearest(new Point(1, 1), new Ore("o4", new Point(4, 4), 2)),
                world.getTileOccupant(new Point(1, 2)));

        world.moveEntity(world.getTileOccupant(new Point(1, 2)), new Point(2, 1));
        assertEquals(world.findNearest(new Point(1, 1), new Ore("o4", new Point(4, 4), 2)),
                world.getTileOccupant(new Point(2, 1)));

        world.worldRemoveEntity(world.createOre("o1", new Point(2, 1), 2));
        world.worldRemoveEntity(world.createOre("o2", new Point(0, 0), 2));
        world.worldRemoveEntity(world.createOre("o3", new Point(4, 4), 2));

        assertEquals(world.getTileOccupant(new Point(2, 1)), null);
        assertEquals(world.getTileOccupant(new Point(0, 0)), null);
        assertEquals(world.getTileOccupant(new Point(4, 4)), null);

    }

    @Test public void testWorldBackground()
    {
        //sets bg to vein, checks it, removes, checks vacated
        Vein vn = world.createVein("vein", new Point(0,0),5);



        world.setBackground(new Point(0,0),vn);

        assertEquals(world.getBackground(vn.getPosition()), vn);

        world.setBackground(vn.getPosition(), null);

        assertEquals(world.getBackground(vn.getPosition()), null);

    }

    @Test public void testNextPosition()
    {

        //set up the obstacles to allow all if paths
        Ore o1 = new Ore("o1", new Point(2,2),1);
        Ore o2 = new Ore("o2", new Point(3,1),1);
        world.addEntity(o1);
        world.addEntity(o2);

        //create the first testee
        Miner m = new MinerFull("m", new Point(2,1),1,1,1);
        world.addEntity(m);

        //test all new position patterns
        assertEquals(world.nextPosition(m.getPosition(), new Point(1, 1)), new Point(1,1));
        assertEquals(world.nextPosition(m.getPosition(), new Point(4,1)), new Point(2,1));
        assertEquals(world.nextPosition(m.getPosition(), new Point(2,4)), new Point(2,1));
        assertEquals(world.nextPosition(m.getPosition(), new Point(2,0)), new Point(2,0));

        //move miner to act as additional ignorable object, ensure moved
        world.moveEntity(m, new Point(1,1));

        assertEquals(world.getTileOccupant(m.getPosition()), m);

        //create second testee




    }

}
