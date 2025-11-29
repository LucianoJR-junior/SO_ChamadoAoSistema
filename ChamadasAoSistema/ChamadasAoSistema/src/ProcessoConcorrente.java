import java.io.*;
import java.util.*;
import java.util.concurrent.*; //essencial para esta classe, pois importa todas as classes desta biblioteca, fundamental para gerenciar 
// threads e tarefas paralelas, exemplos de classe incluídas: ExecutorService, Executors e Future

public class ProcessoConcorrente {

    private final File diretorio;
    private final int quantidadeThreads;

    public ProcessoConcorrente(String caminhoPasta, int quantidadeThreads) {
        this.diretorio = new File(caminhoPasta);
        this.quantidadeThreads = quantidadeThreads;
    }

    public void processar(String arquivoSaida) throws IOException {
        long inicio = System.currentTimeMillis();
        long memoriaAntes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        ExecutorService pool = Executors.newFixedThreadPool(quantidadeThreads); //Permite que o sistema gerencie a execução concorrente de tarefas,
        //limitando o número de execuções simultâneas.
        List<Future<String[]>> futuros = new ArrayList<>(); //?
        double somaTotalGeral = 0;

        File[] arquivos = diretorio.listFiles((dir, nome) -> nome.endsWith(".txt"));
        if (arquivos == null) throw new IOException("Pasta inválida: " + diretorio.getAbsolutePath());

        for (File arquivo : arquivos) {
            futuros.add(pool.submit(() -> { //?
                long t1 = System.currentTimeMillis();
                double soma = LeitorDeArquivo.somarNumerosDoArquivo(arquivo);
                long t2 = System.currentTimeMillis();
                return new String[]{arquivo.getName(), Double.toString(soma), Long.toString(t2 - t1)};
            }));
        }

        pool.shutdown(); //encerra o pool de threads, entao o ExecutorService não aceitara novas tarefas

        List<String[]> linhasRelatorio = new ArrayList<>();

        for (Future<String[]> futuro : futuros) { //inicia um loop para resgatar sobre cada resultado armazenado no future
            try {
                String[] dados = futuro.get();
                linhasRelatorio.add(dados);
                somaTotalGeral += Double.parseDouble(dados[1]);

            } catch (Exception e) {
                throw new IOException("Erro durante o processamento concorrente.");
            }
        }

        long fim = System.currentTimeMillis();
        long memoriaDepois = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        GeradorDeRelatorio.gerar(
                arquivoSaida,
                "concorrente",
                linhasRelatorio,
                fim - inicio,
                memoriaDepois - memoriaAntes,
                somaTotalGeral
        );

        System.out.println("Processamento concorrente concluído! Relatório gerado em: " + arquivoSaida);
    }
}
