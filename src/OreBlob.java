import processing.core.PImage;

import java.util.List;

public class OreBlob extends Actor
{
    private int animationRate;

    public OreBlob(String name, Point position, int rate, int AnimationRate, List<PImage> pImages)
    {
        super(name,position,rate, pImages);
        animationRate = AnimationRate;
    }

    public int getAnimationRate()
    {
        return animationRate;
    }
}
