package au.com.javacloud.model;

/**
 * Created by david on 22/05/16.
 */

public abstract class BaseBean {
    protected int id;
    protected String displayValue;

    @Override
    public String toString() {
        return getClass().getSimpleName()+"["+id+"] displayValue="+displayValue;
    }

    public String getNameColumn() {
    	return "id";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

}
