import java.math.*;
import java.net.*;
import java.io.*;
import java.util.Random;

class Server {

    static int gcd(int e, int z) {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }

    private static int randomPrime() {
        int num = 0;
        Random rand = new Random();

        num = rand.nextInt(1000) + 1;
        while (!isPrime(num)) {
            num = rand.nextInt(1000) + 1;
        }

        return num;

    }

    private static boolean isPrime(int inputNum) {
        if (inputNum <= 3 || inputNum % 2 == 0)
            return inputNum == 2 || inputNum == 3;
        int divisor = 3;
        while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0))
            divisor += 2;

        return inputNum % divisor != 0;

    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        try {
            int p, q, n, z, d = 0, e, i, CT;
            // Scanner sc = new Scanner(System.in);
            BigInteger msgback;
            ServerSocket ss = new ServerSocket(5500);
            System.out.println("Server Started");
            System.out.println("Waiting for client to accept");
            Socket s = ss.accept();
            System.out.println("Client accepted");

            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            p = randomPrime();
            System.out.println("p="+p);
            q = randomPrime();
            System.out.println("q="+q);
            n = p * q;
            z = (p - 1) * (q - 1);
            for (e = 2; e < z; e++) {
                if (gcd(e, z) == 1) {
                    break;
                }
            }
            System.out.println("the value of e (public key of server) = " + e);
            for (i = 0; i <= 9; i++) {
                int x = 1 + (i * z);
                if (x % e == 0) {
                    d = x / e;
                    break;
                }
            }
            System.out.println("the value of d (private key of server) = " + d);
            System.out.println("Public Key (e,n) = ("+e+","+n+")");
            System.out.println("Sending this public key to client...");
            

            dout.write(e);
            dout.writeInt(n);

            CT = din.readInt();
            System.out.println("Cipher text recieved: " + CT);

            BigInteger N = BigInteger.valueOf(n);
            BigInteger C = BigDecimal.valueOf(CT).toBigInteger();
            System.out.println("C="+C);
            System.out.println("d="+d);
            System.out.println("N="+N);
            msgback = (C.pow(d)).mod(N);
            int v = (int) (Math.pow(CT, d)) % n;
            System.out.println(v);
            System.out.println("Decrypted message is : " + msgback);

            //for single character
            // int msgbackint = msgback.intValue();
            // int PTnum = msgbackint + 64;
            // char PT = (char)PTnum;  
            // System.out.println(Character.toLowerCase(PT));

            // for more than 1 letter string
            int radix = 10;
            int nums=0;
            String msg = "";
            String PTstring = msgback.toString(radix);
            for(int j=0; j<PTstring.length(); j++) {
                // char abc = PTstring.charAt(j);
                // int index = Integer.parseInt(String.valueOf(abc));
                // index = index + 64;
                // msg = msg + Character.toString((char) index);
                nums = nums * 10 + (PTstring.charAt(j) - '0');
                if (nums >= 32 && nums <= 122) {
                    char cha = (char)nums;
                    nums = 0;
                    System.out.print(cha);
                }
            }
            // int num = 0;
            // for (int i = 0; i < len; i++) {
    
            //     // Append the current digit
            //     num = num * 10 + (str.charAt(i) - '0');
    
            //     // If num is within the required range
            //     if (num >= 32 && num <= 122) {
    
            //         // Convert num to char
            //         char ch = (char)num;
            //         System.out.print(ch);
    
            //         // Reset num to 0
            //         num = 0;
            //     }
            // }
            System.out.println(msg.toLowerCase());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}