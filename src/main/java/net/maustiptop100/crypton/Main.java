package net.maustiptop100.crypton;

import net.maustiptop100.crypton.crypto.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static final void log(String msg)
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        System.out.println("[ " + format.format(new Date()) + " / INFO ] " + msg);
    }

    public static final void logerr(String msg)
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        System.err.println("[ " + format.format(new Date()) + " / ERR ] " + msg);
    }

    public static void main(String[] args) throws Exception
    {
        if(args.length != 0)
        {
            List<HashMap<String, File>> actions = new Vector<HashMap<String, File>>();
            char[] passphrase = "default".toCharArray();

            log("Computing tasks...");

            for(int i = 0; i < args.length; i++)
            {
                String argument = args[i];
                if(argument.equalsIgnoreCase("--encrypt-file"))
                {
                    String arg = args[i+1];
                    if(args[i+1].startsWith("\""))
                    {
                        for(String nextarg : args)
                        {
                            arg += " " + nextarg;
                            if(arg.endsWith("\""))
                                break;
                        }
                        arg.replaceAll("\"", "");
                    }
                    HashMap<String, File> map = new HashMap<String, File>();
                    map.put("ENCRYPT", new File(arg));
                    actions.add(map);
                }
                else if(argument.equalsIgnoreCase("--decrypt-file"))
                {
                    String arg = args[i+1];
                    if(args[i+1].startsWith("\""))
                    {
                        for(String nextarg : args)
                        {
                            arg += " " + nextarg;
                            if(arg.endsWith("\""))
                                break;
                        }
                        arg.replaceAll("\"", "");
                    }
                    HashMap<String, File> map = new HashMap<String, File>();
                    map.put("DECRYPT", new File(arg));
                    actions.add(map);
                }
                else if(argument.equalsIgnoreCase("--passphrase"))
                {
                    String arg = args[i+1];
                    if(args[i+1].startsWith("\""))
                    {
                        for(String nextarg : args)
                        {
                            arg += " " + nextarg;
                            if(arg.endsWith("\""))
                                break;
                        }
                        arg.replaceAll("\"", "");
                    }
                    passphrase = arg.toCharArray();
                }
            }

            long start = System.currentTimeMillis();

            log("Starting tasks...");

            // EXECUTE COMMANDS
            for(HashMap<String, File> action : actions)
            {
                File file;
                if((file = action.get("ENCRYPT")) != null)
                {
                    Utils.encryptFile(file, passphrase);
                }
                else if((file = action.get("DECRYPT")) != null)
                {
                    Utils.decryptFile(file, passphrase);
                }
            }

            log("All tasks done! (" + (System.currentTimeMillis() - start) + " ms)");
        }
    }
}
