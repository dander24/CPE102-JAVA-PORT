public class Quake extends NonStatic
{
    private int animationRate;

    public Quake(String name, Point position, int AnimationRate)
    {
        super(name,position);
        animationRate = AnimationRate;
    }

    public int getAnimationRate()
    {
        return animationRate;
    }
}
