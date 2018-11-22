package com.example.schoolshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.GoodCarContract;
import com.example.schoolshop.contract.GoodDetailContract;
import com.example.schoolshop.contract.OrderContract;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.gson.GoodsDetailGson;
import com.example.schoolshop.gson.GoodsPrice;
import com.example.schoolshop.gson.UserOrderGson;
import com.example.schoolshop.presenter.GoodCarPresenter;
import com.example.schoolshop.presenter.GoodDetailPresenter;
import com.example.schoolshop.presenter.OrderPresenter;
import com.example.schoolshop.util.GlideRoundTransform;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GoodsDetailActivity extends BaseActivity implements GoodDetailContract.View, OrderContract.View, GoodCarContract.View {


    @InjectView(R.id.iv_cover)
    ImageView ivCover;
    @InjectView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @InjectView(R.id.ry_tags)
    RecyclerView ryTags;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.tv_describe)
    TextView tvDescribe;
    @InjectView(R.id.ry_comment)
    RecyclerView ryComment;
    @InjectView(R.id.tv_comment)
    TextView tvComment;
    @InjectView(R.id.sl_head)
    SmartRefreshLayout slHead;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.tv_choose)
    TextView tvChoose;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;
    @InjectView(R.id.ll_count)
    RelativeLayout llCount;
    @InjectView(R.id.iv_shopcar)
    ImageView ivShopcar;
    @InjectView(R.id.tv_shopcar_count)
    TextView tvShopcarCount;
    @InjectView(R.id.fl_goods)
    FrameLayout flGoods;
    @InjectView(R.id.bottomSheetLayout)
    BottomSheetLayout bottomSheetLayout;
    private GoodsDetailGson goodsDetailGson;
    private GoodDetailPresenter goodDetailPresenter = new GoodDetailPresenter(this);
    private OrderPresenter orderPresenter = new OrderPresenter(this);
    public GoodCarPresenter getGoodCarPresent = new GoodCarPresenter(this);
    private View sheetDialog;
    private RecyclerView ryShopCar;

    @Override
    public int intiLayout() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        notSetStatusBarColor();
    }

    @Override
    public void initData() {
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        Log.i(TAG, "initData: " + getIntent().getStringExtra("id"));
        goodDetailPresenter.getGoodsDetail(getIntent().getStringExtra("id"), getIntent().getStringExtra("kind"));
        getGoodCarPresent.getShopCarGoodsList("1");
        slHead.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                goodDetailPresenter.getGoodsDetail(getIntent().getStringExtra("id"), getIntent().getStringExtra("kind"));
            }
        });

        sheetDialog = LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.recycler_view, null, false);
        ryShopCar = sheetDialog.findViewById(R.id.ry_shopcar);
        ryShopCar.setLayoutManager(new LinearLayoutManager(GoodsDetailActivity.this));
        ryShopCar.setAdapter(shopCarAdapter);
        TextView viewById = sheetDialog.findViewById(R.id.tv_clean);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodCarPresent.deleteUserShopCar("1");
                shopCarAdapter.notifyDataSetChanged();
            }
        });

        shopCarAdapter.bindToRecyclerView(ryShopCar);
    }

    @Override
    protected int setStatusBarColor() {
        return 0xfffafafa;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @Override
    public void showLoading() {
        showDialog();
    }

    @Override
    public void hideLoading() {
        dialogClose();
    }

    @Override
    public void loadFailed(String msg) {
        Toast.makeText(this, "获取商品信息出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setGoodDeail(GoodsDetailGson commentGson) {
        slHead.finishRefresh();
        GoodsId = String.valueOf(commentGson.getGoods().getId());
        Glide.with(GoodsDetailActivity.this).asBitmap().load(commentGson.getGoods().getGoods_pic()).into(ivCover);
        tvGoodsName.setText(commentGson.getGoods().getGoods_name());
        tvDescribe.setText(commentGson.getGoods().getGoods_describe());
        tvPrice.setText("￥" + commentGson.getGoods().getGoods_price());
        String goods_tags = commentGson.getGoods().getGoods_tags();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GoodsDetailActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ryTags.setLayoutManager(linearLayoutManager);
        List<String> strings = Arrays.asList(goods_tags.split(","));
        TagAdapter goodsAdapter = new TagAdapter(strings);
        ryTags.setAdapter(goodsAdapter);
        tvComment.setText("用户评价(" + commentGson.getComment().size() + ")条");
        CommentAdapter comment = new CommentAdapter(commentGson.getComment());
        ryComment.setLayoutManager(new LinearLayoutManager(GoodsDetailActivity.this));
        ryComment.setAdapter(comment);
        goodsDetailGson = commentGson;
    }

    @Override
    public void setPrice(List<GoodsPrice> price) {
        Log.i(TAG, "setPrice: " + price);
        if (BottmTvPrice != null) {
            if (price.size() > 0) {
                Log.i(TAG, "setPrice: " + price);
                BottmTvPrice.setText("￥ " + price.get(0).getPro_price() + "");
                tvNum.setText("库存：" + price.get(0).getPro_num() + "");
                Glide.with(GoodsDetailActivity.this).asBitmap().load(price.get(0).getPro_pic()).into(ivBottomHead);
            } else {
                BottmTvPrice.setText("该商品缺货");
                tvNum.setText("库存：0");
            }

        }
    }

    private String GoodsId;
    private TextView BottmTvPrice;
    private TextView tvSelect, tvNum;
    private ImageView ivBottomHead;

    public void showBottomDialog(final boolean isShopCar) {
        View contentView = LayoutInflater.from(this)
                .inflate(R.layout.buy_goods_bottom_dialog, null);
        ImageView ivCloase = contentView.findViewById(R.id.iv_close);
        ivBottomHead = contentView.findViewById(R.id.iv_head);
        tvNum = contentView.findViewById(R.id.tv_num);
        RecyclerView ryeKind = (RecyclerView) contentView.findViewById(R.id.ry_kind);
        RecyclerView ryAttribute = (RecyclerView) contentView.findViewById(R.id.ry_attr);
        RecyclerView ryColor = (RecyclerView) contentView.findViewById(R.id.ry_color);
        tvSelect = contentView.findViewById(R.id.tv_select);
        BottmTvPrice = contentView.findViewById(R.id.tv_price);
        TextView tvAttr = contentView.findViewById(R.id.tv_attr);
        TextView tvNoGoods = contentView.findViewById(R.id.tv_no_goods);
        LinearLayout llAttr = contentView.findViewById(R.id.ll_attr);
        TextView tvKind = contentView.findViewById(R.id.tv_kind);
        TextView tvColor = contentView.findViewById(R.id.tv_color);
        ryAttribute.setLayoutManager(new GridLayoutManager(this, 3));
        ryeKind.setLayoutManager(new GridLayoutManager(this, 3));
        ryColor.setLayoutManager(new GridLayoutManager(this, 3));
        if (goodsDetailGson != null) {
            Glide.with(GoodsDetailActivity.this).asBitmap().load(goodsDetailGson.getGoods().getGoods_pic()).apply(new RequestOptions().transform(new GlideRoundTransform(GoodsDetailActivity.this, 7))).into((ImageView) contentView.findViewById(R.id.iv_head));
            //商品种类
            if (goodsDetailGson.getName().size() < 1 && goodsDetailGson.getSize().size() < 1 && goodsDetailGson.getColor().size() < 1) {
                tvNoGoods.setVisibility(View.VISIBLE);
                llAttr.setVisibility(View.GONE);
            } else {
                llAttr.setVisibility(View.VISIBLE);
                tvNoGoods.setVisibility(View.GONE);
                if (goodsDetailGson.getName().size() > 0) {
                    KindAdapter sizeAdapter = new KindAdapter(goodsDetailGson.getName());
                    ryeKind.setAdapter(sizeAdapter);
                    tvKind.setText(goodsDetailGson.getModelattr());

                } else {
                    tvKind.setVisibility(View.GONE);
                }
                //商品属性
                if (goodsDetailGson.getSize().size() > 0) {
                    AttributeAdapter attrAdapter = new AttributeAdapter(goodsDetailGson.getSize());
                    ryAttribute.setAdapter(attrAdapter);
                    tvAttr.setText(goodsDetailGson.getSizeattr());

                } else {
                    tvAttr.setVisibility(View.GONE);
                }
                //商品颜色
                if (goodsDetailGson.getColor().size() > 0) {
                    ColorAdapter colorAdapter = new ColorAdapter(goodsDetailGson.getColor());
                    ryColor.setAdapter(colorAdapter);
                    tvColor.setText(goodsDetailGson.getColorattr());

                } else {
                    tvColor.setVisibility(View.GONE);
                }
            }

            tvPrice.setText("￥ " + goodsDetailGson.getGoods().getGoods_price());
        }
        TextView tvSubmit = contentView.findViewById(R.id.tv_submit);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(contentView);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kindSelectBuilder.toString().isEmpty()) {
                    Toast.makeText(GoodsDetailActivity.this, "亲！请选择商品属性", Toast.LENGTH_SHORT).show();
                } else if (colorSelectBuilder.toString().isEmpty()) {
                    Toast.makeText(GoodsDetailActivity.this, "亲！请选择商品属性", Toast.LENGTH_SHORT).show();
                } else if (attributeSelectBuilder.toString().isEmpty()) {
                    Toast.makeText(GoodsDetailActivity.this, "亲！请选择商品属性", Toast.LENGTH_SHORT).show();
                } else {
                    if (tvNum.getText().toString().equals("库存：0")) {
                        Toast.makeText(GoodsDetailActivity.this, "该商品暂时缺货，请重新选择！", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isShopCar) {
                            dialog.dismiss();
                            goodCarPresent.addGoodsCar("1", String.valueOf(goodsDetailGson.getGoods().getId()), "种类：" + kindSelectBuilder.toString() + "  颜色：" + colorSelectBuilder.toString() + "   属性：" + attributeSelectBuilder.toString(), "0");
                        } else {
                            Log.i(TAG, "onClick: " + "种类：" + kindSelectBuilder.toString() + "  颜色：" + colorSelectBuilder.toString() + "   属性：" + attributeSelectBuilder.toString());
                            orderPresenter.submitUserOrder("1", getIntent().getStringExtra("id"), "种类：" + kindSelectBuilder.toString() + "  颜色：" + colorSelectBuilder.toString() + "   属性：" + attributeSelectBuilder.toString(), "1", BottmTvPrice.getText().toString().replace("￥", ""), "0");
                            dialog.dismiss();
                        }
                    }
                }
            }
        });
        ivCloase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }

    //显示购物车列表
    private void showCart() {
        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            bottomSheetLayout.showWithSheetView(sheetDialog);
        }
    }

    @OnClick({R.id.tv_choose, R.id.tv_submit, R.id.iv_shopcar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_shopcar:
                showCart();//显示购物车
                break;
            case R.id.tv_choose:
                showBottomDialog(true);
                break;
            case R.id.tv_submit:
                showBottomDialog(false);
                break;
        }
    }

    private ShopCarAdapter shopCarAdapter = new ShopCarAdapter(null);
    public GoodCarPresenter goodCarPresent = new GoodCarPresenter(this);
    private StringBuilder attributeSelectBuilder = new StringBuilder();
    private StringBuilder kindSelectBuilder = new StringBuilder();
    private StringBuilder colorSelectBuilder = new StringBuilder();

    public class ShopCarAdapter extends BaseQuickAdapter<GoodGson.GoodsBean, BaseViewHolder> {
        private NumberFormat nf;

        public ShopCarAdapter(List<GoodGson.GoodsBean> dataList) {
            super(R.layout.pop_shopcar_list, dataList);
            nf = NumberFormat.getCurrencyInstance();
            nf.setMaximumFractionDigits(2);
        }


        @Override
        protected void convert(final BaseViewHolder helper, final GoodGson.GoodsBean item) {
            helper.setText(R.id.tv_name, item.getGoods_name())
                    .setText(R.id.tv_price, nf.format(item.getGoods_price()) + " * " + item.getNum())
                    .setText(R.id.tv_count, String.valueOf(item.getNum()))
                    .setOnClickListener(R.id.iv_add, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goodCarPresent.addGoodsCar("1", String.valueOf(item.getId()), "", "0");
                            notifyDataSetChanged();

                        }
                    })
                    .setOnClickListener(R.id.iv_reduce, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goodCarPresent.addGoodsCar("1", String.valueOf(item.getId()), "", "1");
                            TextView view = (TextView) helper.getView(R.id.tv_count);
                            if (Integer.valueOf(view.getText().toString()) < 2) {
                                remove(helper.getPosition());
                            }
                            notifyDataSetChanged();
                        }
                    });
        }
    }

    @Override
    public void submitSuccess(UserOrderGson userOrder) {
        String kind = kindSelectBuilder.toString().isEmpty() ? "" : "样式：" + kindSelectBuilder.toString();
        String color = colorSelectBuilder.toString().isEmpty() ? "" : "颜色：" + colorSelectBuilder.toString();
        String attr = attributeSelectBuilder.toString().isEmpty() ? "" : "属性：" + attributeSelectBuilder.toString();
        Intent intent = new Intent(GoodsDetailActivity.this, SubmitGoodsOrderActivity.class);
        intent.putExtra("attr", kind + "  " + color + "  " + attr);
        intent.putExtra("oid", String.valueOf(userOrder.getId()));
        intent.putExtra("gid", getIntent().getStringExtra("id"));
        intent.putExtra("price", BottmTvPrice.getText().toString());
        intent.putExtra("id", GoodsId);
        startActivity(intent);
    }

    @Override
    public void submitFailed() {
        Toast.makeText(this, "订单提交失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addSuccess() {
        Toast.makeText(this, "商品添加成功", Toast.LENGTH_SHORT).show();
        getGoodCarPresent.getShopCarGoodsList("1");
    }

    @Override
    public void delSuccess() {
        getGoodCarPresent.getShopCarGoodsList("1");
    }

    @Override
    public void addFailed() {
        Toast.makeText(this, "商品添加失败", Toast.LENGTH_SHORT).show();
    }

    private NumberFormat nf;

    @Override
    public void loadShopCarList(List<GoodGson.GoodsBean> goodsBeen) {
        Log.i(TAG, "loadShopCarList: running" + goodsBeen.size());
        int count = 0;
        double cost = 0;
        shopCarAdapter.replaceData(goodsBeen);
        ryShopCar.setAdapter(shopCarAdapter);
        for (int i = 0; i < goodsBeen.size(); i++) {
            GoodGson.GoodsBean item = goodsBeen.get(i);
            cost += item.getNum() * item.getGoods_price();
            count += item.getNum();
        }
        goodsBeen.clear();
        if (count < 1) {
            tvShopcarCount.setVisibility(View.GONE);
        } else {
            tvShopcarCount.setVisibility(View.VISIBLE);
        }
        tvShopcarCount.setText(String.valueOf(count));
        tvMoney.setText(nf.format(cost));
    }

    @Override
    public void delShopCarSuccess() {

    }

    @Override
    public void delShopCarFailed() {

    }

    private class KindAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        private Map<Integer, Boolean> map = new HashMap<>();
        private boolean onBind;
        private int checkedPosition = -1;

        public KindAdapter(@Nullable List<String> data) {
            super(R.layout.ry_goods_detail_kind_item, data);
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
                        Log.i(TAG, "convert: " + attributeSelectBuilder.length());
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
                goodDetailPresenter.getGoodsPrice(getIntent().getStringExtra("id"),
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

        public ColorAdapter(@Nullable List<String> data) {
            super(R.layout.ry_goods_detail_kind_item, data);
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
                goodDetailPresenter.getGoodsPrice(getIntent().getStringExtra("id"),
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

        public AttributeAdapter(@Nullable List<String> data) {
            super(R.layout.ry_goods_detail_kind_item, data);
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
                goodDetailPresenter.getGoodsPrice(getIntent().getStringExtra("id"),
                        colorSelectBuilder.toString(),
                        kindSelectBuilder.toString(),
                        attributeSelectBuilder.toString());
            } else {
                helper.setChecked(R.id.tv_tag, false);
            }
            onBind = false;
        }
    }

    private class TagAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public TagAdapter(@Nullable List<String> data) {
            super(R.layout.ry_goods_tag_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            if (helper.getPosition() == 0) {
                helper.setBackgroundRes(R.id.tv_tag, R.drawable.goods_item_tag_hot)
                        .setTextColor(R.id.tv_tag, getResources().getColor(R.color.red));
            } else if (helper.getPosition() == 1) {
                helper.setBackgroundRes(R.id.tv_tag, R.drawable.goods_item_tag_new)
                        .setTextColor(R.id.tv_tag, getResources().getColor(R.color.blue));
            }
            if (helper.getPosition() == 2) {
                helper.setBackgroundRes(R.id.tv_tag, R.drawable.goods_item_tag_super)
                        .setTextColor(R.id.tv_tag, getResources().getColor(R.color.yellow));
            }
            helper.setText(R.id.tv_tag, item);
        }
    }

    private class CommentAdapter extends BaseQuickAdapter<GoodsDetailGson.CommentBean, BaseViewHolder> {

        public CommentAdapter(@Nullable List<GoodsDetailGson.CommentBean> data) {
            super(R.layout.ry_comment_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsDetailGson.CommentBean item) {
            String comment = item.getUsername();
            String substring = comment.substring(3, 7);
            String replace = comment.replace(substring, "****");
            helper.setText(R.id.tv_username, replace)
                    .setText(R.id.tv_comment, item.getComment());
        }
    }
}
