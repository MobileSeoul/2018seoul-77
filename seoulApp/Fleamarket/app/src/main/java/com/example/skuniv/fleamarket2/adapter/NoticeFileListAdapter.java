package com.example.skuniv.fleamarket2.adapter;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.skuniv.fleamarket2.databinding.FileItemBinding;
import com.example.skuniv.fleamarket2.databinding.GoodsItemBinding;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.GoodsViewModel;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeCommand;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeFileViewModel;

public class NoticeFileListAdapter extends RecyclerView.Adapter<NoticeFileViewHolder>{
    Context context;
    NoticeCommand noticeCommand;
    ObservableArrayList<NoticeFileViewModel> fileList;

    public NoticeFileListAdapter(ObservableArrayList<NoticeFileViewModel> fileList, Context context, NoticeCommand noticeCommand){
        this.fileList = fileList;
        this.context = context;
        this.noticeCommand = noticeCommand;
    }

    @Override
    public NoticeFileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FileItemBinding binding = FileItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoticeFileViewHolder(binding, context);
    }

    @Override
    public void onBindViewHolder(NoticeFileViewHolder holder, int position) {
        final NoticeFileViewModel file = fileList.get(position);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeCommand.downlaodFile(file.getFilePath().get());
                Toast.makeText(context,"다운로드",Toast.LENGTH_SHORT).show();
            }
        });
        holder.bind(file);
    }

    void setItem(ObservableArrayList<NoticeFileViewModel> fileList) {
        if (fileList == null) {
            return;
        }
        this.fileList = fileList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }
}


class NoticeFileViewHolder extends RecyclerView.ViewHolder{
    FileItemBinding binding;
    Context context;
    //ViewHolder 생성
    public NoticeFileViewHolder(FileItemBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    void bind(NoticeFileViewModel file) {
        binding.setFile(file);
        //binding.setVariable(BR.goods, goods);
    }
}