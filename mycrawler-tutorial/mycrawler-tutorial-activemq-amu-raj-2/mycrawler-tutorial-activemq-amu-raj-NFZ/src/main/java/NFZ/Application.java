package NFZ;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import NFZ.Medicine;
import NFZ.Discount;

public class Application {

    private static RestTemplate restTemplate = new RestTemplate();


    private static void showAll(boolean showDiscounts) {
        Medicine[] meds = restTemplate.getForObject("http://localhost:8080/medicines", Medicine[].class);

        int i = 0;

        for(Medicine m : meds) {

            System.out.format("Lek: %d - %s\n", i++, m.getName());
            
            if (showDiscounts) {
                List<Discount> discounts = m.getDiscounts();
                for(Discount d : discounts) {
                    System.out.format("  Zniżka: %g - %s\n", d.getValue(), d.getDescribe());
                }
            }
        }
    }

    private static void add(Medicine medicine) {
        try {
            restTemplate.postForLocation("http://localhost:8080/medicines", medicine);
        } catch(org.springframework.web.client.HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.CONFLICT) {
                System.out.format("Błąd: lek o takiej nazwie już istnieje.\n");
            } else {
                throw ex;
            }
        }
    }

    private static void remove(int i) {
        Medicine[] meds = restTemplate.getForObject("http://localhost:8080/medicines", Medicine[].class);
        String name = meds[i].getName();
        restTemplate.delete("http://localhost:8080/medicines/{medicine}", name);
    }

    private static void change(int i, List<Discount> discounts) {
        Medicine[] meds = restTemplate.getForObject("http://localhost:8080/medicines", Medicine[].class);
        String name = meds[i].getName();
        try {
            restTemplate.put("http://localhost:8080/medicines/{medicine}", discounts, name);
        } catch(org.springframework.web.client.HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                System.out.format("Błąd: podany lek nie istnieje.\n");
            } else {
                throw ex;
            }
        }
    }

    private static void showInterface() {
        System.out.format(
            "Akcje:\n" +
            "  0 - wyświetl wszystko\n" +
            "  1 - dodaj lek\n" +
            "  2 - usuń lek\n" +
            "  3 - zmodyfikuj zniżki\n" +
            "  4 - wyjdź\n\n" +
            "Akcja: ");
    }

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);

        showAll(true);
        
        boolean run = true;
MAIN:
        while (run) {
            showInterface();
            switch(in.nextInt()) {
                case 0:
                    showAll(true);
                    break;
                case 1:
                    System.out.format("Nazwa leku: ");
                    String name = in.next();
                    Medicine medicine = new Medicine(name);
                    add(medicine);
                    break;
                case 2:
                    showAll(false);
                    System.out.format("\nNumer leku do usunięcia: ");
                    remove(in.nextInt());
                    break;
                case 3:
                    showAll(false);
                    System.out.format("\nNumer leku do modyfikacji: ");
                    int i = in.nextInt();
                    List<Discount> discounts = new ArrayList<Discount>();
DISCOUNTS:
                    while (true) {
                        System.out.format("\nWartość zniżki (lub -1 aby zakończyć edycję): ");
                        float value = in.nextFloat();
                        if (value == -1f) {
                            break DISCOUNTS;
                        }
                        System.out.format("\nOpis zniżki: ");
                        String description = in.next();
                        discounts.add(new Discount(value, description));
                    }
                    change(i, discounts);
                    break;
                case 4:
                    break MAIN;
            }
        }
    }
}
