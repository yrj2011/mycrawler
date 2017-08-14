package medicines;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2015-01-27.
 */

public class Medicine{

    private String name;
    private List<Discount> discounts;

    public Medicine(){}
    public Medicine(String name){
        this.name = name;
        discounts = new ArrayList<Discount>();
    }

    public void addDiscount(Discount discount){
        discounts.add(discount);
    }

    public List<Discount> getDiscounts(){
        return discounts;
    }

    public String getName(){
        return name;
    }

    public void setDiscounts(List<Discount> discounts){
        this.discounts = discounts;
    }

    public void setName(String name){
        this.name = name;
    }



    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        Medicine med = (Medicine) obj;
        return name.equals(med.name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Medicine[");
        sb.append("name=").append(name);
        return sb.toString();
    }
}
