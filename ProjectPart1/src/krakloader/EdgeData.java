package krakloader;

/**
 * Represents the raw data from a line in kdv_unload.txt.
 * It has been modified by group A.
 */
public class EdgeData {

    public final int FNODE;
    public final int TNODE;
    public final double LENGTH;
    public final int TYP; //Final?
    public final String VEJNAVN; //Final?
    public final int FROMLEFT;
    public final int TOLEFT;
    public final int FROMRIGHT;
    public final int TORIGHT;
    public final int V_POSTNR;
    public final int H_POSTNR;
    public final String FROMLEFT_BOGSTAV;
    public final String TOLEFT_BOGSTAV;
    public final String FROMRIGHT_BOGSTAV;
    public final String TORIGHT_BOGSTAV;
    public final int FRAKOERSEL;
    public final int SPEED;
    public final double DRIVETIME;
    public final String ONE_WAY;
    public final String F_TURN;
    public final String T_TURN;

    @Override
    public String toString() {
        return FNODE + ","
                + TNODE + ","
                + String.format("%.5f,", LENGTH)
                + TYP + ","
                + "'" + VEJNAVN + "',"
                + FROMLEFT + ","
                + TOLEFT + ","
                + FROMRIGHT + ","
                + TORIGHT + ","
                + "'" + FROMLEFT_BOGSTAV + "',"
                + "'" + TOLEFT_BOGSTAV + "',"
                + "'" + FROMRIGHT_BOGSTAV + "',"
                + "'" + TORIGHT_BOGSTAV + "',"
                + FRAKOERSEL + ","
                + SPEED + ","
                + String.format("%.3f,", DRIVETIME)
                + "'" + ONE_WAY + "',"
                + "'" + F_TURN + "',"
                + "'" + T_TURN + "',";
    }
    
    /**
     * Overloaded constructor for use with KrakLoader and DataLine.
     * @param line 
     */
    public EdgeData(String line) {
        DataLine dl = new DataLine(line);
        FNODE = dl.getInt();
        TNODE = dl.getInt();
        LENGTH = dl.getDouble();
        int DAV_DK = dl.getInt();
        int DAV_DK_ID = dl.getInt();
        TYP = dl.getInt();
        VEJNAVN = dl.getString();
        FROMLEFT = dl.getInt();
        TOLEFT = dl.getInt();
        FROMRIGHT = dl.getInt();
        TORIGHT = dl.getInt();
        FROMLEFT_BOGSTAV = dl.getString();
        TOLEFT_BOGSTAV = dl.getString();
        FROMRIGHT_BOGSTAV = dl.getString();
        TORIGHT_BOGSTAV = dl.getString();
        int V_SOGNENR = dl.getInt();
        int H_SOGNENR = dl.getInt();
        V_POSTNR = dl.getInt();
        H_POSTNR = dl.getInt();
        int KOMMUNENR = dl.getInt();
        int VEJKODE = dl.getInt();
        int SUBNET = dl.getInt();
        String RUTENR = dl.getString();
        FRAKOERSEL = dl.getInt();
        int ZONE = dl.getInt();
        SPEED = dl.getInt();
        DRIVETIME = dl.getDouble();
        ONE_WAY = dl.getString();
        F_TURN = dl.getString();
        T_TURN = dl.getString();
        int VEJNR = dl.getInt();
        String AENDR_DATO = dl.getString();
        int TJEK_ID = dl.getInt();
    }
    
    /**
     * Overloaded constructor to use with LoadCoast (for coastline data).
     */
    public EdgeData() {
        FNODE = 0;
        TNODE = 0;
        LENGTH = 0;
        TYP = 48; //48 represents coastlines.
        VEJNAVN = "";
        FROMLEFT = 0;
        TOLEFT = 0;
        FROMRIGHT = 0;
        TORIGHT = 0;
        FROMLEFT_BOGSTAV = "";
        TOLEFT_BOGSTAV = "";
        FROMRIGHT_BOGSTAV = "";
        TORIGHT_BOGSTAV = "";
        V_POSTNR = 0;
        H_POSTNR = 0;
        FRAKOERSEL = 0;
        SPEED = 0;
        DRIVETIME = 0;
        ONE_WAY = "";
        F_TURN = "";
        T_TURN = "";
    }
    
    /**
     * Overloaded constructor for OSMParser, taking data as multiple parameters
     * instead of an dataline.
     * @param refOne
     * @param refTwo
     * @param name
     * @param typ
     * @param speed
     * @param dTime
     * @param length 
     */
    public EdgeData(int refOne, int refTwo, String name, int typ, int speed, double dTime, double length) {
        FNODE = refOne;
        TNODE = refTwo;
        LENGTH = length;
        TYP = typ;
        VEJNAVN = name;
        FROMLEFT = 0;
        TOLEFT = 0;
        FROMRIGHT = 0;
        TORIGHT = 0;
        FROMLEFT_BOGSTAV = "";
        TOLEFT_BOGSTAV = "";
        FROMRIGHT_BOGSTAV = "";
        TORIGHT_BOGSTAV = "";
        V_POSTNR = 0;
        H_POSTNR = 0;
        FRAKOERSEL = 0;
        SPEED = speed;
        DRIVETIME = dTime;
        ONE_WAY = "";
        F_TURN = "";
        T_TURN = "";
    }
    
    /**
     * This constructor is used for testing only. Used in RouteJUnit.
     * @param FN
     * @param TN
     * @param length
     * @param dTime
     * @param way 
     */
    public EdgeData(int FN, int TN, double length, double dTime, String way) {
        FNODE = FN;
        TNODE = TN;
        LENGTH = length;
        TYP = 0;
        VEJNAVN = "test";
        FROMLEFT = 0;
        TOLEFT = 0;
        FROMRIGHT = 0;
        TORIGHT = 0;
        FROMLEFT_BOGSTAV = "";
        TOLEFT_BOGSTAV = "";
        FROMRIGHT_BOGSTAV = "";
        TORIGHT_BOGSTAV = "";
        V_POSTNR = 0;
        H_POSTNR = 0;
        FRAKOERSEL = 0;
        SPEED = 10;
        DRIVETIME = dTime;
        ONE_WAY = way;
        F_TURN = "";
        T_TURN = "";
    }
}
