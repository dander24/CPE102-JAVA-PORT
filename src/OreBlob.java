public class OreBlob extends Actor
{
    private int animationRate;

    public OreBlob(String name, Point position, int rate, int AnimationRate)
    {
        super(name,position,rate);
        animationRate = AnimationRate;
    }

    public int getAnimationRate()
    {
        return animationRate;
    }
}
