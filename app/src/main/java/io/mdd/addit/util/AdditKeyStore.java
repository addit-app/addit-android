//package io.mdd.addit.util;
//
//import android.content.Context;
//import android.security.KeyPairGeneratorSpec;
//import android.util.Base64;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.math.BigInteger;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.CertificateException;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Enumeration;
//import java.util.List;
//
//import javax.crypto.Cipher;
//import javax.crypto.CipherInputStream;
//import javax.crypto.CipherOutputStream;
//import javax.security.auth.x500.X500Principal;
//
//public class AdditKeyStore {
//    static final String TAG = "SimpleKeystoreApp";
//    static final String CIPHER_TYPE = "RSA/ECB/PKCS1Padding";
//    static final String CIPHER_PROVIDER = "AndroidOpenSSL";
//
//    KeyStore keyStore;
//    List<String> keyAliases;
//
//    Context context;
//
//    public void KeyStore(Context context) {
//        this.context = context;
//        try {
//            keyStore = KeyStore.getInstance("AndroidKeyStore");
//            keyStore.load(null);
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        refreshKeys();
//    }
//
//    private void refreshKeys() {
//        keyAliases = new ArrayList<>();
//        try {
//            Enumeration<String> aliases = keyStore.aliases();
//            while (aliases.hasMoreElements()) {
//                keyAliases.add(aliases.nextElement());
//            }
//        } catch (Exception e) {
//        }
//    }
//
//    public void createNewKeys(String newKey) {
//        String alias = newKey;
//        try {
//            // Create new key if needed
//            if (!keyStore.containsAlias(alias)) {
//                Calendar start = Calendar.getInstance();
//                Calendar end = Calendar.getInstance();
//                end.add(Calendar.YEAR, 1);
//                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
//                        .setAlias(alias)
//                        .setSubject(new X500Principal("CN=Sample Name, O=Android Authority"))
//                        .setSerialNumber(BigInteger.ONE)
//                        .setStartDate(start.getTime())
//                        .setEndDate(end.getTime())
//                        .build();
//                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
//                generator.initialize(spec);
//
//                KeyPair keyPair = generator.generateKeyPair();
//            }
//        } catch (Exception e) {
//            Toast.makeText(context, "Exception " + e.getMessage() + " occured", Toast.LENGTH_LONG).show();
//            Log.e(TAG, Log.getStackTraceString(e));
//        }
//        refreshKeys();
//    }
//
//    public void deleteKey(final String alias) {
//        try {
//            keyStore.deleteEntry(alias);
//            refreshKeys();
//        } catch (KeyStoreException e) {
//            Toast.makeText(context,
//                    "Exception " + e.getMessage() + " occured",
//                    Toast.LENGTH_LONG).show();
//            Log.e(TAG, Log.getStackTraceString(e));
//        }
//
//    }
//
//    public void encryptString(String alias, String startText) {
//        try {
//            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, null);
//            RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();
//
//            String initialText = startText.toString();
//            if (initialText.isEmpty()) {
//                Toast.makeText(context, "Enter text in the 'Initial Text' widget", Toast.LENGTH_LONG).show();
//                return;
//            }
//
//            Cipher inCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
//            inCipher.init(Cipher.ENCRYPT_MODE, publicKey);
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            CipherOutputStream cipherOutputStream = new CipherOutputStream(
//                    outputStream, inCipher);
//            cipherOutputStream.write(initialText.getBytes("UTF-8"));
//            cipherOutputStream.close();
//
//            byte[] vals = outputStream.toByteArray();
//            encryptedText.setText(Base64.encodeToString(vals, Base64.DEFAULT));
//        } catch (Exception e) {
//            Toast.makeText(context, "Exception " + e.getMessage() + " occured", Toast.LENGTH_LONG).show();
//            Log.e(TAG, Log.getStackTraceString(e));
//        }
//    }
//
//    public void decryptString(String alias) {
//        try {
//            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, null);
//            RSAPrivateKey privateKey = (RSAPrivateKey) privateKeyEntry.getPrivateKey();
//
//            Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
//            output.init(Cipher.DECRYPT_MODE, privateKey);
//
//            String cipherText = encryptedText.getText().toString();
//            CipherInputStream cipherInputStream = new CipherInputStream(
//                    new ByteArrayInputStream(Base64.decode(cipherText, Base64.DEFAULT)), output);
//            ArrayList<Byte> values = new ArrayList<>();
//            int nextByte;
//            while ((nextByte = cipherInputStream.read()) != -1) {
//                values.add((byte) nextByte);
//            }
//
//            byte[] bytes = new byte[values.size()];
//            for (int i = 0; i < bytes.length; i++) {
//                bytes[i] = values.get(i).byteValue();
//            }
//
//            String finalText = new String(bytes, 0, bytes.length, "UTF-8");
//            decryptedText.setText(finalText);
//
//        } catch (Exception e) {
//            Toast.makeText(this, "Exception " + e.getMessage() + " occured", Toast.LENGTH_LONG).show();
//            Log.e(TAG, Log.getStackTraceString(e));
//        }
//    }
//}
