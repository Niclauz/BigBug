package wang.xvip.bigbug.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/4/25 - 11:27.
 */

public class ByteUtils {
    /**
     * convert byte[] to Object
     *
     * @param bytes
     * @return
     */
    public static Object byteToObject(byte[] bytes) throws Exception {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } finally {
            if (ois != null) ois.close();
        }
    }

    /**
     * convert Object to byte[]
     *
     * @param obj
     * @return
     */
    public static byte[] objectToByte(Object obj) throws Exception {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return bos.toByteArray();
        } finally {
            if (oos != null) oos.close();
        }
    }

    /**
     * append byte to bit
     *
     * @param bytes
     * @param sb
     */
    public static void byteToBit(byte[] bytes, StringBuilder sb) {
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
    }


    /**
     * convert byte to bit
     *
     * @param bytes
     * @return
     */
    public static String byteToBit(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

}
