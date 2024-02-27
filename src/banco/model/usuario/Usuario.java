package banco.model.usuario;

public record Usuario(
        Long id,
        String nome,
        String sobrenome,
        int cpf,
        String email,
        String senha) {

    @Override
    public String toString() {
        return "Usuario { " + id +
                ", '" + nome + '\'' +
                ", '" + sobrenome + '\'' +
                ", " + cpf +
                ", '" + email + '\'' +
                ", '" + senha + '\'' +
                '}';
    }
}
