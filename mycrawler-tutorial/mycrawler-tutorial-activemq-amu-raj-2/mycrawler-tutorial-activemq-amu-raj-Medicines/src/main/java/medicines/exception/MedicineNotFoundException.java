package medicines.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Dominik on 2015-01-28.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MedicineNotFoundException extends RuntimeException {
    public MedicineNotFoundException(){
        super("Nie ma takiego leku.");
    }
}
