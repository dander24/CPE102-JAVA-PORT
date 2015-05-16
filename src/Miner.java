import processing.core.PImage;
import java.util.List;

public class Miner extends Actor
{
    private int resourceCount, resourceLimit, animationRate;

    public Miner(String name, Point position, int rate, int ResourceLimit, int AnimationRate, List<PImage> pImages)
    {
        super(name,position,rate,pImages);
        resourceCount = 0;
        resourceLimit = ResourceLimit;
        animationRate = AnimationRate;
    }

    public void setResourceCount(int i){
        resourceCount = i;
    }

    public int getResourceCount()
    {
        return resourceCount;
    }

    public int getResourceLimit()
    {
        return resourceLimit;
    }

    public int getAnimationRate()
    {
        return animationRate;
    }
}
