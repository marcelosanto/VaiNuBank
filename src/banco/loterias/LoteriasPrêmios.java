package banco.loterias;

import java.util.Map;

public class LoteriasPrêmios {
    LoteriasTipos tiposLoterias;

    Map<LoteriasTipos, Integer> premios = Map.of(LoteriasTipos.MEGA_SENA, 10_000_000, LoteriasTipos.QUINA, 5_000_000, LoteriasTipos.LOTOFACIL, 2_000_000, LoteriasTipos.TIMEMANIA, 1_000_000);


    public int loteriasPremios(LoteriasTipos loterias) {
        return premios.get(loterias);
    }
}
