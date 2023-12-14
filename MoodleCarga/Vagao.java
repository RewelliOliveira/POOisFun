package MoodleCarga;

import java.util.List;

interface Vagao {
    List<Pass> getElementos();

    void embarcar(Pass pass) throws MsgException;

    void desembarcar(String idPass);

    boolean exists(Pass pass);
}
