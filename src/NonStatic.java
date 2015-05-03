public class NonStatic extends Entity
{
    private Point position;

    public NonStatic(String name, Point Position){
        super(name);
        this.position = Position;
    }

    public void setPosition(Point point)
    {
        position = point;
    }

    public Point getPosition()
    {
        return position;
    }
}
