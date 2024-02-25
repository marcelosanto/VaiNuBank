package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Times {
    private final List<String> maioresTimes = new ArrayList<>(Arrays.asList("Flamengo", "Palmeiras", "Santos", "São Paulo", "Corinthians", "Cruzeiro", "Grêmio", "Internacional", "Fluminense", "Botafogo", "Vasco"));

    public List<String> getMaioresTimes() {
        return maioresTimes;
    }

    public String randomTimes() {
        return this.maioresTimes.get(new Random().nextInt(this.maioresTimes.size()));
    }
}
