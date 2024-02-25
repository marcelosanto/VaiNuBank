package banco.loterias;

public enum LoteriasTipos {
    MEGA_SENA(6),
    QUINA(5),
    LOTOFACIL(15), //Você marca entre 15 e 20 números, dentre os 25 disponíveis no volante, e fatura prêmio se acertar 11, 12, 13, 14 ou 15 números
    TIMEMANIA(7); //numeros sorteados 7 e um time do coração

    private final int numero;

    LoteriasTipos(int numero) {
        this.numero = numero;
    }

    public int getNumero(int numero) {
        return numero;
    }

    public int getNumero() {
        return numero;
    }
}
