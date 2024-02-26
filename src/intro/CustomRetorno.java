package intro;

import banco.conta.ContaTipo;
import banco.model.usuario.Usuario;

public record CustomRetorno(Usuario usuario, ContaTipo contaTipo) {
}
