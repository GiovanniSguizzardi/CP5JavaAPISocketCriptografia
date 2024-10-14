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
            printAsciiArt();

            Scanner scanner = new Scanner(System.in);
            System.out.print("* Digite a mensagem que deseja enviar ao servidor: ");
            String mensagem = scanner.nextLine();

            gerarChavesRSA();

            BigInteger mensagemBigInt = new BigInteger(1, mensagem.getBytes(StandardCharsets.UTF_8));

            Socket socket = new Socket("localhost", 9600);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF(N.toString());
            out.writeUTF(E.toString());

            BigInteger mensagemCriptografada = mensagemBigInt.modPow(E, N);
            System.out.println("* Mensagem criptografada: " + mensagemCriptografada);

            out.writeUTF(mensagemCriptografada.toString());
            out.flush();

            BigInteger respostaCriptografada = new BigInteger(in.readUTF());
            System.out.println("* Resposta criptografada recebida: " + respostaCriptografada);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private static void gerarChavesRSA() {
        BigInteger p = new BigInteger("32416190071");
        BigInteger q = new BigInteger("32416187567");

        N = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        E = BigInteger.valueOf(65537);  // Valor comum para E
        D = E.modInverse(phi);

        System.out.println("* Novas chaves geradas:");
        System.out.println("N: " + N);
        System.out.println("E: " + E);
        System.out.println("D: " + D);
    }
}
