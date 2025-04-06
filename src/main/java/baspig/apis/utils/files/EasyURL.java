package baspig.apis.utils.files;

import baspig.apis.utils.util.BP;
import baspig.apis.utils.util.ReturnType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Is an easier way to get data from a URL.
 * @Author Baspig
 */
class EasyURL {

    private String URLi;

    /**
     * Creates an manageable URL
     * @param URL The URL in a String value
     */
    private EasyURL(String URL){
        this.URLi = URL;
    }

    /**
     * @return The URL in a String
     */
    public String getURL() {
        return URLi;
    }

    /**
     * Replaces the given URL
     */
    public void setURL(String URL) {
        this.URLi = URL;
    }

    /**
     * It is a fast way to check raw text content.
     *
     * @return A String List, each index is a line of the raw document
     */
    public List<String> getRawTextContent(){
        /// Usage of try catch block
        try {
            /// Creation of the list and call to the URL at the same line
            return new BufferedReader(new InputStreamReader(new URI(URLi).toURL().openStream())).lines().toList();
            /// Return the indexed String value
        } catch (URISyntaxException | IOException e) {
            BP.fancyReturnLog("getRawTextContent",this.getClass(), e , ReturnType.Null);
        }
        return null;
    }

    /**
     * It is a fast way to specific check raw text content.
     *
     * @param index The index to search into the file
     * @return A String of the specified line index.
     */
    public String getRawTextContent(int index){
        /// Usage of try catch block
        try {
            /// Creation of the list and call to the URL at the same line
            List<String> content = new BufferedReader(new InputStreamReader(new URI(URLi).toURL().openStream())).lines().toList();
            /// Return the indexed String value
            return FileUtils.indexCheck(content, index) ? content.get(index) : content.getLast();
        } catch (URISyntaxException | IOException e) {

            //exception handler and fancy log
            BP.fancyReturnLog(BP.methodColor + "getRawTextContent(int)",this.getClass(), e , ReturnType.Null);
        }
        ///return null as an error in the execution.
        return null;
    }
}
