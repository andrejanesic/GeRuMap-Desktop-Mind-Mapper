package rs.edu.raf.dsw.rudok.app.repository;

public class Connection extends Element{

    private Topic from,to;

    public Connection(String nodeName,int color,Topic from,Topic to) {
        super(nodeName,color);
        this.from = from;
        this.to = to;
    }

    public Topic getFrom() {
        return from;
    }

    public Topic getTo() {
        return to;
    }
}
