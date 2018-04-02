package newlang3;

/**
 *
 * @author c0115058
 */

public class ValueImpl implements Value{
    String s;
    ValueType type;

    public ValueImpl(String s_v) {
        s = s_v;
        type = ValueType.STRING;
    }
     
    public ValueImpl(int i_v) {
        s = String.valueOf(i_v);
        type = ValueType.INTEGER;
    }
    public ValueImpl(double d_v) {
        s = String.valueOf(d_v);
        type = ValueType.DOUBLE;
    }

    public ValueImpl(boolean b_v) {
        s = String.valueOf(b_v);
        type = ValueType.BOOL;
    }
    
    @Override
    public String getSValue() {
        return s;
    }

    @Override
    public int getIValue() {
        return Integer.parseInt(s);
    }

    @Override
    public double getDValue() {
        return Double.parseDouble(s);
    }

    @Override
    public boolean getBValue() {
        return Boolean.valueOf(s);
    }

    @Override
    public ValueType getType() {
        return type; 
    }
    @Override
    public String toString() {
        return getSValue(); 
    }
}