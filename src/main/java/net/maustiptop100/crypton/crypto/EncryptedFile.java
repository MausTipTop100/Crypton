package net.maustiptop100.crypton.crypto;

import net.maustiptop100.crypton.Main;
import net.maustiptop100.crypton.math.Hex;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class EncryptedFile {

    /*
        PROTOCOL (in HEX)
        Encrypted SecretKey Object
        Encrypted Content
     */

    private File file;

    private byte[] content;
    private char[] passphrase;

    private static final String salt = "#UU7Q_ZPDS(Sjg4ua/Fl#EhKWlvNp:!a!#6RR#67Z.E15i*vXUaV88*8o@:JYc7U4_l?9af5xCQjfIPKa1eP?HdSYG+d#5a7Zn#KKLZ8zaod!oZ_#o5gzRN6whneZ-Bu";

    public EncryptedFile(File f, char[] pass)
    {
        this.file = f;
        this.passphrase = pass;
    }

    public EncryptedFile(File f, byte[] c, char[] pass)
    {
        this.file = f;
        this.content = c;
        this.passphrase = pass;
    }

    private static final SecretKey getKeyFromPassphrase(char[] pass)
            throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        Main.log("Generating secondary SecretKey Object from passphrase...");
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return new SecretKeySpec(
                factory.generateSecret(new PBEKeySpec(pass, salt.getBytes(), 65536, 256)).getEncoded(),
                "AES");
    }

    public void writeFile()
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidKeySpecException, IllegalBlockSizeException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Main.log("Generating primary SecretKey Object...");
        SecretKey key = KeyGenerator.getInstance("AES").generateKey();

        Main.log("Initializing cipher...");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.WRAP_MODE, getKeyFromPassphrase(this.passphrase));
        Main.log("Wrapping key...");
        byte[] keywrap = cipher.wrap(key);

        ObjectOutputStream oos = new ObjectOutputStream(baos);
        Main.log("Writing wrapped key...");
        oos.writeObject(keywrap);

        Main.log("Re-Initializing cipher...");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        CipherOutputStream cos = new CipherOutputStream(baos, cipher);
        ObjectOutputStream ocos = new ObjectOutputStream(cos);

        Main.log("Writing encrypted content...");
        ocos.writeObject(this.content);
        ocos.flush();
        ocos.close();

        Main.log("Writing file...");
        FileOutputStream fos = new FileOutputStream(this.file);
        Main.log("Converting to hex...");
        fos.write(new Hex(baos.toByteArray()).getHex().getBytes());
        fos.flush();
        fos.close();
    }

    public void readFile()
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidKeySpecException, ClassNotFoundException
    {

        Main.log("Reading bytes from file...");
        FileInputStream fis = new FileInputStream(this.file);
        ByteArrayInputStream bais = new ByteArrayInputStream(new Hex(new String(fis.readAllBytes())).getBytes());
        fis.close();

        Main.log("Reading wrapped key...");
        ObjectInputStream ois = new ObjectInputStream(bais);
        byte[] keywrap = (byte[]) ois.readObject();
        ois.close();

        Main.log("Initializing cipher...");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.UNWRAP_MODE, getKeyFromPassphrase(this.passphrase));

        Main.log("Unwrapping key...");
        SecretKey key = (SecretKey) cipher.unwrap(keywrap, "AES", Cipher.SECRET_KEY);

        Main.log("Re-Initializing cipher...");
        cipher.init(Cipher.DECRYPT_MODE, key);

        CipherInputStream cis = new CipherInputStream(bais, cipher);

        Main.log("Decrypting content...");
        ObjectInputStream ocis = new ObjectInputStream(cis);
        this.content = (byte[]) ocis.readObject();

        ocis.close();
    }

    public byte[] getContent()
    {
        return this.content;
    }
}