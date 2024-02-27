package banco.db;

import banco.agencia.Agencia;
import banco.conta.Conta;
import banco.conta.ContaOperacoes;
import banco.conta.ContaTipo;
import banco.conta.Operacoes;
import banco.model.usuario.Usuario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DbCsvFile {
    private static String filePath = System.getProperty("user.dir") + "/" + "banco.csv";

    public static String convertToCsv(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder csvBuilder = new StringBuilder();


        for (Field field : fields) {
            csvBuilder.append(field.getName()).append(",");
        }
        csvBuilder.deleteCharAt(csvBuilder.length() - 1);
        csvBuilder.append("\n");


        for (Field field : fields) {
            field.setAccessible(true);
            try {
                csvBuilder.append(field.get(object)).append(",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        csvBuilder.deleteCharAt(csvBuilder.length() - 1);

        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(csvBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return csvBuilder.toString();
    }

    public static Conta convertToObject(String csvData) {
        String[] csvFields = csvData.split(",");
        Conta conta = new Conta();
        List<ContaOperacoes> historico = new ArrayList<>();

        try {
            conta.setUsuario(new Usuario(Long.parseLong(csvFields[0].trim()),
                    csvFields[1].trim(),
                    csvFields[2].trim(),
                    Integer.parseInt(csvFields[3].trim()),
                    csvFields[4].trim(),
                    csvFields[5].trim()));
            conta.setAgencia(new Agencia(Integer.parseInt(csvFields[0].trim()), csvFields[1].trim()));
            conta.setSaldo(Double.parseDouble(csvFields[2].trim()));
            conta.setNumeroConta(Integer.parseInt(csvFields[3].trim()));
            conta.setContaTipo(ContaTipo.valueOf(csvFields[4].trim()));

            // Adicionando histórico (se houver)
            for (int i = 5; i < csvFields.length; i++) {
                historico.add(new ContaOperacoes(Operacoes.valueOf(csvFields[0].trim()),
                        Double.parseDouble(csvFields[1].trim()),
                        csvFields[2].trim(),
                        Double.parseDouble(csvFields[3].trim())));
            }
            conta.setHistorico(historico);

            return conta;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void readCsvFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Conta conta = convertToObject(line);
                // Faça algo com a conta, como adicioná-la a uma lista
                System.out.println("Object created from CSV line: " + conta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
