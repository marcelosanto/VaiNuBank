package intro;

import banco.conta.ContaTipo;
import usuario.Usuario;

public record CustomRetorno(Usuario usuario, ContaTipo contaTipo) {
}
