package com.example.skuniv.fleamarket2.viewModel.noticeViewModel;

import android.databinding.ObservableArrayList;

import com.example.skuniv.fleamarket2.model.noticeModel.FilesModel;

import java.util.List;

public class NoticeFilesViewModel {
    public final ObservableArrayList<NoticeFileViewModel> fileList = new ObservableArrayList<>();

    public void setFileList(List<FilesModel> fileList){
        for(int i =0; i<fileList.size();i++){
            this.fileList.add(new NoticeFileViewModel(fileList.get(i).getfName(),fileList.get(i).getfPath()));
        }
    }

    public ObservableArrayList<NoticeFileViewModel> fileList() {
        return fileList;
    }
}
