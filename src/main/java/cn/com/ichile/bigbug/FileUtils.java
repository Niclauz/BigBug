package cn.com.ichile.bigbug;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/4/25 - 11:35.
 */

public class FileUtils {
    public static final String ROOT_DIR = "nick";
    public static final String DOWNLOAD_DIR = "download";
    public static final String CACHE_DIR = "cache";
    public static final String ICON_DIR = "icon";

    public static boolean isSdAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static String getDownloadDir() {
        return getDir(DOWNLOAD_DIR);
    }

    public static String getRootDir() {
        return getDir(ROOT_DIR);
    }

    public static String getCacheDir() {
        return getDir(ROOT_DIR);
    }

    public static String getIconDir() {
        return getDir(ICON_DIR);
    }

    private static String getDir(String dirName) {
        String dir = null;
        StringBuilder stringBuilder = new StringBuilder();
        if (isSdAvailable()) {
            stringBuilder.append(getExternalStoragePath());
        } else {
            stringBuilder.append(getCachePath());
        }
        stringBuilder.append(dirName);
        stringBuilder.append(File.separator);
        String path = stringBuilder.toString();
        if (createDir(path)) {
            dir = path;
        } else {
            dir = null;
        }
        return dir;
    }


    private static boolean createDir(String path) {
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * get app dir in sd
     *
     * @return
     */
    public static String getExternalStoragePath() {
        String path = null;
        StringBuilder stringBuilder = new StringBuilder();
        if (isSdAvailable()) {
            stringBuilder.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            stringBuilder.append(File.separator);
            stringBuilder.append(ROOT_DIR);
            stringBuilder.append(File.separator);
            path = stringBuilder.toString();
        }
        return path;
    }

    public static String getCachePath() {
        File cacheDir = null;
        // cacheDir = App.getContext().getCacheDir();
        if (null == cacheDir) {
            return null;
        } else {
            return cacheDir.getAbsolutePath() + "/";
        }
    }

    public static boolean copyFile(String src, String dest, boolean deleteSrc) {
        File srcFile = new File(src);
        File destFile = new File(dest);
        return copyFile(srcFile, destFile, deleteSrc);
    }

    /**
     * copy file
     *
     * @param src
     * @param dest
     * @param deleteSrc
     * @return
     */
    public static boolean copyFile(File src, File dest, boolean deleteSrc) {
        if (!src.exists() || !dest.exists() || src.isDirectory() || dest.isDirectory())
            return false;

        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];
            int i = 1;
            while ((i = is.read(buffer)) > 0) {
                os.write(buffer, 0, i);
                os.flush();
            }
            if (deleteSrc) {
                src.delete();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    /**
     * the file can be write or not
     *
     * @param path
     * @return
     */
    public static boolean isWriteable(String path) {
        if (TextUtils.isEmpty(path))
            return false;
        File file = new File(path);
        return file.exists() && file.canWrite();
    }

    /**
     * copy single file by file channel,faster then file input stream
     *
     * @param src
     * @param dest
     * @return
     */
    public static boolean fileChannelCopy(File src, File dest) {
        if (!src.exists() || src.isDirectory() || !dest.exists() || dest.isDirectory())
            return false;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fisChannel = null;
        FileChannel fosChannel = null;

        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            fisChannel = fis.getChannel();
            fosChannel = fos.getChannel();
            fisChannel.transferTo(0, fisChannel.size(), fosChannel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
                if (fos != null)
                    fos.close();
                if (fisChannel != null)
                    fisChannel.close();
                if (fosChannel != null)
                    fosChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    /**
     * format file size
     *
     * @param fileLength
     * @return
     */
    public static String formatFileSize(double fileLength) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String stringSize = "0.00";
        if (fileLength < 1024) {
            stringSize = decimalFormat.format(fileLength) + "B";
        } else if (fileLength > 1048576) {
            stringSize = decimalFormat.format(fileLength / 1024) + "K";
        } else if (fileLength > 1073741824) {
            stringSize = decimalFormat.format(fileLength / 1048576) + "M";
        } else {
            stringSize = decimalFormat.format(fileLength / 1073741824) + "G";
        }
        return stringSize;
    }

    /**
     * get file's extension name
     *
     * @param fileName
     * @return
     */
    public static String getExtensionName(String fileName) {
        String extensionName = "NOT_FOUND";
        if (!TextUtils.isEmpty(fileName)) {
            int dot = fileName.lastIndexOf(".");
            if ((dot > -1) && (dot < fileName.length() - 1)) {
                extensionName = fileName.substring(dot + 1);
            }
        }
        return extensionName;
    }

    /**
     * read file to string
     *
     * @param path
     * @return
     */
    public static String readFileToStr(String path) {
        String str = "";
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                str = sb.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null)
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
        return str;
    }

}
