package banco.loterias;

import utils.RandomsNumeros;
import utils.Times;

import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

public class Loterias {
    LoteriasTipos loteriasEnum;

    private static Integer numerosDeAcertos(List<Integer> numerosJogados, TreeSet<Integer> numerosSorteados) {
        int acertos = 0;

        for (Integer numeroJogado : numerosJogados) {
            if (numerosSorteados.contains(numeroJogado)) {
                acertos++;
            }
        }

        System.out.println("Numeros de acertos: " + acertos);

        return acertos;
    }

    private static int resultadoDaLoteria(List<Integer> numerosJogados, TreeSet<Integer> numerosSorteados, LoteriasTipos tipoLoteria) {
        System.out.print("Numeros sorteados: ");
        numerosSorteados.forEach(n -> System.out.print(n + " "));

        System.out.println();

        System.out.print("Numeros jogados: ");
        numerosJogados.forEach(j -> System.out.print(j + " "));
        System.out.println();

        int acertos = numerosDeAcertos(numerosJogados, numerosSorteados);

        if (tipoLoteria.getNumero() <= acertos) {
            System.out.println("Parabens, voce acertou todos os numeros");
            return 1;
        }

        return 0;
    }

    public Double valorDaAposta(int numerosDeAposta, LoteriasTipos tipos) {
        LoteriasRegras regras = new LoteriasRegras();

        if (numerosDeAposta == regras.qtdJogadas.get(tipos).getFirst()) {
            return regras.getValorDasApostas(tipos);
        }

        int aposta = numerosDeAposta - regras.qtdJogadas.get(tipos).getFirst();
        return (aposta * 4) * regras.getValorDasApostas(tipos);
    }

    public Double realizarSorteio(LoteriasTipos loterias, List<Integer> numerosJogados, String times) {
        int resultado = 0;
        int resultadoDosTimes = 0;
        TreeSet<Integer> randoms;
        double valorPremio = 0.0;

        switch (loterias) {
            case MEGA_SENA:
                randoms = RandomsNumeros.gerarNumerosAleatoriosTreeSet(LoteriasTipos.MEGA_SENA);
                resultado = resultadoDaLoteria(numerosJogados, randoms, LoteriasTipos.MEGA_SENA);
                break;
            case QUINA:
                loteriasEnum = LoteriasTipos.QUINA;
                randoms = RandomsNumeros.gerarNumerosAleatoriosTreeSet(LoteriasTipos.QUINA);
                resultado = resultadoDaLoteria(numerosJogados, randoms, LoteriasTipos.QUINA);
                break;
            case LOTOFACIL:
                loteriasEnum = LoteriasTipos.LOTOFACIL;
                randoms = RandomsNumeros.gerarNumerosAleatoriosTreeSet(LoteriasTipos.LOTOFACIL);
                resultado = resultadoDaLoteria(numerosJogados, randoms, LoteriasTipos.LOTOFACIL);
                break;
            case TIMEMANIA:
                loteriasEnum = LoteriasTipos.TIMEMANIA;
                randoms = RandomsNumeros.gerarNumerosAleatoriosTreeSet(LoteriasTipos.TIMEMANIA);
                resultado = resultadoDaLoteria(numerosJogados, randoms, LoteriasTipos.TIMEMANIA);
                resultadoDosTimes = resultadoDosTimes(times, new Times().randomTimes());
                break;
        }

        // vem como int 0 - Não ganhou, 1 - Ganhador, 2 - Ganhador em Time + Aposta

        if (resultado > 0) {
            valorPremio = LoteriasPremios.loteriasPremios(loterias);
            if (loterias == LoteriasTipos.TIMEMANIA && resultadoDosTimes == 0) {
                valorPremio = valorPremio / 2;
            }
        }

        return valorPremio;
    }


    private int resultadoDosTimes(String times, String s) {
        if (Objects.equals(times, s)) {
            System.out.println("Parabens, voce acertou o Time do coração");
            return 1;
        }
        return 0;
    }
}
