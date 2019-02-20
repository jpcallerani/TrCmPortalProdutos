package br.tr.com.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    
    /**
     * Formata o campo de data.
     * @param p_date
     * @return 
     */
    public static String formataData(Date p_date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(p_date);
    }
}
