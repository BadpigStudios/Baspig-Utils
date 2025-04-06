package baspig.apis.utils;

/**
 * It's just a fast way to fix internal class calling
 */
@SuppressWarnings("unused")
public class ModID {
    private Object modId;

    protected ModID(Object modId){
        this.modId = modId;
    }

    protected Object get(){
        return modId;
    }

    protected void set(Object modId){
        this.modId = modId;
    }
}
