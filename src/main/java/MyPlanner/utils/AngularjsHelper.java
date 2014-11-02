package MyPlanner.utils;

/**
 * Created by TomKolse on 02-Nov-14.
 */
public class AngularjsHelper {
    public static String printArray(Object[] array, String arrayName){
        String result = arrayName + "=[";
        for(int i = 0; i < array.length; i++){
            if(i < array.length -1)
                result += array[i].toString() + ",";
            else
                result += array[i].toString();
        }
        result += "]";

        return result;
    }
}
