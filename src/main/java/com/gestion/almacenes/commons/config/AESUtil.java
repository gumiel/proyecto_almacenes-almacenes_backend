package com.gestion.almacenes.commons.config;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESUtil {
  private static final String ALGORITHM = "AES";
  private static final String SECRET_KEY = "0123456789abcdefh"; // Cambia esta clave por una más segura

  public static String encrypt(String data) throws Exception {
    SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, key);
    byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  public static String decrypt(String encryptedData) throws Exception {
    SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
    Cipher cipher = Cipher.getInstance(ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
    byte[] decryptedBytes = cipher.doFinal(decodedBytes);
    return new String(decryptedBytes, StandardCharsets.UTF_8);
  }
}
