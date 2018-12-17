package com.example.skuniv.fleamarket2.model.noticeModel;

public class FilesModel {
    String fName;
    String fPath;

    public FilesModel(String fName, String fPath) {
        this.fName = fName;
        this.fPath = fPath;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfPath() {
        return fPath;
    }

    public void setfPath(String fPath) {
        this.fPath = fPath;
    }
}
