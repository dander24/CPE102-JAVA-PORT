public class AStarPoint extends Point{
    private double f,g,h;
    private Point cameFrom;
    public AStarPoint(int x, int y, Point cameFrom)
    {
        super(x,y);
        f = 0;
        g = 0;
        h = 0;
        this.cameFrom = cameFrom;
    }

    public void setF(double f) {
        this.f = f;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public Point getCameFrom() {
        return cameFrom;
    }
}
