package io.wollinger.hkmanager;

import gnu.crypto.mode.IMode;
import gnu.crypto.mode.ModeFactory;
import gnu.crypto.pad.IPad;
import gnu.crypto.pad.PadFactory;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SaveLoader {
    private static final String CIPHER_MODE = "ECB";
    private static final String CIPHER_ALGO = "Rijndael";
    private static final String CIPHER_PAD  = "PKCS7";
    private static final byte[] RIJNDAEL_KEY = "UKu52ePUBwetZ9wNX88o54dnfKRu0T1l".getBytes();
    private static final int BLOCK_SIZE = 16;

    private static byte[] crypt(byte[] input) throws Exception{
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(IMode.KEY_MATERIAL, RIJNDAEL_KEY);
        attributes.put(IMode.CIPHER_BLOCK_SIZE, BLOCK_SIZE);
        attributes.put(IMode.STATE, IMode.DECRYPTION);

        IMode mode = ModeFactory.getInstance(CIPHER_MODE, CIPHER_ALGO, BLOCK_SIZE);
        mode.init(attributes);

        int bs = mode.currentBlockSize();

        IPad padding = PadFactory.getInstance(CIPHER_PAD);
        padding.init(bs);
        byte[] pad = padding.pad(input, 0, input.length);
        byte[] pt = new byte[input.length + pad.length];
        byte[] ct = new byte[pt.length];

        System.arraycopy(input, 0, pt, 0, input.length);
        System.arraycopy(pad, 0, pt, input.length, pad.length);

        for (int i = 0; i + bs < pt.length; i += bs)
            mode.update(pt, i, ct, i);

        int unpad = padding.unpad(ct, 0, ct.length);
        byte[] output = new byte[ct.length - unpad];
        System.arraycopy(ct, 0, output, 0, ct.length);
        return output;
    }

    private static byte[] decrypt(String input) throws Exception {
        byte[] message = input.getBytes();
        byte[] tmp = Arrays.copyOfRange(message, 0, message.length-1);
        return crypt(Base64.getMimeDecoder().decode(new String(tmp)));
    }

    public static JSONObject loadSave(File dir) throws Exception {
        String str = new String(Files.readAllBytes(dir.toPath()), StandardCharsets.UTF_8);
        if(str.charAt(0) == '{')
            return new JSONObject(str.trim());
        else
            return new JSONObject(new String(decrypt(str)).trim());
    }
}
