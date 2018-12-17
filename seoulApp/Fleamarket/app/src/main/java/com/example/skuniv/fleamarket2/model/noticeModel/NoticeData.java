package com.example.skuniv.fleamarket2.model.noticeModel;

import java.util.List;

public class NoticeData {
    List<NoticeModel> items;
    NoticeMeta meta;

    public List<NoticeModel> getItems() {
        return items;
    }

    public void setItems(List<NoticeModel> items) {
        this.items = items;
    }

    public NoticeMeta getMeta() {
        return meta;
    }

    public void setMeta(NoticeMeta meta) {
        this.meta = meta;
    }
}
