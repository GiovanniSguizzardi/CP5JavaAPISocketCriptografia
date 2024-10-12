import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Servidor {
    private static BigInteger N;
    private static BigInteger D;

    public static void main(String[] args) {
        try {
            printAsciiArt();
            ServerSocket serverSocket = new ServerSocket(9600);
            System.out.println("Servidor aguardando conexão...");
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            N = new BigInteger(in.readUTF());
            BigInteger E = new BigInteger(in.readUTF());
            gerarChavePrivada(E);

            BigInteger mensagemCriptografada = new BigInteger(in.readUTF());
            System.out.println("• Mensagem criptografada recebida: " + mensagemCriptografada);

            BigInteger mensagemDescriptografada = mensagemCriptografada.modPow(D, N);
            String mensagemOriginal = new String(mensagemDescriptografada.toByteArray(), StandardCharsets.UTF_8);
            System.out.println("• Mensagem descriptografada: " + mensagemOriginal);

            BigInteger respostaCriptografada = mensagemDescriptografada.modPow(E, N); // Criptografa novamente com E
            out.writeUTF(respostaCriptografada.toString()); // Envia a mensagem criptografada de volta

            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void gerarChavePrivada(BigInteger E) {
        BigInteger p = new BigInteger("2750159");
        BigInteger q = new BigInteger("2750161");
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        D = E.modInverse(phi);  // Inverso de E mod phi(N)

        System.out.println("• Chave privada gerada (D): " + D);
    }

    private static void printAsciiArt() {
        System.out.println();
        System.out.println(" $$$$$$\\  $$$$$$$\\  $$$$$$$\\                         $$$$$\\  $$$$$$\\  $$\\    $$\\  $$$$$$\\  ");
        System.out.println("$$  __$$\\ $$  __$$\\ $$  ____|                        \\__$$ |$$  __$$\\ $$ |   $$ |$$  __$$\\ ");
        System.out.println("$$ /  \\__|$$ |  $$ |$$ |                                $$ |$$ /  $$ |$$ |   $$ |$$ /  $$ |");
        System.out.println("$$ |      $$$$$$$  |$$$$$$$\\        $$$$$$\\             $$ |$$$$$$$$ |\\$$\\  $$  |$$$$$$$$ |");
        System.out.println("$$ |      $$  ____/ \\_____$$\\       \\______|      $$\\   $$ |$$  __$$ | \\$$\\$$  / $$  __$$ |");
        System.out.println("$$ |  $$\\ $$ |      $$\\   $$ |                    $$ |  $$ |$$ |  $$ |  \\$$$  /  $$ |  $$ |");
        System.out.println("\\$$$$$$  |$$ |      \\$$$$$$  |                    \\$$$$$$  |$$ |  $$ |   \\$  /   $$ |  $$ |");
        System.out.println(" \\______/ \\__|       \\______/                      \\______/ \\__|  \\__|    \\_/    \\__|  \\__|");
        System.out.println();
    }
}
