package com.example.skuniv.fleamarket2.viewModel.noticeViewModel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.skuniv.fleamarket2.model.noticeModel.FilesModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NoticeItemViewModel {
    public final ObservableField<Integer> no = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> date = new ObservableField<>();
    public final ObservableField<String> contents = new ObservableField<>();
    public final ObservableArrayList<FilesModel> files = new ObservableArrayList<>();

    public ObservableField<Integer> getNo() {
        return no;
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableField<String> getDate() {
        return date;
    }

    public ObservableField<String> getContents() {
        return contents;
    }

    public ObservableArrayList<FilesModel> getFiles() {
        return files;
    }

    public NoticeItemViewModel(int no, String title, String date, String contents, List<FilesModel> files) {
        this.no.set(no);
        this.title.set(title);
        this.date.set(date);
        this.contents.set(contents);
        this.files.addAll(files);

    }
}
