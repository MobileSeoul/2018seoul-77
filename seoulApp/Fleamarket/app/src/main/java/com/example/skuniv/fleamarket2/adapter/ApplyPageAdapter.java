package com.example.skuniv.fleamarket2.adapter;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skuniv.fleamarket2.databinding.PageItemBinding;
import com.example.skuniv.fleamarket2.model.AdminSellerModel.ApplyListModel;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.AdminCommand;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.ApplyMetaViewModel;

public class ApplyPageAdapter extends RecyclerView.Adapter<ApplyPageHolder>{
    Context context;
    AdminCommand adminCommand;
    ApplyMetaViewModel applyMetaViewModel;
    ApplyListModel applyListModel;
    public ObservableArrayList<Integer> pageList;

    public ApplyPageAdapter(Context context, AdminCommand adminCommand,ApplyListModel applyListModel,
                            ApplyMetaViewModel applyMetaViewModel){
        this.context = context;
        this.adminCommand = adminCommand;
        this.applyMetaViewModel = applyMetaViewModel;
        this.applyListModel = applyListModel;
        pageList = applyMetaViewModel.pageList;
    }

    @Override
    public ApplyPageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PageItemBinding binding = PageItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ApplyPageHolder(binding, context);
    }

    @Override
    public void onBindViewHolder(ApplyPageHolder holder, final int position) {
        int page = pageList.get(position);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyListModel.setPage(position+1);
                notifyDataSetChanged();
                adminCommand.getApply();
            }
        });
        holder.bind(page);
    }

    public void setPageList(ObservableArrayList<Integer> pageList) {
        this.pageList = pageList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return applyMetaViewModel.pageList.size();
    }
}

class ApplyPageHolder extends RecyclerView.ViewHolder {
    PageItemBinding binding;
    Context context;

    //ViewHolder 생성
    public ApplyPageHolder(PageItemBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    void bind(Integer page) {
        //Glide.with(context).load(shop.goods.get().getImage()).override(300, 300).into(binding.imageId);
        binding.pageId.setText(""+page);
    }
}
