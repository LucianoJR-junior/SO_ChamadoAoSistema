import java.io.*;
import java.util.*;

public class ProcessoSequencial {

    private final File diretorio;

    public ProcessoSequencial(String caminhoPasta) {
        this.diretorio = new File(caminhoPasta);
    }

    public void processar(String arquivoSaida) throws IOException {
        long inicio = System.currentTimeMillis();
        long memoriaAntes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        List<String[]> linhasRelatorio = new ArrayList<>();
        double somaTotalGeral = 0;

        File[] arquivos = diretorio.listFiles((dir, nome) -> nome.endsWith(".txt"));

        if (arquivos == null) throw new IOException("Pasta inválida: " + diretorio.getAbsolutePath());

        for (File arquivo : arquivos) {
            long t1 = System.currentTimeMillis(); //registra o tempo antes de processar o arquivo atual
            double soma = LeitorDeArquivo.somarNumerosDoArquivo(arquivo); //chama o método somarNum... da classe LeitorDeArquivo
            long t2 = System.currentTimeMillis(); //registra o tempo depois de processar o arquivo atual 

            linhasRelatorio.add(new String[]{arquivo.getName(), Double.toString(soma), Long.toString(t2 - t1)}); //cria um array de strings com os resultados
            somaTotalGeral += soma;
        }

        long fim = System.currentTimeMillis(); //registra o tempo final após o loop ter terminado
        long memoriaDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(); //calcula a memoria usada após o processamento

        GeradorDeRelatorio.gerar( //chama o método gerar da classe GeradorDeRelatorio
                arquivoSaida,
                "sequencial",
                linhasRelatorio,
                fim - inicio,
                memoriaDepois - memoriaAntes,
                somaTotalGeral
        );

        System.out.println("Processamento sequencial concluído! Relatório gerado em: " + arquivoSaida);
    }
}
