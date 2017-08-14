package NFZ;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Medicine {
    private String name = "";
    private List<Discount> discounts = new ArrayList<Discount>();

    private Medicine() {}

    public Medicine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }
}
