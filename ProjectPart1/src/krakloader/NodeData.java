package krakloader;

/**
 * An object storing the raw node data from the krak data file. Changed by group
 * A, check method recalc().
 */
public class NodeData {
    
    final int KDV;
    private double X_COORD;
    private double Y_COORD;
    

    /**
     * Parses node data from line, throws an IOException if something unexpected
     * is read
     *
     * @param line The source line from which the NodeData fields are parsed
     */
    public NodeData(String line) {
        DataLine dl = new DataLine(line);
        int ARC = dl.getInt();
        KDV = dl.getInt();
        int KDV_ID = dl.getInt();
        X_COORD = dl.getDouble();
        Y_COORD = dl.getDouble();
    }

    /**
     * Overloaded constructor for use with coastline data which does not have
     * krak information.
     *
     * @param line
     * @param overload
     */
    public NodeData(String line, String overload) {
        DataLine dl = new DataLine(line);
        KDV = 0;
        X_COORD = dl.getDouble();
        Y_COORD = dl.getDouble();
    }

    public NodeData(int id, double x, double y) {
        KDV = id;
        X_COORD = x;
        Y_COORD = y;
    }

    /**
     * Returns a string representing the node data in the same format as used in
     * the kdv_node_unload.txt file.
     */
    @Override
    public String toString() {
        return KDV + "," + X_COORD + "," + Y_COORD;
    }

    /**
     * Added method by group A, to recalculate the coordinates of a node, to fit
     * with the coordinates used in Java.
     *
     * @param ymax
     * @param xmin
     */
    public void recalc(double ymax, double xmin) {
        X_COORD = X_COORD - xmin;
        Y_COORD = (-Y_COORD) + ymax;
    }

    public void recalcCoast(double ymax, double xmin) {
        X_COORD = X_COORD - xmin;
        Y_COORD = (-Y_COORD) + ymax;
    }

    public double getX_COORD() {
        return X_COORD;
    }

    public double getY_COORD() {
        return Y_COORD;
    }

    public int getKDV() {
        return KDV;
    }
    

}
