package wang.xvip.bigbug.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/4/25 - 10:45.
 */

public abstract class SimpleAsyncTask {

    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(android.os.Message msg) {
            onPostExecute();
        }
    };

    /**
     * 耗时任务执行之前调用的方法
     */
    public void onPreExecute() {

    }

    /**
     * 耗时任务执行完成之后调用的方法
     */
    public void onPostExecute() {

    }

    /**
     * 在后台执行一个耗时任务
     */
    public abstract void doInBackground();

    /**
     * 开启一个异步任务
     */
    public void execute() {
        onPreExecute();
        new Thread() {
            public void run() {
                doInBackground();
                handler.sendEmptyMessage(0);
            }

        }.start();

    }
}
