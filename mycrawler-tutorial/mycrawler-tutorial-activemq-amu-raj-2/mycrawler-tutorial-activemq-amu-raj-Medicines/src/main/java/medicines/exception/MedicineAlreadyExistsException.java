package medicines.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Dominik on 2015-01-28.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class MedicineAlreadyExistsException extends RuntimeException{
    public MedicineAlreadyExistsException(){
        super("Lek o takiej nazwie już istnieje.");
    }
}
