package banco.loterias;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LoteriasRegras {
    Map<LoteriasTipos, List<Integer>> qtdJogadas = Map.of(LoteriasTipos.MEGA_SENA, Arrays.asList(6, 20), LoteriasTipos.QUINA, Arrays.asList(5, 15), LoteriasTipos.LOTOFACIL, Arrays.asList(15, 18), LoteriasTipos.TIMEMANIA, List.of(10));


    Map<LoteriasTipos, Double> valorDasApostas = Map.of(LoteriasTipos.MEGA_SENA, 7.50, LoteriasTipos.QUINA, 5.55, LoteriasTipos.LOTOFACIL, 4.33, LoteriasTipos.TIMEMANIA, 2.50);

    public List<Integer> getQtdJogadas(LoteriasTipos loterias) {
        return qtdJogadas.get(loterias);
    }

    public Double getValorDasApostas(LoteriasTipos loterias) {
        return valorDasApostas.get(loterias);
    }
}
