package rs.edu.raf.dsw.rudok.app.repository;

public class Topic extends Element{

    private int size;
    private int x,y;

    public Topic(String nodeName,int color,int size,int x,int y) {
        super(nodeName,color);
        this.size = size;
        this.x = x;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
