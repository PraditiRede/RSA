import java.math.BigInteger;
import java.net.*;
import java.io.*;
import java.util.*;

class Client {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        try {
            int e, n, CT;
            String msg="";
            Socket s = new Socket("localhost", 5500);
            System.out.println("Connected");

            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            Scanner sc = new Scanner(System.in);

            System.out.println("Receiving public key from server...");
            e = din.read();
            n = din.readInt();
            System.out.println("Public Key received (e,n) = ("+e+","+n+")");

            System.out.println("Enter the message to be sent to server");
            String PT = sc.nextLine();

            for(int i=0; i<PT.length(); i++) {
                // char a = PT.charAt(i);
                // int index = Character.toUpperCase(a) - 64;
                int index = PT.charAt(i);
                msg = msg+Integer.toString(index);
            }
            // int l = str.length();
            // int convert;
            // for (int i = 0; i < l; i++) {
            //     convert = str[i] - NULL;
            //     cout << convert;
            // }
            // int msg = sc.nextInt();
            System.out.println(msg);
            int msgi = Integer.parseInt(msg);
            System.out.println("Message="+msgi);
            System.out.println("e="+e);
            System.out.println("n="+n);

            CT = (int) (Math.pow(msgi, e)) % n;

            System.out.println("Encrypted message is : " + CT);
            System.out.println("Sending CT to server...");

            // dout.writeInt(CT);
            dout.writeInt(CT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}