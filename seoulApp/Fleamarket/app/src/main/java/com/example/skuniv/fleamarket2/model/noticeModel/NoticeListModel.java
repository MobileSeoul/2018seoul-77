package com.example.skuniv.fleamarket2.model.noticeModel;

import java.util.ArrayList;
import java.util.List;

public class NoticeListModel {
    public int page;
    String type;
    String keyword="";

    public NoticeListModel(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
