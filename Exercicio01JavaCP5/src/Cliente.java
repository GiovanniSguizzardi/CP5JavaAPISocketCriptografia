import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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

            // Gerar as chaves RSA com p e q maiores
            gerarChavesRSA();

            // Converte a mensagem para BigInteger, garantindo que seja positiva
            BigInteger mensagemBigInt = new BigInteger(1, mensagem.getBytes(StandardCharsets.UTF_8));

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
        System.out.println();
        System.out.println(" $$$$$$\\  $$$$$$$\\  $$$$$$$\\                       $$$$$$\\  $$\\       $$$$$$\\ $$$$$$$$\\ $$\\   $$\\ $$$$$$$$\\ $$$$$$$$\\ ");
        System.out.println("$$  __$$\\ $$  __$$\\ $$  ____|                     $$  __$$\\ $$ |      \\_$$  _|$$  _____|$$$\\  $$ |\\__$$  __|$$  _____|");
        System.out.println("$$ /  \\__|$$ |  $$ |$$ |                          $$ /  \\__|$$ |        $$ |  $$ |      $$$$\\ $$ |   $$ |   $$ |      ");
        System.out.println("$$ |      $$$$$$$  |$$$$$$$\\        $$$$$$\\       $$ |      $$ |        $$ |  $$$$$\\    $$ $$\\$$ |   $$ |   $$$$$\\    ");
        System.out.println("$$ |      $$  ____/ \\_____$$\\       \\______|      $$ |      $$ |        $$ |  $$  __|   $$ \\$$$$ |   $$ |   $$  __|   ");
        System.out.println("$$ |  $$\\ $$ |      $$\\   $$ |                    $$ |  $$\\ $$ |        $$ |  $$ |      $$ |\\$$$ |   $$ |   $$ |      ");
        System.out.println("\\$$$$$$  |$$ |      \\$$$$$$  |                    \\$$$$$$  |$$$$$$$$\\ $$$$$$\\ $$$$$$$$\\ $$ | \\$$ |   $$ |   $$$$$$$$\\ ");
        System.out.println(" \\______/ \\__|       \\______/                      \\______/ \\________|\\______|\\________|\\__|  \\__|   \\__|   \\________|");
        System.out.println();
    }

    // Usando p e q maiores para gerar um N maior
    private static void gerarChavesRSA() {
        BigInteger p = new BigInteger("2750159"); // Número primo maior
        BigInteger q = new BigInteger("2750161"); // Outro número primo maior

        // Calcular N = p * q
        N = p.multiply(q);

        // Calcular phi(N)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Definir chave pública E e calcular D
        E = BigInteger.valueOf(65537); // Valor comum para E em implementações modernas
        D = E.modInverse(phi); // Inverso multiplicativo de E

        System.out.println("Novas chaves geradas:");
        System.out.println("N: " + N);
        System.out.println("E: " + E);
        System.out.println("D: " + D);
    }
}
