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
}
