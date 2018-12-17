package com.example.skuniv.fleamarket2.model.noticeModel;

import java.util.List;

public class NoticeModel {
    int no;
    String title;
    String date;
    String contents;
    List<FilesModel> files;




    public NoticeModel(int no, String title, String date, String contents, List<FilesModel> files){
        this.no = no;
        this.title = title;
        this.date = date;
        this.contents = contents;
        this.files = files;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<FilesModel> getFiles() {
        return files;
    }

    public void setFiles(List<FilesModel> files) {
        this.files = files;
    }
}
