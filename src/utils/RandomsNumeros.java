package utils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomsNumeros {
    public static List<Integer> gerarNumerosAleatorios(int quantidade) {
        Random random = new Random();
        return IntStream.range(0, quantidade)
                .mapToObj(i -> random.nextInt())
                .collect(Collectors.toList());
    }
}
