import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String modo = null;
        String pasta = null;
        String arquivoSaida = "relatorio.csv";
        int quantidadeThreads = 4;

        // Leitura dos argumentos
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--modo":
                    modo = args[++i];
                    break;
                case "--pasta":
                    pasta = args[++i];
                    break;
                case "--saida":
                    arquivoSaida = args[++i];
                    break;
                case "--threads":
                    quantidadeThreads = Integer.parseInt(args[++i]);
                    break;
            }
        }

        if (modo == null || pasta == null) {
            System.out.println("Uso correto:");
            System.out.println("java Principal --modo [sequencial|concorrente] --pasta <diretorio> [--threads N] --saida arquivo.csv");
            return;
        }

        try {
            if (modo.equalsIgnoreCase("sequencial")) {
                ProcessoSequencial processo = new ProcessoSequencial(pasta);
                processo.processar(arquivoSaida);

            } else if (modo.equalsIgnoreCase("concorrente")) {
                ProcessoConcorrente processo = new ProcessoConcorrente(pasta, quantidadeThreads);
                processo.processar(arquivoSaida);

            } else {
                System.out.println("Modo inválido. Use: sequencial ou concorrente.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao processar arquivos: " + e.getMessage());
        }
    }
}
