package MyPlanner.interfaces;

/**
 * Created by TomKolse on 03-Nov-14.
 */
public interface AngularjsPrintable {

    /**
     * Method used in AngularjsHelper.
     *
     * @return Should return object in this format:
     * {varName:'varValue',varName:'varValue'}
     *
     * example:
     *
     * @Override
     * public String angularPrint() {
     *      return "{id:'"+id+"',name:'"+name+"',courseCode:'"+courseCode+"'}";
     * }
     */
    public String angularPrint();

}
