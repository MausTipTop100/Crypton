package net.maustiptop100.crypton.crypto;

import net.maustiptop100.crypton.Main;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Utils {

    public static byte[] loadEncryptedFile(File file, char[] password)
            throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, ClassNotFoundException
    {
        EncryptedFile encfile = new EncryptedFile(file, password);
        encfile.readFile();
        Main.log("File was successfully read!");
        return encfile.getContent();
    }

    public static void saveAsEncryptedFile(File file, byte[] bytes, char[] password)
            throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
            InvalidKeyException, IOException, InvalidKeySpecException
    {
        EncryptedFile encfile = new EncryptedFile(file, bytes, password);
        encfile.writeFile();
        Main.log("File was successfully wrote!");
    }

    public static void encryptFile(File file, char[] password)
            throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException
    {
        FileInputStream fis = new FileInputStream(file);
        Main.log("Reading content...");
        byte[] content = fis.readAllBytes();
        fis.close();
        Main.log("Deleting old file...");
        file.delete();
        Main.log("Creating new file...");
        File newfile = new File(file.getAbsolutePath() + ".encf");
        Main.log("Starting encryption task...");
        saveAsEncryptedFile(newfile, content, password);
    }

    public static void decryptFile(File file, char[] password)
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidKeySpecException, ClassNotFoundException
    {
        if(file.getName().endsWith(".encf"))
        {
            Main.log("Starting decryption task...");
            byte[] bytes = loadEncryptedFile(file, password);
            Main.log("Deleting old file...");
            file.delete();
            Main.log("Creating new file...");
            File newfile = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length()-5));
            newfile.createNewFile();
            Main.log("Writing to new file...");
            try(var fos = new FileOutputStream(newfile))
            { fos.write(bytes); }
        }
        else
            throw new IllegalArgumentException("File isn't a supported encrypted file!");
    }

}
