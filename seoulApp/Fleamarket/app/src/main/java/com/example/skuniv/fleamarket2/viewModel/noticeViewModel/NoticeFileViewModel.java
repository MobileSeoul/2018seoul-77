package com.example.skuniv.fleamarket2.viewModel.noticeViewModel;

import android.databinding.ObservableField;

public class NoticeFileViewModel {
    public final ObservableField<String> fileName = new ObservableField<>();
    public final ObservableField<String> filePath = new ObservableField<>();

    public NoticeFileViewModel(String fileName, String filePath) {
        this.fileName.set(fileName);
        this.filePath.set(filePath);
    }

    public ObservableField<String> getFileName() {
        return fileName;
    }



    public ObservableField<String> getFilePath() {
        return filePath;
    }
}
