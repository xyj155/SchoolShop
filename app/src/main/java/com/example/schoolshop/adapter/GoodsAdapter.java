package com.example.schoolshop.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.contract.GoodDetailContract;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.gson.GoodsDetailGson;
import com.example.schoolshop.gson.GoodsPrice;
import com.example.schoolshop.presenter.GoodDetailPresenter;
import com.example.schoolshop.ui.GoodsDetailActivity;
import com.example.schoolshop.ui.homefragment.SortFragment;
import com.example.schoolshop.util.GlideRoundTransform;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


public class GoodsAdapter extends BaseAdapter implements StickyListHeadersAdapter, GoodDetailContract.View {

    private List<GoodGson.GoodsBean> dataList;
    private SortFragment mContext;
    private NumberFormat nf;
    private LayoutInflater mInflater;


    public GoodsAdapter(List<GoodGson.GoodsBean> dataList, SortFragment mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(mContext.getContext());
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_header_view, parent, false);
            convertView.setVisibility(View.GONE);
        }
        convertView.setVisibility(View.GONE);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return dataList.get(position).getId();
    }

    @Override
    public int getCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }


    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.ry_goods_list_item, parent, false);
            holder = new ItemViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        GoodGson.GoodsBean item = dataList.get(position);
        holder.bindData(item, position);
        return convertView;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadFailed(String msg) {

    }

    @Override
    public void setGoodDetail(GoodsDetailGson commentGson) {

    }

    @Override
    public void setPrice(List<GoodsPrice> price) {

    }


    private class ItemViewHolder {
        private TextView name, price, tvCount;
        private ImageView tvAdd, tvMinus, ivCover;
        private LinearLayout llItem;

        public ItemViewHolder(View itemView) {
            name = (TextView) itemView.findViewById(R.id.tv_name);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvMinus = (ImageView) itemView.findViewById(R.id.iv_reduce);
            tvAdd = (ImageView) itemView.findViewById(R.id.iv_add);
            ivCover = itemView.findViewById(R.id.iv_cover);
            llItem = itemView.findViewById(R.id.ll_item);
        }


        public void bindData(final GoodGson.GoodsBean item, final int position) {
            name.setText(item.getGoods_name());//商品名称
            Glide.with(mContext).load(item.getGoods_pic()).into(ivCover);//商品图片
            tvCount.setText(String.valueOf(item.getNum()));//商品数量
            price.setText(nf.format(item.getGoods_price()));//商品价格
            if (item.getNum() < 1) {
                tvCount.setVisibility(View.GONE);
                tvMinus.setVisibility(View.GONE);
            } else {
                tvCount.setVisibility(View.VISIBLE);
                tvMinus.setVisibility(View.VISIBLE);
            }
            llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext.getContext(), GoodsDetailActivity.class);
                    intent.putExtra("id", String.valueOf(item.getId()));
                    intent.putExtra("kind", String.valueOf(item.getGoods_kind()));
                    mContext.startActivity(intent);
                }
            });
            tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getName().size() > 0) {
                        showBottomDialog(item, tvMinus, tvCount);
                    }else {
                        SortFragment activity = mContext;
                        int count = item.getNum();
                        mContext.goodCarPresent.addGoodsCar("1", String.valueOf(item.getId()), "", "0");
                        if (count < 1) {
                            tvMinus.setAnimation(getShowAnimation());
                            tvMinus.setVisibility(View.VISIBLE);
                            tvCount.setVisibility(View.VISIBLE);
                        }
                        activity.update();
                        count++;
                        int[] loc = new int[2];
                        v.getLocationInWindow(loc);
                        activity.playAnimation(loc);
                        tvCount.setText(String.valueOf(count));
                    }
                }
            });
            tvMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SortFragment activity = mContext;
                    int count = item.getNum();
                    mContext.goodCarPresent.addGoodsCar("1", String.valueOf(item.getId()), "", "1");
                    if (count < 2) {
                        tvMinus.setAnimation(getHiddenAnimation());
                        tvMinus.setVisibility(View.GONE);
                        tvCount.setVisibility(View.GONE);
                    }
                    activity.update();
                    count--;
                    tvCount.setText(String.valueOf(count));
                }
            });
        }

    }

    private Animation getShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    private Animation getHiddenAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    private TextView tvSelect;
    private GoodDetailPresenter goodDetailPresenter;

    public void showBottomDialog(final GoodGson.GoodsBean goodsDetailGson, final ImageView tvMinus, final TextView tvCount) {
        goodDetailPresenter = new GoodDetailPresenter(this);
        goodDetailPresenter.getGoodsDetail("1",String.valueOf(goodsDetailGson.getId()), goodsDetailGson.getKind());
        View contentView = LayoutInflater.from(mContext.getContext())
                .inflate(R.layout.buy_goods_bottom_dialog, null);
        ImageView ivCloase = contentView.findViewById(R.id.iv_close);
        TextView tvNum = contentView.findViewById(R.id.tv_num);
        TextView tvSubmit = contentView.findViewById(R.id.tv_submit);
        RecyclerView ryeKind = (RecyclerView) contentView.findViewById(R.id.ry_kind);
        RecyclerView ryAttribute = (RecyclerView) contentView.findViewById(R.id.ry_attr);
        RecyclerView ryColor = (RecyclerView) contentView.findViewById(R.id.ry_color);
        tvSelect = contentView.findViewById(R.id.tv_select);
        TextView BottmTvPrice = contentView.findViewById(R.id.tv_price);
        TextView tvAttr = contentView.findViewById(R.id.tv_attr);
        TextView tvNoGoods = contentView.findViewById(R.id.tv_no_goods);
        LinearLayout llAttr = contentView.findViewById(R.id.ll_attr);
        TextView tvKind = contentView.findViewById(R.id.tv_kind);
        TextView tvColor = contentView.findViewById(R.id.tv_color);
        ryAttribute.setLayoutManager(new GridLayoutManager(mContext.getContext(), 3));
        ryeKind.setLayoutManager(new GridLayoutManager(mContext.getContext(), 3));
        ryColor.setLayoutManager(new GridLayoutManager(mContext.getContext(), 3));
        if (goodsDetailGson != null) {
            Glide.with(mContext).asBitmap().load(goodsDetailGson.getGoods_pic()).apply(new RequestOptions().transform(new GlideRoundTransform(mContext.getContext(), 7))).into((ImageView) contentView.findViewById(R.id.iv_head));
            //商品种类
            if (goodsDetailGson.getName().size() < 1 && goodsDetailGson.getSize().size() < 1 && goodsDetailGson.getColor().size() < 1) {
                tvNoGoods.setVisibility(View.VISIBLE);
                llAttr.setVisibility(View.GONE);
            } else {
                llAttr.setVisibility(View.VISIBLE);
                tvNoGoods.setVisibility(View.GONE);
                if (goodsDetailGson.getName().size() > 0) {
                    KindAdapter sizeAdapter = new KindAdapter(goodsDetailGson.getName(), goodsDetailGson);
                    ryeKind.setAdapter(sizeAdapter);
                    tvKind.setText(goodsDetailGson.getModelattr());
                } else {
                    tvKind.setVisibility(View.GONE);
                }
                //商品属性
                if (goodsDetailGson.getSize().size() > 0) {
                    AttributeAdapter attrAdapter = new AttributeAdapter(goodsDetailGson.getSize(), goodsDetailGson);
                    ryAttribute.setAdapter(attrAdapter);
                    tvAttr.setText(goodsDetailGson.getSizeattr());

                } else {
                    tvAttr.setVisibility(View.GONE);
                }
                //商品颜色
                if (goodsDetailGson.getColor().size() > 0) {
                    ColorAdapter colorAdapter = new ColorAdapter(goodsDetailGson.getColor(), goodsDetailGson);
                    ryColor.setAdapter(colorAdapter);
                    tvColor.setText(goodsDetailGson.getColorattr());

                } else {
                    tvColor.setVisibility(View.GONE);
                }
            }
            BottmTvPrice.setText("￥ " + goodsDetailGson.getGoods_price());
        }

        final BottomSheetDialog dialog = new BottomSheetDialog(mContext.getContext());
        dialog.setContentView(contentView);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        ivCloase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortFragment activity = mContext;
                int count = goodsDetailGson.getNum();
                mContext.goodCarPresent.addGoodsCar("1", String.valueOf(goodsDetailGson.getId()), kindSelectBuilder.toString() + "  " + colorSelectBuilder.toString() + "  " + attributeSelectBuilder.toString(), "0");
                if (count < 1) {
                    tvMinus.setAnimation(getShowAnimation());
                    tvMinus.setVisibility(View.VISIBLE);
                    tvCount.setVisibility(View.VISIBLE);
                }
                activity.update();
                count++;
                int[] loc = new int[2];
                v.getLocationInWindow(loc);
                activity.playAnimation(loc);
                tvCount.setText(String.valueOf(count));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private StringBuilder attributeSelectBuilder = new StringBuilder();
    private StringBuilder kindSelectBuilder = new StringBuilder();
    private StringBuilder colorSelectBuilder = new StringBuilder();

    private class KindAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        private Map<Integer, Boolean> map = new HashMap<>();
        private boolean onBind;
        private int checkedPosition = -1;
        private GoodGson.GoodsBean goodsDetailGson;

        public KindAdapter(@Nullable List<String> data, GoodGson.GoodsBean goodsDetailGson) {
            super(R.layout.ry_goods_detail_kind_item, data);
            this.goodsDetailGson = goodsDetailGson;
        }


        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            RadioButton view = helper.getView(R.id.tv_tag);
            helper.setText(R.id.tv_tag, item);
            view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        map.clear();
                        map.put(helper.getPosition(), true);
                        checkedPosition = helper.getPosition();
//                        Log.i(TAG, "convert: " + attributeSelectBuilder.length());
                        kindSelectBuilder.delete(0, kindSelectBuilder.length());
                    } else {
                        map.remove(helper.getPosition());
                        if (map.size() == 0) {
                            checkedPosition = -1; //-1 代表一个都未选择
                        }
                    }
                    if (!onBind) {
                        notifyDataSetChanged();
                    }
                }
            });
            onBind = true;
            if (map != null && map.containsKey(helper.getPosition())) {
                helper.setChecked(R.id.tv_tag, true);
                kindSelectBuilder.append(item);
                Log.i(TAG, "convert: " + item);
                tvSelect.setText(kindSelectBuilder.toString() + "  " + colorSelectBuilder.toString() + "  " + attributeSelectBuilder.toString());
                goodDetailPresenter.getGoodsPrice(String.valueOf(goodsDetailGson.getId()),
                        colorSelectBuilder.toString(),
                        kindSelectBuilder.toString(),
                        attributeSelectBuilder.toString());
            } else {
                helper.setChecked(R.id.tv_tag, false);
            }
            onBind = false;
        }
    }

    private class ColorAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        private Map<Integer, Boolean> map = new HashMap<>();
        private boolean onBind;
        private int checkedPosition = -1;

        private GoodGson.GoodsBean goodsDetailGson;

        public ColorAdapter(@Nullable List<String> data, GoodGson.GoodsBean goodsDetailGson) {
            super(R.layout.ry_goods_detail_kind_item, data);
            this.goodsDetailGson = goodsDetailGson;
        }

        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            RadioButton view = helper.getView(R.id.tv_tag);
            helper.setText(R.id.tv_tag, item);
            view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        map.clear();
                        map.put(helper.getPosition(), true);
                        checkedPosition = helper.getPosition();
                        colorSelectBuilder.delete(0, colorSelectBuilder.length());
                    } else {
                        map.remove(helper.getPosition());
                        if (map.size() == 0) {
                            checkedPosition = -1; //-1 代表一个都未选择
                        }
                    }
                    if (!onBind) {
                        notifyDataSetChanged();
                    }
                }
            });
            onBind = true;
            if (map != null && map.containsKey(helper.getPosition())) {
                helper.setChecked(R.id.tv_tag, true);
                colorSelectBuilder.append(item);
                tvSelect.setText(kindSelectBuilder.toString() + "  " + colorSelectBuilder.toString() + "  " + attributeSelectBuilder.toString());
                goodDetailPresenter.getGoodsPrice(String.valueOf(goodsDetailGson.getId()),
                        colorSelectBuilder.toString(),
                        kindSelectBuilder.toString(),
                        attributeSelectBuilder.toString());
            } else {
                helper.setChecked(R.id.tv_tag, false);
            }
            onBind = false;
        }
    }

    private class AttributeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        private Map<Integer, Boolean> map = new HashMap<>();
        private boolean onBind;
        private int checkedPosition = -1;

        private GoodGson.GoodsBean goodsDetailGson;

        public AttributeAdapter(@Nullable List<String> data, GoodGson.GoodsBean goodsDetailGson) {
            super(R.layout.ry_goods_detail_kind_item, data);
            this.goodsDetailGson = goodsDetailGson;
        }


        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            RadioButton view = helper.getView(R.id.tv_tag);
            helper.setText(R.id.tv_tag, item);
            view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        map.clear();
                        map.put(helper.getPosition(), true);
                        checkedPosition = helper.getPosition();
                        attributeSelectBuilder.delete(0, attributeSelectBuilder.length());
                    } else {
                        map.remove(helper.getPosition());
                        if (map.size() == 0) {
                            checkedPosition = -1; //-1 代表一个都未选择
                        }
                    }
                    if (!onBind) {
                        notifyDataSetChanged();
                    }
                }
            });
            onBind = true;
            if (map != null && map.containsKey(helper.getPosition())) {
                helper.setChecked(R.id.tv_tag, true);
                attributeSelectBuilder.append(item);
                tvSelect.setText(kindSelectBuilder.toString() + "  " + colorSelectBuilder.toString() + "  " + attributeSelectBuilder.toString());
                goodDetailPresenter.getGoodsPrice(String.valueOf(goodsDetailGson.getId()),
                        colorSelectBuilder.toString(),
                        kindSelectBuilder.toString(),
                        attributeSelectBuilder.toString());
            } else {
                helper.setChecked(R.id.tv_tag, false);
            }
            onBind = false;
        }
    }
}
