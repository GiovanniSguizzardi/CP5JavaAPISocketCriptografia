import java.io.*;
import java.net.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class Servidor {
    private static BigInteger N;
    private static BigInteger D;

    public static void main(String[] args) {
        try {
            // Imprimir a mensagem ASCII no início
            printAsciiArt();

            ServerSocket serverSocket = new ServerSocket(9600);
            System.out.println("Servidor aguardando conexão...");

            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Receber as chaves públicas (N, E) do cliente
            N = new BigInteger(in.readUTF());
            BigInteger E = new BigInteger(in.readUTF());

            // Gerar a chave privada D com base em N e E
            gerarChavePrivada(E);

            // Receber a mensagem criptografada do cliente
            BigInteger mensagemCriptografada = new BigInteger(in.readUTF());
            System.out.println("• Mensagem criptografada recebida: " + mensagemCriptografada);

            // Descriptografar a mensagem
            BigInteger mensagemDescriptografada = mensagemCriptografada.modPow(D, N);
            String mensagemOriginal = new String(mensagemDescriptografada.toByteArray(), StandardCharsets.UTF_8);
            System.out.println("• Mensagem descriptografada: " + mensagemOriginal);

            // Criptografar a mensagem original de volta e enviar para o cliente
            BigInteger respostaCriptografada = mensagemDescriptografada.modPow(E, N); // Criptografa novamente com E
            out.writeUTF(respostaCriptografada.toString()); // Envia a mensagem criptografada de volta

            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para gerar a chave privada D com base em E e N
    private static void gerarChavePrivada(BigInteger E) {
        BigInteger p = N.divide(BigInteger.probablePrime(256, new SecureRandom()));
        BigInteger q = N.divide(p);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        D = E.modInverse(phi);

        System.out.println("• Chave privada gerada (D): " + D);
    }

    // Método para imprimir o texto ASCII
    private static void printAsciiArt() {
        System.out.println("");
        System.out.println(" $$$$$$\\  $$$$$$$\\  $$$$$$$\\                         $$$$$\\  $$$$$$\\  $$\\    $$\\  $$$$$$\\  ");
        System.out.println("$$  __$$\\ $$  __$$\\ $$  ____|                        \\__$$ |$$  __$$\\ $$ |   $$ |$$  __$$\\ ");
        System.out.println("$$ /  \\__|$$ |  $$ |$$ |                                $$ |$$ /  $$ |$$ |   $$ |$$ /  $$ |");
        System.out.println("$$ |      $$$$$$$  |$$$$$$$\\        $$$$$$\\             $$ |$$$$$$$$ |\\$$\\  $$  |$$$$$$$$ |");
        System.out.println("$$ |      $$  ____/ \\_____$$\\       \\______|      $$\\   $$ |$$  __$$ | \\$$\\$$  / $$  __$$ |");
        System.out.println("$$ |  $$\\ $$ |      $$\\   $$ |                    $$ |  $$ |$$ |  $$ |  \\$$$  /  $$ |  $$ |");
        System.out.println("\\$$$$$$  |$$ |      \\$$$$$$  |                    \\$$$$$$  |$$ |  $$ |   \\$  /   $$ |  $$ |");
        System.out.println(" \\______/ \\__|       \\______/                      \\______/ \\__|  \\__|    \\_/    \\__|  \\__|");
        System.out.println("");
    }
}