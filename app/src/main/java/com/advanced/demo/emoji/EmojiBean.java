package com.advanced.demo.emoji;

import java.util.List;

/**
 * @author by morton_ws on 2017/8/12.
 */

public class EmojiBean {

    public List<Emoji> list;

    public static class Emoji {
        public String content;
        public String id;
        public String tip;
    }
}
