package MyPlanner.utils;

import MyPlanner.interfaces.AngularjsPrintable;

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
    public static String printArray(AngularjsPrintable[] array, String arrayName){
        String result = arrayName + "=[";
        for(int i = 0; i < array.length; i++){
            if(i < array.length -1)
                result += array[i].angularPrint() + ",";
            else
                result += array[i].angularPrint();
        }
        result += "]";

        return result;
    }

}
