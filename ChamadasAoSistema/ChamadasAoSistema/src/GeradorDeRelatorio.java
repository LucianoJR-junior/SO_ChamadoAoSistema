import java.io.*;
import java.util.*;

public class GeradorDeRelatorio {

    public static void gerar(
            String arquivoSaida,
            String modo,
            List<String[]> linhas,
            long tempoTotalMs,
            long diferencaMemoria,
            double somaGeral) throws IOException {

        try (PrintWriter escritor = new PrintWriter(new FileWriter(arquivoSaida))) {

            escritor.println("Modo: " + modo); //escritor escreve no arquivo
            escritor.println("Tempo Total: " + tempoTotalMs + " ms");
            escritor.println("Variação da Memória: " + diferencaMemoria + " bytes");
            escritor.println("Soma Geral: " + somaGeral);
            escritor.println();
            escritor.println("Arquivo | Soma | Tempo (ms)");

            for (String[] linha : linhas) {
                escritor.println(String.join(" | ", linha));
            }
        }
    }
}
