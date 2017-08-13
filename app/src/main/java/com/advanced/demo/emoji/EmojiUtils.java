package com.advanced.demo.emoji;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author by morton_ws on 2017/8/12.
 */

public class EmojiUtils {

    private static String sEmojiJson = "{\"list\":[{\"content\":\"1F601\",\"id\":\"e1\",\"tip\":\"\"},{\"content\":\"1F602\",\"id\":\"e2\",\"tip\":\"\"},{\"content\":\"1F603\",\"id\":\"e3\",\"tip\":\"\"},{\"content\":\"1F604\",\"id\":\"e4\",\"tip\":\"\"},{\"content\":\"1F605\",\"id\":\"e5\",\"tip\":\"\"},{\"content\":\"1F606\",\"id\":\"e6\",\"tip\":\"\"},{\"content\":\"1F609\",\"id\":\"e7\",\"tip\":\"\"},{\"content\":\"1F60A\",\"id\":\"e8\",\"tip\":\"\"},{\"content\":\"1F60B\",\"id\":\"e9\",\"tip\":\"\"},{\"content\":\"1F60C\",\"id\":\"e10\",\"tip\":\"\"},{\"content\":\"1F60D\",\"id\":\"e11\",\"tip\":\"\"},{\"content\":\"1F60F\",\"id\":\"e12\",\"tip\":\"\"},{\"content\":\"1F612\",\"id\":\"e13\",\"tip\":\"\"},{\"content\":\"1F613\",\"id\":\"e14\",\"tip\":\"\"},{\"content\":\"1F614\",\"id\":\"e15\",\"tip\":\"\"},{\"content\":\"1F616\",\"id\":\"e16\",\"tip\":\"\"},{\"content\":\"1F618\",\"id\":\"e17\",\"tip\":\"\"},{\"content\":\"1F61A\",\"id\":\"e18\",\"tip\":\"\"},{\"content\":\"1F61B\",\"id\":\"e19\",\"tip\":\"\"},{\"content\":\"1F61C\",\"id\":\"e20\",\"tip\":\"\"},{\"content\":\"1F61D\",\"id\":\"e21\",\"tip\":\"\"},{\"content\":\"1F61E\",\"id\":\"e22\",\"tip\":\"\"},{\"content\":\"1F620\",\"id\":\"e23\",\"tip\":\"\"},{\"content\":\"1F621\",\"id\":\"e24\",\"tip\":\"\"},{\"content\":\"1F622\",\"id\":\"e25\",\"tip\":\"\"},{\"content\":\"1F623\",\"id\":\"e26\",\"tip\":\"\"},{\"content\":\"1F624\",\"id\":\"e27\",\"tip\":\"\"},{\"content\":\"1F625\",\"id\":\"e28\",\"tip\":\"\"},{\"content\":\"1F628\",\"id\":\"e29\",\"tip\":\"\"},{\"content\":\"1F629\",\"id\":\"e30\",\"tip\":\"\"},{\"content\":\"1F62A\",\"id\":\"e31\",\"tip\":\"\"},{\"content\":\"1F62B\",\"id\":\"e32\",\"tip\":\"\"},{\"content\":\"1F62D\",\"id\":\"e33\",\"tip\":\"\"},{\"content\":\"1F630\",\"id\":\"e34\",\"tip\":\"\"},{\"content\":\"1F631\",\"id\":\"e35\",\"tip\":\"\"},{\"content\":\"1F632\",\"id\":\"e36\",\"tip\":\"\"},{\"content\":\"1F633\",\"id\":\"e37\",\"tip\":\"\"},{\"content\":\"1F635\",\"id\":\"e38\",\"tip\":\"\"},{\"content\":\"1F637\",\"id\":\"e39\",\"tip\":\"\"},{\"content\":\"1F638\",\"id\":\"e40\",\"tip\":\"\"},{\"content\":\"1F63A\",\"id\":\"e41\",\"tip\":\"\"},{\"content\":\"1F63B\",\"id\":\"e42\",\"tip\":\"\"},{\"content\":\"1F63C\",\"id\":\"e43\",\"tip\":\"\"},{\"content\":\"1F63D\",\"id\":\"e44\",\"tip\":\"\"},{\"content\":\"1F63E\",\"id\":\"e45\",\"tip\":\"\"},{\"content\":\"1F63F\",\"id\":\"e46\",\"tip\":\"\"},{\"content\":\"1F640\",\"id\":\"e47\",\"tip\":\"\"},{\"content\":\"1F645\",\"id\":\"e48\",\"tip\":\"\"},{\"content\":\"1F646\",\"id\":\"e49\",\"tip\":\"\"},{\"content\":\"1F647\",\"id\":\"e50\",\"tip\":\"\"},{\"content\":\"1F648\",\"id\":\"e51\",\"tip\":\"\"},{\"content\":\"1F649\",\"id\":\"e52\",\"tip\":\"\"},{\"content\":\"1F64A\",\"id\":\"e53\",\"tip\":\"\"},{\"content\":\"1F64B\",\"id\":\"e54\",\"tip\":\"\"},{\"content\":\"1F64C\",\"id\":\"e55\",\"tip\":\"\"},{\"content\":\"1F64D\",\"id\":\"e56\",\"tip\":\"\"},{\"content\":\"1F64E\",\"id\":\"e57\",\"tip\":\"\"},{\"content\":\"1F64F\",\"id\":\"e58\",\"tip\":\"\"}]}";

    public static String getEmoji(String emojiCode) {
        if (TextUtils.isEmpty(emojiCode)) {
            return "";
        }
        int emojiInt = Integer.valueOf(emojiCode, 16);
        char[] emojiChar = Character.toChars(emojiInt);
        return new String(emojiChar);
    }

    public static EmojiBean getEmojiData() {
        return JSON.parseObject(sEmojiJson, EmojiBean.class);
    }

    public static List<EmojiBean.Emoji> getEmojiList(EmojiBean emojiBean) {
        return emojiBean.list.subList(0, 8);
    }
}
