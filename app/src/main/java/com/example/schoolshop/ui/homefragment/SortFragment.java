package com.example.schoolshop.ui.homefragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.adapter.GoodsAdapter;
import com.example.schoolshop.base.BaseFragment;
import com.example.schoolshop.contract.GoodCarContract;
import com.example.schoolshop.contract.HomeSortContract;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.presenter.GoodCarPresenter;
import com.example.schoolshop.presenter.HomeSortPresenter;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static android.os.Looper.getMainLooper;

/**
 * Created by Administrator on 2018/10/31.
 */

public class SortFragment extends BaseFragment implements HomeSortContract.View, GoodCarContract.View {
    @InjectView(R.id.radioButton)
    RadioButton radioButton;
    @InjectView(R.id.radioGroup)
    RadioGroup radioGroup;
    @InjectView(R.id.ry_sort)
    RecyclerView rySort;
    @InjectView(R.id.ry_list)
    StickyListHeadersListView ryList;
    @InjectView(R.id.sl_sort)
    SmartRefreshLayout slSort;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.tv_count)
    TextView tvCount;
    @InjectView(R.id.iv_shopcar)
    ImageView ivShopcar;
    @InjectView(R.id.ll_count)
    LinearLayout llCount;
    @InjectView(R.id.bottomSheetLayout)
    BottomSheetLayout bottomSheetLayout;
    @InjectView(R.id.fl_goods)
    FrameLayout flGoods;
    @InjectView(R.id.tv_shopcar_count)
    TextView tvShopcarCount;

    public HomeSortPresenter homeSortPresenter = new HomeSortPresenter(this);
    private SortItemAdapter sortAdapter = new SortItemAdapter(null);
    private List<String> list = new ArrayList<>();
    private int layoutPosition;
    private GoodsAdapter goodAdapter;
    private View sheetDialog;
    private ShopCarAdapter shopCarAdapter = new ShopCarAdapter(null);
    private Handler mHanlder;
    public GoodCarPresenter goodCarPresent = new GoodCarPresenter(this);
    private List<GoodGson.GoodsBean> goodsList = new ArrayList<>();
    private NumberFormat nf;
    private RecyclerView ryShopCar;
    public static SortFragment instance;

    public static SortFragment getInstance() {
        return instance;
    }

    @Override
    protected int setView() {
        return R.layout.fragment_sort;
    }


    @Override
    protected void init(View view) {
        ButterKnife.inject(this, view);
        rySort.setLayoutManager(new LinearLayoutManager(getActivity()));
        ryList.addHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.item_header_view, null));
        sheetDialog = LayoutInflater.from(getActivity()).inflate(R.layout.recycler_view, null, false);
        ryShopCar = sheetDialog.findViewById(R.id.ry_shopcar);
        ryShopCar.setLayoutManager(new LinearLayoutManager(getContext()));
        ryShopCar.setAdapter(shopCarAdapter);
        mHanlder = new Handler(getMainLooper());
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        sheetDialog.findViewById(R.id.tv_clean)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goodAdapter.notifyDataSetChanged();//清空购物车
                        update();
                    }
                });

        shopCarAdapter.bindToRecyclerView(ryShopCar);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        homeSortPresenter.getGoodsListByLocation("嘉兴", "热销产品", "1");//请求商品列表数据
        goodCarPresent.getShopCarGoodsList("1");//获取购物车的商品数量
        slSort.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                String item = sortAdapter.getItem(layoutPosition);
                homeSortPresenter.getGoodsListByLocation("嘉兴", item, "1");
            }
        });
        goodAdapter = new GoodsAdapter(goodsList, SortFragment.this);
        ryList.setAdapter(goodAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void showLoading() {
        showDialog();
    }

    @Override
    public void hideLoading() {
        loadSuccess();
    }

    @Override
    public void loadFailed(String msg) {
        loadFailed();
        slSort.finishRefresh();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    //加载商品条目
    @Override
    public void loadGoodsList(final GoodGson goodGson) {
        slSort.finishRefresh();
        list.addAll(goodGson.getKind());
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        rySort.setAdapter(sortAdapter);
        sortAdapter.replaceData(list);//加载条目
        goodsList.clear();//清除当前所有商品
        goodsList.addAll(goodGson.getGoods());

   goodAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.tv_count, R.id.iv_shopcar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_shopcar:
                showCart();//显示购物车
                break;
            case R.id.tv_count:
                break;
        }
    }

    //显示购物车列表
    private void showCart() {
        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            bottomSheetLayout.showWithSheetView(sheetDialog);
        }
    }



    //刷新布局 总价、购买数量等
    public void update() {
        goodAdapter.notifyDataSetChanged();
        if (shopCarAdapter != null) {
            shopCarAdapter.notifyDataSetChanged();
        }
        if (bottomSheetLayout.isSheetShowing() && goodsList.size() < 1) {
            bottomSheetLayout.dismissSheet();
        }
    }


    @Override
    public void addSuccess() {
        String item = sortAdapter.getItem(layoutPosition);
        homeSortPresenter.getGoodsListByLocation("嘉兴", item, "1");
        goodCarPresent.getShopCarGoodsList("1");//获取购物车的商品数量
    }

    @Override
    public void delSuccess() {
        String item = sortAdapter.getItem(layoutPosition);
        homeSortPresenter.getGoodsListByLocation("嘉兴", item, "1");
        goodCarPresent.getShopCarGoodsList("1");//获取购物车的商品数量
    }

    @Override
    public void addFailed() {

    }


    @Override
    public void loadShopCarList(List<GoodGson.GoodsBean> goodsBeen) {
        int count = 0;
        double cost = 0;
        shopCarAdapter.replaceData(goodsBeen);
//        ryShopCar.setAdapter(shopCarAdapter);
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
                            goodCarPresent.addGoodsCar("1", String.valueOf(item.getId()), "","0");
                            SortFragment.this.update();
                            notifyDataSetChanged();

                        }
                    })
                    .setOnClickListener(R.id.iv_reduce, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goodCarPresent.addGoodsCar("1", String.valueOf(item.getId()),"", "1");
                            SortFragment.this.update();
                            TextView view = (TextView) helper.getView(R.id.tv_count);
                            if (Integer.valueOf(view.getText().toString()) < 2) {
                                remove(helper.getPosition());
                            }
                            notifyDataSetChanged();
                        }
                    });
        }
    }

    //条目适配器
    private class SortItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


        public SortItemAdapter(@Nullable List<String> data) {
            super(R.layout.ry_sort_list_item, data);

        }

        @Override
        protected void convert(final BaseViewHolder helper, final String item) {
            helper.setText(R.id.rb_sort, item)
                    .setTag(R.id.rb_sort, helper.getPosition())
                    .setOnClickListener(R.id.rb_sort, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layoutPosition = helper.getLayoutPosition();
                            notifyDataSetChanged();
                            homeSortPresenter.getGoodsListByLocation("嘉兴", item, "1");
                            goodCarPresent.getShopCarGoodsList("1");//获取购物车的商品数量
                        }
                    });
            if (helper.getPosition() == layoutPosition) {
                helper.setChecked(R.id.rb_sort, true);
            } else {
                helper.setChecked(R.id.rb_sort, false);
            }
        }
    }


    public void playAnimation(int[] start_location) {
        ImageView img = new ImageView(getActivity());
        img.setImageResource(R.mipmap.shop_add);
        setAnim(img, start_location);
    }

    private void setAnim(final View v, int[] start_location) {
        final ViewGroup flShopcar = getActivity().findViewById(R.id.rl_container);
        addViewToAnimLayout(flShopcar, v, start_location);
        Animation set = createAnim(start_location[0], start_location[1]);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                mHanlder.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flShopcar.removeView(v);
                    }
                }, 100);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(set);
    }

    private Animation createAnim(int startX, int startY) {
        int[] des = new int[2];
        ivShopcar.getLocationInWindow(des);
        AnimationSet set = new AnimationSet(false);
        Animation translationX = new TranslateAnimation(0, des[0] - startX, 0, 0);
        translationX.setInterpolator(new LinearInterpolator());
        Animation translationY = new TranslateAnimation(0, 0, 0, des[1] - startY);
        translationY.setInterpolator(new AccelerateInterpolator());
        Animation alpha = new AlphaAnimation(1, 0.5f);
        set.addAnimation(translationX);
        set.addAnimation(translationY);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    private void addViewToAnimLayout(final ViewGroup vg, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        int[] loc = new int[2];
        vg.getLocationInWindow(loc);
        view.setX(x);
        view.setY(y - loc[1]);
        vg.addView(view);
    }


}
