package semyungai.web.config.err;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import java.util.List;

//@Component
public class BindingResultErr {
    public static ResponseEntity<?> getBindingResultErr(BindingResult bindingResult){
        List<?> err = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity.badRequest().body(err);
    }

}
