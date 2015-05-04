public class Miner extends Actor
{
    private int resourceCount, resourceLimit, animationRate;

    public Miner(String name, Point position, int rate, int ResourceLimit, int AnimationRate)
    {
        super(name,position,rate);
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
