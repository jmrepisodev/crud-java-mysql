/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crud_java.controlador;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author JOSE
 */
public class AutenticateController {
    
    public byte[] cifrar (String sinCifrar) throws Exception {
        final byte[] bytes = sinCifrar.getBytes("UTF-8");
        final Cipher aes = getCipher(true);
        final byte[] cifrado = aes.doFinal(bytes);
        
        return cifrado;
    }

     public String descifrar (byte[] cifrado) throws Exception {
        final Cipher aes = getCipher(false);
        final byte[] bytes = aes.doFinal(cifrado);
        final String sinCifrar = new String(bytes, "UTF-8");
        
        return sinCifrar;
     }

     private Cipher getCipher(boolean paraCifrar) throws Exception {
         final String frase = "FraseLargaConDiferentesLetrasNumerosYCaracteresEspeciales_áÁéÉíÍóÓúÚüÜñÑ1234567890!#%$&()=%_NO_USAR_ESTA_FRASE!_";
         final MessageDigest digest = MessageDigest.getInstance("SHA");
         digest.update(frase.getBytes("UTF-8"));
         final SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");

         final Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
         if (paraCifrar) {
                 aes.init(Cipher.ENCRYPT_MODE, key);
         } else {
                 aes.init(Cipher.DECRYPT_MODE, key);
         }

         return aes;
     }
     
     public static String getSHA256(String input){

	String toReturn = null;
	try {
	    MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    digest.reset();
	    digest.update(input.getBytes("utf8"));
	    toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
            
           // MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
           // toReturn=bytesToHex(encodedhash);
            
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	return toReturn;
    }
     
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
