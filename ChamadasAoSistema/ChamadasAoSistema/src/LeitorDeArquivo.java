import java.io.*; //inclusão da biblioteca para input e output
import java.util.regex.*;

public class LeitorDeArquivo {

    private static final Pattern PADRAO_NUMERO =
            Pattern.compile("-?\\d+(\\.\\d+)?");

    public static double somarNumerosDoArquivo(File arquivo) throws IOException {
        double soma = 0.0;

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = leitor.readLine()) != null) { //le linha até o final
                Matcher matcher = PADRAO_NUMERO.matcher(linha);

                while (matcher.find()) {//procura o proximo padrão "-?\\d+(\\.\\d+)?"
                    soma += Double.parseDouble(matcher.group());
                }
            }
        }

        return soma;
    }
}

