package MyPlanner.utils;

/**
 * Util class.
 * Helps make it easy to use both spring and angularjs
 */
public class AngularjsHelper {
    /**
     * Mehtod for printing out a angularjs array. Used with ng-init
     *
     * @param array - the array to use with ng-init
     * @param arrayName - the array name to be used in ng-init
     * @return - ng-init array
     */
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
