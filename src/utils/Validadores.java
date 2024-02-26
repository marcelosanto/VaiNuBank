package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validadores {
    public static boolean validarEmail(String email) {
        // Define a expressão regular para validar o e-mail
        String regex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

        // Compila a expressão regular em um padrão
        Pattern pattern = Pattern.compile(regex);

        // Cria um objeto Matcher associado à sequência de entrada
        Matcher matcher = pattern.matcher(email);

        // Verifica se o e-mail corresponde ao padrão
        return matcher.matches();
    }
}
