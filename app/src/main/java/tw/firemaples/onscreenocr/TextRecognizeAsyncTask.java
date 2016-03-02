package tw.firemaples.onscreenocr;

import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.util.List;

import tw.firemaples.onscreenocr.utils.Tool;

/**
 * Created by firem_000 on 2016/3/2.
 */
public class TextRecognizeAsyncTask extends AsyncTask<Void, String, Boolean> {

    private final Context context;
    private final TessBaseAPI baseAPI;
    private final CaptureViewHandler captureViewHandler;
    private final List<Rect> boxList;

    private OnTextRecognizeAsyncTaskCallback callback;

    public TextRecognizeAsyncTask(Context context, TessBaseAPI baseAPI, CaptureViewHandler captureViewHandler, List<Rect> boxList) {
        this.context = context;
        this.baseAPI = baseAPI;
        this.captureViewHandler = captureViewHandler;
        this.boxList = boxList;
    }

    public TextRecognizeAsyncTask setCallback(OnTextRecognizeAsyncTaskCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onProgressUpdate("Orc recognizing...");

        if (callback == null) {
            throw new UnsupportedOperationException("Callback is not implemented");
        }
        callback.onCaptureScreenStart();
//        View v1 = ((Service)context).getWindow().getDecorView().getRootView();
//        v1.setDrawingCacheEnabled(true);
//        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
//        v1.setDrawingCacheEnabled(false);
        callback.onCaptureScreenEnd();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Tool.LogInfo(values[0]);
        captureViewHandler.setProgressMode(true, values[0]);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        captureViewHandler.setProgressMode(false, null);
    }

    public interface OnTextRecognizeAsyncTaskCallback {
        void onTextRecognizeFinished();

        void onCaptureScreenStart();

        void onCaptureScreenEnd();
    }
}