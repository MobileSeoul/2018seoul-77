package com.example.skuniv.fleamarket2.adapter;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skuniv.fleamarket2.databinding.NoticeItemBinding;
import com.example.skuniv.fleamarket2.databinding.NoticeListItemBinding;
import com.example.skuniv.fleamarket2.view.noticeView.NoticeItemDialog;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeCommand;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeFilesViewModel;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeItemViewModel;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeMetaViewModel;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG =0;
    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager linearLayoutManager;

    private boolean isMoreLoading = false;
    private int visibleThreshold = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;
    NoticeFilesViewModel noticeFilesViewModel;


    public ObservableArrayList<NoticeItemViewModel> noticeList;
    Context context;
    NoticeCommand noticeCommand;
    NoticeMetaViewModel noticeMetaViewModel;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    public NoticeListAdapter(ObservableArrayList<NoticeItemViewModel> noticeList, Context context, NoticeCommand noticeCommand,
                             OnLoadMoreListener onLoadMoreListener,NoticeMetaViewModel noticeMetaViewModel){
        this.noticeList = noticeList;
        this.context = context;
        this.noticeCommand = noticeCommand;
        this.onLoadMoreListener = onLoadMoreListener;
        this.noticeMetaViewModel = noticeMetaViewModel;
//        System.out.println("==============="+shopsList.get(1));
    }

    public void setRecyclerView(RecyclerView mView){
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Log.d("total", totalItemCount + "");
                Log.d("visible", visibleItemCount + "");

                Log.d("first", firstVisibleItem + "");
                Log.d("last", lastVisibleItem + "");

                if (noticeMetaViewModel.getCount().get() > totalItemCount) {
                    if (!isMoreLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                            System.out.println("onLoadMore()------");
                        }
                        isMoreLoading = true;
                    }
                }
            }
        });
    }

    // 현재 로딩중이면 프로그레스바 리턴 or 아니면 아이템 리턴
    /*@Override
    public int getItemViewType(int position) {
        return itemList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }*/

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //만약 타입이 view_item이면 아이템 추가
        //if(viewType == VIEW_ITEM)
        NoticeListItemBinding binding = NoticeListItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new NoticeViewHolder(binding, context);
        //아니면 프로그레스바 아이템 추가
        //else{
    }

    public void setNoiceList(ObservableArrayList<NoticeItemViewModel> noticeList) {
        this.noticeList = noticeList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final NoticeViewHolder holder, int position) {
        final NoticeItemViewModel notice = noticeList.get(position);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoticeItemDialog dialog = new NoticeItemDialog(context,notice,noticeCommand);
                dialog.show();
            }
        });
        holder.bind(notice);
    }

    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading=isMoreLoading;
    }

    void setItem(ObservableArrayList<NoticeItemViewModel> noticeList) {
        if (noticeList == null) {
            return;
        }
        this.noticeList = noticeList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }
}


class NoticeViewHolder extends RecyclerView.ViewHolder {
    NoticeListItemBinding binding;
    Context context;

    //ViewHolder 생성
    public NoticeViewHolder(NoticeListItemBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    void bind(NoticeItemViewModel notice) {
        Log.i("bind", "=======" + notice.title.get());
        //Glide.with(context).load(shop.goods.get().getImage()).override(300, 300).into(binding.imageId);
        binding.setNotice(notice);
    }
}