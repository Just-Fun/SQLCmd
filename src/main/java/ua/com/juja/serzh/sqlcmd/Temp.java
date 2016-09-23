package ua.com.juja.serzh.sqlcmd;

/**
 * Created by Serzh on 9/12/16.
 */
public class Temp {

    private int i = 0;
    private String s = "";

    public static void main(String[] args) {
        System.out.println("Hi!");
    }

    private static void some() {
    }

    private void some(int i) {
    }

    private static void some(String i) {
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != getClass()) return false;
        Temp temp = (Temp) o;
        if (i != temp.i) return false;
        return s.equals(temp.s);
    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + s.hashCode();
        return result;
    }
}
