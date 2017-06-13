package wang.xvip.bigbug.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/4/27 - 14:33.
 */

public class LogWriter extends Thread {

    private static final String LOG_FILE_PATH = "LogWriter/";
    private static final String LOG_ROOT_PATH = "/sdcard/";

    private static LogWriter instance = null;
    private static Process mLogWriterProcess = null;

    private static String mPackageName = "*";
    private BufferedReader mBufferedReader;
    private BufferedWriter mBufferedWriter;

    public static void startCatchLog(String packageName) {
        if (instance == null) {
            instance = new LogWriter();
            mPackageName = packageName;
            instance.start();
        }
    }

    public static void stopCatchLog() {
        if (mLogWriterProcess != null) {
            mLogWriterProcess.destroy();
            mLogWriterProcess = null;
        }
    }

    @Override
    public void run() {
        super.run();

        try {
            mLogWriterProcess = Runtime.getRuntime().exec("logcat " + mPackageName + ":E");

            mBufferedReader = new BufferedReader(new InputStreamReader(mLogWriterProcess
                    .getInputStream()));


            String fileName = LOG_ROOT_PATH + LOG_FILE_PATH + Calendar.DAY_OF_YEAR + ".log";
            File file = new File(fileName);
            if (!file.exists() && !file.isDirectory()) {
                file.createNewFile();
            }
            mBufferedWriter = new BufferedWriter(new FileWriter(file, true));
            String line;
            while ((line = mBufferedReader.readLine()) != null) {
                mBufferedWriter.append(line);
                mBufferedWriter.newLine();
                mBufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mBufferedWriter != null) try {
                mBufferedWriter.close();
                mBufferedWriter = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mBufferedReader != null) try {
                mBufferedReader.close();
                mBufferedReader = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        instance = null;
    }
}
