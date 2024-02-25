package utils;

import banco.loterias.LoteriasTipos;

import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomsNumeros {

    public static List<Integer> gerarNumerosAleatorios(int quantidade) {
        Random random = new Random();
        return IntStream.range(0, quantidade)
                .mapToObj(i -> random.nextInt())
                .collect(Collectors.toList());
    }

    public static TreeSet<Integer> gerarNumerosAleatoriosTreeSet(LoteriasTipos loteria) {
        Random random = new Random();
        TreeSet<Integer> treeSet = new TreeSet<>();
        int quantidade = loteria.getNumero();

        int quantidadeDeNumeros = switch (loteria) {
            case MEGA_SENA -> 60;
            case QUINA, TIMEMANIA -> 80;
            case LOTOFACIL -> 25;
        };

        //megasena é 60 numeros no cartao - 6 sorteados
        //quina é 80 numeros no cartao - 5 sorteados
        //lotofacil é 25 numeros no cartao - 15 sorteados
        //timemania é 80 numeros no cartao - 10 sorteados

        do {
            treeSet.add(random.nextInt(1, quantidadeDeNumeros));
        } while (treeSet.size() != quantidade);

        return treeSet;
    }


}
