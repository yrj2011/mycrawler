package clinic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AL on 2015-01-28.
 */
public class Prescription {

    public class Record {
        private String name;
        private float discount;

        private Record() {}

        public Record(String name, float discount) {
            this.name = name;
            this.discount = discount;
        }

        public String getName() {
            return name;
        }

        public float getDiscount() {
            return getDiscount();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (getClass() != o.getClass()) return false;
            Prescription.Record r = (Prescription.Record) o;
            return (this.getName().equals(r.getName()) &&
                        this.getDiscount() == r.getDiscount());
        }

    }

    public List<Prescription.Record> medicines = new ArrayList<Prescription.Record>();
    public String pesel;

    public Prescription(String pesel) {
        this.pesel = pesel;
    }

    public void addMedicine(String name, Float discount) {
        medicines.add(new Record(name, discount));
    }

    public void removeMedicine(String name, Float discount) {
        medicines.remove(new Record(name, discount));
    }

}
