package NFZ;

public class Discount {
    private float value = 100;
    private String describe = "without discount";

    private Discount() { }

    public Discount(float value, String description) {
        this.value = value;
        this.describe = description;
    }

    public float getValue() {
        return value;
    }

    public String getDescribe() {
        return describe;
    }
}
