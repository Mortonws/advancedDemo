package com.advanced.demo.edittextUI;

import android.widget.EditText;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by sangw on 2017/8/22.
 */

public class EditTextUIActivity extends BaseActivity {
    private EditText mContentInput;
    private TextView mJsonContent;

    private final static String JSON_CONTENT = "{\n" +
            "   \"action\":\"\",\n" +
            "   \"code\": 0,\n" +
            "   \"data\": {\n" +
            "     \"user_data_config\": {\n" +
            "       \"c_data_swich\": \"1\",\n" +
            "       \"jr_app_register\": \"0\",\n" +
            "       \"jr_app_login\": \"1\"\n" +
            "     }\n" +
            "   },\n" +
            "   \"message\": \"\"\n" +
            "}";

    private StringBuilder mJsonSB = new StringBuilder();

    @Override
    protected void initView() {
        super.initView();
        mContentInput = (EditText) findViewById(R.id.content_input);
        mJsonContent = (TextView) findViewById(R.id.json_content);
    }

    @Override
    protected void initPages() {
        super.initPages();
        JSONObject jsonObject = JSON.parseObject(JSON_CONTENT);
        String dataStr = jsonObject.getString("data");
        JSONObject configObject = JSON.parseObject(dataStr);
        String configStr = configObject.getString("user_data_config");
        mJsonSB.append("dataValue:").append(dataStr).append("。\n");
        mJsonSB.append("------------\n");
        mJsonSB.append("configValue:").append(configStr).append("\n");
        JSONObject configValueObject = JSON.parseObject(configStr);
        Map<String, String> configMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : configValueObject.entrySet()) {
            configMap.put(entry.getKey(), entry.getValue().toString());
        }
        mJsonSB.append("-----------\n");
        mJsonSB.append("configMapValue:").append(configMap.toString()).append("。").append("\n");
        mJsonContent.setText(mJsonSB.toString());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_edittext_ui;
    }
}
