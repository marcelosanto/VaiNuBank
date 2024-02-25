package banco.loterias;

import java.util.Map;

public class LoteriasPremios {
    private static Map<LoteriasTipos, Double> premios = Map.of(LoteriasTipos.MEGA_SENA, 10_000_000.0, LoteriasTipos.QUINA, 5_000_000.0, LoteriasTipos.LOTOFACIL, 2_000_000.0, LoteriasTipos.TIMEMANIA, 1_000_000.0);

    public static Double loteriasPremios(LoteriasTipos loterias) {
        return premios.get(loterias);
    }

}
