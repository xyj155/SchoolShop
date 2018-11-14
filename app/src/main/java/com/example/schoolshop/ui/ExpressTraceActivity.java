package com.example.schoolshop.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.ExpressContract;
import com.example.schoolshop.gson.ExpressGson;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.presenter.ExpressPresenter;
import com.example.schoolshop.util.GlideRoundTransform;
import com.example.schoolshop.view.CircleImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.amap.api.maps.AMap.MAP_TYPE_NORMAL;

public class ExpressTraceActivity extends BaseActivity implements ExpressContract.View {

    @InjectView(R.id.ry_express)
    RecyclerView ryExpress;
    @InjectView(R.id.ry_goods)
    RecyclerView ryGoods;
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    @InjectView(R.id.map)
    MapView map;
    @InjectView(R.id.iv_head)
    CircleImageView ivHead;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_code)
    TextView tvCode;
    private ExpressPresenter expressPresenter = new ExpressPresenter(this);
    private GoodsAdapter goodsAdapter = new GoodsAdapter(null);
    private TraceAdapter traceAdapter = new TraceAdapter(null);

    @Override
    public int intiLayout() {
        return R.layout.activity_express_trace;
    }

    private AMap aMap;

    @Override
    public void initView() {
        initToolBar().setToolbarBackIco().setToolbarSubtitle("物流");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        map.onCreate(savedInstanceState);
        if (aMap==null){
            aMap=map.getMap();
        }
        this.aMap.getUiSettings().setLogoPosition(-80);
        this.aMap.getUiSettings().setZoomControlsEnabled(false);
        this.aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        this.aMap.setMapType(MAP_TYPE_NORMAL);
        // TODO: add setContentView(...) invocation

    }
    private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }
    @Override
    public void initData() {
        ButterKnife.inject(this);
        expressPresenter.getUserPackageById("1");
        ryGoods.setLayoutManager(new GridLayoutManager(ExpressTraceActivity.this, 2));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ExpressTraceActivity.this);
        linearLayoutManager.setReverseLayout(true);
        ryExpress.setLayoutManager(linearLayoutManager);
        goodsAdapter.bindToRecyclerView(ryGoods);
        traceAdapter.bindToRecyclerView(ryExpress);
        ryExpress.setNestedScrollingEnabled(false);
        ryGoods.setNestedScrollingEnabled(false);
    }

    @Override
    protected int setStatusBarColor() {
        return 0;
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
        Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadExpress(ExpressGson expressGson) {
        goodsAdapter.replaceData(expressGson.getGoods());
        traceAdapter.replaceData(expressGson.getTrace());
        tvName.setText(expressGson.getCom());
        tvCode.setText("快递单号：" + expressGson.getNum());
        MarkerOptions localMarkerOptions1 = new MarkerOptions();
        localMarkerOptions1.draggable(true);
        localMarkerOptions1.position(new LatLng(Double.valueOf(expressGson.getToGeo().substring(11)).doubleValue(), Double.valueOf(expressGson.getToGeo().substring(0, 10)).doubleValue()));
        localMarkerOptions1.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.from)));
        MarkerOptions localMarkerOptions2 = new MarkerOptions();
        localMarkerOptions2.draggable(true);
        localMarkerOptions2.position(new LatLng(Double.valueOf(expressGson.getFromGeo().substring(11)).doubleValue(), Double.valueOf(expressGson.getFromGeo().substring(0, 10)).doubleValue()));
        localMarkerOptions2.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.to)));
        aMap.addMarker(localMarkerOptions1);
        aMap.addMarker(localMarkerOptions2);
        LatLng latLng = new LatLng((Double.valueOf(expressGson.getFromGeo().substring(11)).doubleValue()
                +Double.valueOf(expressGson.getToGeo().substring(11)).doubleValue())/2, (Double.valueOf(expressGson.getToGeo().substring(0, 10)).doubleValue()+Double.valueOf(expressGson.getFromGeo().substring(0, 10)).doubleValue())/2);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,  5, 0, 0)));


        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();//存放所有点的经纬度
        boundsBuilder.include(new LatLng(Double.valueOf(expressGson.getToGeo().substring(11)).doubleValue(),
                Double.valueOf(expressGson.getToGeo().substring(0, 10)).doubleValue()));
        boundsBuilder.include(new LatLng(Double.valueOf(expressGson.getFromGeo().substring(11)).doubleValue(), Double.valueOf(expressGson.getFromGeo().substring(0, 10)).doubleValue()));
        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 50));//第二个参数为四周留空宽度
    }



    private class GoodsAdapter extends BaseQuickAdapter<GoodGson.GoodsBean, BaseViewHolder> {

        public GoodsAdapter(List<GoodGson.GoodsBean> data) {
            super(R.layout.ry_store_goods_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final GoodGson.GoodsBean item) {
            helper.setText(R.id.tv_name, item.getGoods_name())
                    .setText(R.id.tv_price, "￥ " + item.getGoods_price())
                    .setOnClickListener(R.id.ll_goods, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ExpressTraceActivity.this, GoodsDetailActivity.class);
                            intent.putExtra("id", String.valueOf(item.getId()));
                            intent.putExtra("kind", String.valueOf(item.getGoods_kind()));
                            mContext.startActivity(intent);
                        }
                    });
            Glide.with(ExpressTraceActivity.this).load(item.getGoods_pic()).apply(new RequestOptions().transform(new GlideRoundTransform(ExpressTraceActivity.this, 7))).into((ImageView) helper.getView(R.id.iv_cover));
        }
    }


    private class TraceAdapter extends BaseQuickAdapter<ExpressGson.TraceBean, BaseViewHolder> {


        public TraceAdapter(List<ExpressGson.TraceBean> data) {
            super(R.layout.ry_express_trace_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ExpressGson.TraceBean item) {
            Log.i(TAG, "convert: " + item.getAcceptTime());
            helper.setText(R.id.tv_time, item.getAcceptTime().substring(5, 10) + "\n" + item.getAcceptTime().substring(10, 16))
                    .setText(R.id.tv_trace, item.getAcceptStation());
            if (helper.getPosition() == traceAdapter.getItemCount() - 1) {
                Glide.with(ExpressTraceActivity.this).load(R.mipmap.up_red).apply(new RequestOptions().transform(new GlideRoundTransform(ExpressTraceActivity.this, 7))).into((ImageView) helper.getView(R.id.iv_dot));
            }

        }
    }
}
