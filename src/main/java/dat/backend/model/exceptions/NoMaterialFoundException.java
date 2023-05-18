package dat.backend.model.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NoMaterialFoundException extends Exception{

    public NoMaterialFoundException(String message){
        super(message);
        Logger.getLogger("web").log(Level.SEVERE, message);
    }

}
