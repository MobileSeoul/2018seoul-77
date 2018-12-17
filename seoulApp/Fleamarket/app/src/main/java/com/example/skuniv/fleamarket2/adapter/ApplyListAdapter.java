package com.example.skuniv.fleamarket2.adapter;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.databinding.ApplyListItemBinding;
import com.example.skuniv.fleamarket2.view.adminView.ApplyDialog;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.AdminCommand;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.ApplyItemViewModel;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.ApplyMetaViewModel;

public class ApplyListAdapter extends RecyclerView.Adapter<ApplyViewHolder> {
    public ObservableArrayList<ApplyItemViewModel> applyList;
    Context context;
    AdminCommand adminCommand;
    ApplyMetaViewModel applyMetaViewModel;
    ApplyItemViewModel apply;


    public ApplyListAdapter(ObservableArrayList<ApplyItemViewModel> applyList, Context context, AdminCommand adminCommand,
                            ApplyMetaViewModel applyMetaViewModel){
        this.applyList = applyList;
        this.context = context;
        this.adminCommand = adminCommand;
        this.applyMetaViewModel = applyMetaViewModel;
    }
    @Override
    public ApplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ApplyListItemBinding binding = ApplyListItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ApplyViewHolder(binding, context);
    }

    public void setApplyList(ObservableArrayList<ApplyItemViewModel> applyList) {
        this.applyList = applyList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ApplyViewHolder holder, final int position) {
        apply = applyList.get(position);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplyDialog applyDialog = new ApplyDialog(context, applyList.get(position),adminCommand);
                applyDialog.show();
            }
        });
        if(apply.role.get() == 1){
            holder.binding.getRoot().setBackgroundColor(Color.rgb(255,255,255));
        } else if (apply.role.get() == 2){
            holder.binding.getRoot().setBackgroundColor(Color.rgb(178,235,242));
        }else if (apply.role.get() == 3){
            holder.binding.getRoot().setBackgroundColor(Color.rgb(255,204,188));
        }
        holder.bind(apply);
    }
    @Override
    public int getItemCount() {
        return applyList.size();
    }
}

class ApplyViewHolder extends RecyclerView.ViewHolder {
    ApplyListItemBinding binding;
    Context context;

    //ViewHolder 생성
    public ApplyViewHolder(ApplyListItemBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    void bind(ApplyItemViewModel apply) {
        binding.setApply(apply);
    }
}
