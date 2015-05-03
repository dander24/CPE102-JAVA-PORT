public class Obstacle extends NonStatic
{
    public  Obstacle(String name, Point position)
    {
        super(name, position);
    }

    public String getSelfString()
    {
        return "obstacle" + getName() + Integer.toString(getPosition().getX()) + Integer.toString(getPosition().getY());
    }
}
