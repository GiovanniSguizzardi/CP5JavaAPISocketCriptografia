import java.io.*;
import java.net.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Scanner;

public class Cliente {
    private static BigInteger N;
    private static BigInteger E;
    private static BigInteger D;

    public static void main(String[] args) {
        try {
            // Imprime o novo ASCII art
            printAsciiArt();

            // Cria um Scanner para ler a entrada do usuário
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite a mensagem que deseja enviar ao servidor: ");
            String mensagem = scanner.nextLine();

            // Gerar dinamicamente novos p e q e, portanto, novas chaves, com base no comprimento da mensagem
            gerarChavesRSA(mensagem.length());

            // Converte a mensagem para BigInteger
            BigInteger mensagemBigInt = new BigInteger(mensagem.getBytes(StandardCharsets.UTF_8));

            // Conectar ao servidor
            Socket socket = new Socket("localhost", 9600);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Enviar as chaves públicas (N, E) para o servidor
            out.writeUTF(N.toString());
            out.writeUTF(E.toString());

            // Criptografar a mensagem e enviá-la
            BigInteger mensagemCriptografada = mensagemBigInt.modPow(E, N);
            System.out.println("Mensagem criptografada (como número): " + mensagemCriptografada);
            out.writeUTF(mensagemCriptografada.toString());

            // Receber a resposta do servidor
            BigInteger respostaCriptografada = new BigInteger(in.readUTF());
            System.out.println("Resposta criptografada do servidor: " + respostaCriptografada);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para imprimir o novo texto ASCII
    private static void printAsciiArt() {
        System.out.println("");
        System.out.println(" $$$$$$\\  $$$$$$$\\  $$$$$$$\\                       $$$$$$\\  $$\\       $$$$$$\\ $$$$$$$$\\ $$\\   $$\\ $$$$$$$$\\ $$$$$$$$\\ ");
        System.out.println("$$  __$$\\ $$  __$$\\ $$  ____|                     $$  __$$\\ $$ |      \\_$$  _|$$  _____|$$$\\  $$ |\\__$$  __|$$  _____|");
        System.out.println("$$ /  \\__|$$ |  $$ |$$ |                          $$ /  \\__|$$ |        $$ |  $$ |      $$$$\\ $$ |   $$ |   $$ |      ");
        System.out.println("$$ |      $$$$$$$  |$$$$$$$\\        $$$$$$\\       $$ |      $$ |        $$ |  $$$$$\\    $$ $$\\$$ |   $$ |   $$$$$\\    ");
        System.out.println("$$ |      $$  ____/ \\_____$$\\       \\______|      $$ |      $$ |        $$ |  $$  __|   $$ \\$$$$ |   $$ |   $$  __|   ");
        System.out.println("$$ |  $$\\ $$ |      $$\\   $$ |                    $$ |  $$\\ $$ |        $$ |  $$ |      $$ |\\$$$ |   $$ |   $$ |      ");
        System.out.println("\\$$$$$$  |$$ |      \\$$$$$$  |                    \\$$$$$$  |$$$$$$$$\\ $$$$$$\\ $$$$$$$$\\ $$ | \\$$ |   $$ |   $$$$$$$$\\ ");
        System.out.println(" \\______/ \\__|       \\______/                      \\______/ \\________|\\______|\\________|\\__|  \\__|   \\__|   \\________|");
        System.out.println("");
    }

    // Gera novos valores de p, q, N, e, d
    private static void gerarChavesRSA(int tamanhoMensagem) {
        SecureRandom random = new SecureRandom();
        int tamanhoBits = Math.max(512, tamanhoMensagem * 8);  // Calcula um tamanho adequado com base na mensagem
        BigInteger p = BigInteger.probablePrime(tamanhoBits / 2, random);
        BigInteger q = BigInteger.probablePrime(tamanhoBits / 2, random);
        N = p.multiply(q);  // N = p * q

        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        E = BigInteger.valueOf(65537); // Valor comum para E
        D = E.modInverse(phi); // d é o inverso de e mod phi

        System.out.println("Novas chaves geradas: ");
        System.out.println("N: " + N);
        System.out.println("E: " + E);
        System.out.println("D: " + D);
    }
}