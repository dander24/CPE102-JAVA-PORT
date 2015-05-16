import processing.core.PImage;
import java.util.List;

public class Quake extends NonStatic
{
    private int animationRate;

    public Quake(String name, Point position, int AnimationRate, List<PImage> pImages)
    {
        super(name,position,pImages);
        animationRate = AnimationRate;
    }

    public int getAnimationRate()
    {
        return animationRate;
    }
}
