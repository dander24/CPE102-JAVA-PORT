public class MinerNotFull extends Miner
{
    public MinerNotFull(String name, Point position, int rate, int resourceLimit, int AnimationRate)
    {
        super(name, position, rate, resourceLimit, AnimationRate);
    }

    public String getSelfString()
    {
        return "miner" + getName() + Integer.toString(getPosition().getX()) + Integer.toString(getPosition().getY())
                + Integer.toString(getResourceLimit()) + Integer.toString(getRate()) + Integer.toString(getAnimationRate());
    }
}
