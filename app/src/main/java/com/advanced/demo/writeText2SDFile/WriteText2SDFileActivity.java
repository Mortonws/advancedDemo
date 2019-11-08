package com.advanced.demo.writeText2SDFile;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author by morton_ws on 2019-10-23.
 */
public class WriteText2SDFileActivity extends BaseActivity {

    private Button mBtnSaveFile;
    private EditText mFileContent;
    @Override
    protected void initView() {
        super.initView();
        mBtnSaveFile = findViewById(R.id.btn_save_file);
        mFileContent = findViewById(R.id.file_content);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnSaveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileContentStr = mFileContent.getText().toString();
                if (TextUtils.isEmpty(fileContentStr)) {
                    fileContentStr = "sangw";
                }
                savePKLog2File(fileContentStr);
                Toast.makeText(WriteText2SDFileActivity.this, "Save Successfully", Toast.LENGTH_SHORT).show();
                mFileContent.setText("");
            }
        });
    }

    private final static String LIVE_LOG_PATH = "/advanced/LiveLog/";
    private static String TAG = WriteText2SDFileActivity.class.getSimpleName();

    public static void savePKLog2File(String log) {
        if (!sdCardMounted()) {
            return;
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String time = formatter.format(new Date());

        DateFormat contentTimeFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        String contentTime = contentTimeFormatter.format(new Date());
        StringBuilder logResult = new StringBuilder();
        logResult.append(contentTime);
        logResult.append(" ").append(log);
        logResult.append("\n");

        try {

            String fileName = "live-" + time + ".log";
            String logDir = Environment.getExternalStorageDirectory().getPath() + LIVE_LOG_PATH;

            File dir = new File(logDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File logFile = new File(logDir, fileName);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(logFile, true);
            fos.write(logResult.toString().getBytes());
            fos.close();

        } catch (Exception e) {
            Log.e(TAG, "[LiveLogUtils][savePKLog2File] log=" + log, e);
        }
    }

    /**
     * 检查是否安装了sd卡
     *
     * @return false 未安装
     */
    private static boolean sdCardMounted() {
        final String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED)
                && !state.equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.layout_write_text_2_file;
    }
}
