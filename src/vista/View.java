package vista;

import controlador.Controller;


public interface View <C extends Controller>{
    C getController();
    void setController(C controller);

    void dataModelChanged();
    void display();

}
