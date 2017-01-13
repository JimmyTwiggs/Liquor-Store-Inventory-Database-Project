import java.util.Vector;

public class Utility {
	public static Object[][] to2DimArray(Vector v) {  
        Object[][] out = new Object[v.size()][0];  
        for (int i = 0; i < out.length; i++) {  
            out[i] = ((Vector) v.get(i)).toArray();  
        }  
        return out;  
    }     
}
