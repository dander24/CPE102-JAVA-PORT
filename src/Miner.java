public class Miner extends Actor
{
    private int resourceCount, resourceLimit, animationRate;

    public Miner(String name, Point position, int rate, int resourceLimitt, int AnimationRate)
    {
        super(name,position,rate);
        resourceCount = 0;
        resourceLimit = resourceLimit;
        animationRate = AnimationRate;
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
