package banco.model.usuario;

public record Usuario(
        Long id,
        String nome,
        String sobrenome,
        String cpf,
        String email,
        String senha) {
}
