package medicines;

import medicines.exception.MedicineAlreadyExistsException;
import medicines.exception.MedicineNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Created by Dominik on 2015-01-27.
 */

@RestController
@RequestMapping(value = "/medicines")
public class MedicineController {
    private final MedicinesRepository repo = MedicinesRepository.getMedicinesRepository();

    @RequestMapping(method = RequestMethod.GET)
    public List<Medicine> getAllMedicines(){
        return repo.getAllMedicines();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void putMedicine(@RequestBody Medicine med){
        boolean successful = repo.addMedicine(med);
        if(!successful) throw new MedicineAlreadyExistsException();
    }

    @RequestMapping(value="/{name}", method = RequestMethod.DELETE)
    public void removeMedicine(@PathVariable String name){
        boolean successful = repo.removeMedicine(name);
        if(!successful) throw new MedicineNotFoundException();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Medicine getMedicine(@PathVariable String name){
        Medicine med = repo.getMedicine(name);
        if(med == null){
            throw new MedicineNotFoundException();
        }
        else  return med;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public void replaceDiscountsList(@PathVariable String name, @RequestBody List<Discount> discounts){
        Medicine med = repo.getMedicine(name);
        if(med != null){
            med.setDiscounts(discounts);
        }
        else{
            throw new MedicineNotFoundException();
        }
    }
}
