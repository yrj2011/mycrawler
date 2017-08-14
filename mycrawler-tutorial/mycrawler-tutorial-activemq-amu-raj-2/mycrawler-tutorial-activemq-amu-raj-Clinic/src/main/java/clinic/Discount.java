package clinic;

import java.io.Serializable;

/**
 * Created by Dominik on 2015-01-27.
 */
public class Discount{
    private float value;
    private String describe;

    public Discount(){
        value = 100;
        describe = "without discount";
    }

    public Discount(float value, String describe){
        this.value = value;
        this.describe = describe;
    }

    public float getValue(){
        return value;
    }
    public String getDescribe(){
        return describe;
    }
    public void setValue(float value){
        this.value = value;
    }
    public void setDescribe(String describe){
        this.describe = describe;
    }
}
