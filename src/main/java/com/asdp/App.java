package com.asdp;

// import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//UNCOMMENT untuk merubah jadi json (tapi masih error )
// import com.google.gson.Gson;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;


public class App
{

    public static void main(String[] args) {
        // System.out.printf("Hello world! 1");
        String msg = "Hello dunia yang indah :)";
        String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmxFTM2vLc71bAnpi41gZ1/uRkJbr0MNz8sjNadgP9uujawAvXSZ49t0onL7zRWJxr1hqmSyTsd88n++0e0HBRhUZNAHMSUwcYwB6PIwT5aksbsTwQ1V2M2tK0hKEgI5rFyF8NRAIGGtfWNFcvFg5NOgBZMdj/jbD5RXo75eXCpxTI8SiQz4OKWp5bjzPeIJrpBabjFa/nDHQcn7ROUENEL4qfUDUp+B6Ab6IAFJXho3cdugAbjo4sCY/u2Fzz7fsAMIZ3QlVgC0Z9+TnhgSpzBY3l87CgkCNcqojtSWNe2PCinoW4Xg4ezpxoSGQMgQJtjGzvKNDKYNsYriZD6B6UQIDAQAB";
        String enc1 = encryptWithPublicKey(msg, pubKey);

        System.out.print(enc1);

        //UNCOMMENT untuk merubah jadi json (tapi masih error ) ================== start
            // ObjEncRsa hasilObj = new ObjEncRsa(enc1, true);
            // Gson gson = new Gson();
            // String hasilJson = gson.toJson(hasilObj);
            // System.out.print(hasilJson);
        // UNCOMMENT untuk merubah jadi json (tapi masih error ) ================== end
    }

    public static PublicKey setPublicKey(String pub) {
        try {
            if (pub.toLowerCase().contains("public key")) {
                pub = pub.replaceAll("-----BEGIN RSA PUBLIC KEY-----", "")
                        .replaceAll("-----END RSA PUBLIC KEY-----", "")
                        .replaceAll("\n", "");
                ;
            }
            pub = pub.trim();
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(pub);

            X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pk = kf.generatePublic(ks);
            return pk;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        }
        return null;
    }

    public static String encryptWithPublicKey(String message, String publicKey) {
        try {
            Cipher encryptCipher = Cipher.getInstance("RSA");
            // PublicKey pk = GeneratePairKey.setPublicKey(publicKey);
            PublicKey pk = setPublicKey(publicKey);
            encryptCipher.init(Cipher.ENCRYPT_MODE, pk);

            byte[] byteMessage = message.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedMessageBytes = encryptCipher.doFinal(byteMessage);

            String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
            return encodedMessage;
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException
                | NoSuchPaddingException ex) {
            return "";
        }
    }
}
