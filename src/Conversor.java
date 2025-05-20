import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class Conversor {

    static class ApiResponse {
        Map<String, Double> conversion_rates;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("Boas vindas ao conversor de moedas!");
            System.out.println("1. Dólar para Peso Argentino");
            System.out.println("2. Peso Argentino para Dólar");
            System.out.println("3. Dólar para Real Brasileiro");
            System.out.println("4. Real Brasileiro para Dólar");
            System.out.println("5. Dólar para Peso Colombiano");
            System.out.println("6. Peso Colombiano para Dólar");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção válida: ");
            opcao = scanner.nextInt();

            if (opcao >= 1 && opcao <= 6) {
                System.out.print("Digite o valor a ser convertido: ");
                double valor = scanner.nextDouble();
                converterMoeda(opcao, valor);
            }

        } while (opcao != 7);

        System.out.println("Programa encerrado.");
    }

    public static void converterMoeda(int opcao, double valor) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://v6.exchangerate-api.com/v6/ff3c7ab8ef4daf5fb3f89b6d/latest/USD"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            ApiResponse resposta = gson.fromJson(response.body(), ApiResponse.class);

            double taxa;
            double resultado;

            switch (opcao) {
                case 1:
                    taxa = resposta.conversion_rates.get("ARS");
                    resultado = valor * taxa;
                    System.out.println("Valor convertido: " + resultado + " Pesos Argentinos");
                    break;
                case 2:
                    taxa = resposta.conversion_rates.get("ARS");
                    resultado = valor / taxa;
                    System.out.println("Valor convertido: " + resultado + " Dólares");
                    break;
                case 3:
                    taxa = resposta.conversion_rates.get("BRL");
                    resultado = valor * taxa;
                    System.out.println("Valor convertido: " + resultado + " Reais");
                    break;
                case 4:
                    taxa = resposta.conversion_rates.get("BRL");
                    resultado = valor / taxa;
                    System.out.println("Valor convertido: " + resultado + " Dólares");
                    break;
                case 5:
                    taxa = resposta.conversion_rates.get("COP");
                    resultado = valor * taxa;
                    System.out.println("Valor convertido: " + resultado + " Pesos Colombianos");
                    break;
                case 6:
                    taxa = resposta.conversion_rates.get("COP");
                    resultado = valor / taxa;
                    System.out.println("Valor convertido: " + resultado + " Dólares");
                    break;
                default:
                    break;
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao obter dados da API.");
            e.printStackTrace();
        }
    }
}
