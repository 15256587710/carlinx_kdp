package com.ecarxclub.app.ui.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.UserInfoRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.presenter.mine.userinfo.EditUserPresenter;
import com.ecarxclub.app.presenter.mine.userinfo.EditUserView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.CommonUtils;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.TimeUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.dialog.SheetDialog;
import com.ecarxclub.app.utils.picker.DatePicker;
import com.ecarxclub.app.utils.picker.model.Province;
import com.ecarxclub.app.utils.picker.widget.AddressPickTask;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/5/17 16:11.
 * 编辑资料
 */
public class EditUserInfoActivity extends BaseActivityP<EditUserPresenter> implements EditUserView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.ll_setuser_img)
    LinearLayout llSetUserImg;
    @BindView(R.id.img_setuser_head)
    ImageView imgHead;
    @BindView(R.id.ll_exd_birthday)
    LinearLayout llBirthday;
    @BindView(R.id.tv_exd_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_exd_sex)
    TextView tvSex;
    @BindView(R.id.ll_exd_sex)
    LinearLayout llSex;
    @BindView(R.id.tv_exd_address)
    TextView tvAddress;
    @BindView(R.id.et_exd_nickname)
    EditText etNickname;
    @BindView(R.id.ll_exd_address)
    LinearLayout llAddress;
//    @BindView(R.id.ll_set_pwd)
//    LinearLayout llSetPwd;
    @BindView(R.id.ll_exd_nickname)
    LinearLayout llNickname;

    private LocalMedia icon;
    private String path = "";

    private boolean isNickname;//修改昵称的时候   提醒用户更改个人中心信息
    private String[] mStrBirthday;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_userinfo;
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("编辑资料");
        onGetUserInfo();
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });

        //头像
        RxView.clicks(llSetUserImg).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                choosePhoto();
            }
        });

        //生日
        RxView.clicks(llBirthday).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                getBirthday();
            }
        });

        //地址
        RxView.clicks(llAddress).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                setAddressPick();
            }
        });
        //性别
        RxView.clicks(llSex).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                getSex();
            }
        });
        //设置密码
//        RxView.clicks(llSetPwd).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
//            @Override
//            public void call(Void aVoid) {
//                startIntent(EditUserInfoActivity.this, ChangePwdActivity.class);
//            }
//        });

    }

    //获取用户信息
    public void onGetUserInfo() {
        presenter.onGetUserInfo();
    }

    @Override
    public void onUserInfo(UserInfoRes baseMsgRes) {
        if (baseMsgRes.success) {
            etNickname.setText(baseMsgRes.data.nickName);
            etNickname.setSelection(etNickname.getText().toString().length());
            if (baseMsgRes.data.gender == 0) {
                tvSex.setText("女");
            } else {
                tvSex.setText("男");
            }
            if (baseMsgRes.data.birthday!=null){
                mStrBirthday=baseMsgRes.data.birthday.split("-");
                tvBirthday.setText(baseMsgRes.data.birthday);
            }
            tvAddress.setText(baseMsgRes.data.location);
            Glide.with(EditUserInfoActivity.this).load(baseMsgRes.data.icon).apply(YxbContent.optionsRound).into(imgHead);
        }
    }

    @Override
    public void onGetResult(BaseMsgRes baseMsgRes) {
        UrlOkHttpUtils.isUploadImg = false;
        if (baseMsgRes.isSuccess()) {
            Glide.with(EditUserInfoActivity.this).load("file://" + path).apply(YxbContent.optionsRound).into(imgHead);
            EventBus.getDefault().post(new EventMessage(Constant.USER_INFO_SUCCESS, ""));
            ToastUtils.showTextShort("上传成功");
        }
        //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
        PictureFileUtils.deleteCacheDirFile(EditUserInfoActivity.this);
    }

    //修改用户信息
    @Override
    public void onEditUser(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()) {
            ToastUtils.showTextShort(baseMsgRes.getMsg());
            if (isNickname) {//修改昵称
                EventBus.getDefault().post(new EventMessage(Constant.USER_INFO_SUCCESS, ""));
            }
            isNickname = false;
        }
    }

    @Override
    protected EditUserPresenter createPresenter() {
        return new EditUserPresenter(this);
    }


    //性别
    private void getSex() {
        new SheetDialog(EditUserInfoActivity.this)
                .builder()
                .setCancelable(false)
                .setTitle("请选择您的性别")
                .setCanceledOnTouchOutside(false)
                .addSheetItem("男", SheetDialog.SheetItemColor.Blue,
                        new SheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tvSex.setText("男");
                                setSex("1");
                            }
                        })
                .addSheetItem("女", SheetDialog.SheetItemColor.Blue,
                        new SheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tvSex.setText("女");
                                setSex("0");
                            }
                        }).show();
    }

    private void setSex(String sexs) {
//        presenter.changeSex(sexs);
        setUserInfo("gender", sexs);
    }

    private void setUserInfo(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        presenter.changeUserInfo(map);
    }

    //生日
    private void getBirthday() {
        final DatePicker picker = new DatePicker(EditUserInfoActivity.this);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setTopHeight(50);
        picker.setRangeStart(1920, 1, 1);
        picker.setRangeEnd(Integer.parseInt(TimeUtils.getCUSeconds().toString().split(" ")[0].toString().split("-")[0]), Integer.parseInt(TimeUtils.getCUSeconds().toString().split(" ")[0].toString().split("-")[1]), Integer.parseInt(TimeUtils.getCUSeconds().toString().split(" ")[0].toString().split("-")[2]));
        picker.setSelectedItem(mStrBirthday!=null&&mStrBirthday.length==3?Integer.parseInt(mStrBirthday[0]):2019,
                mStrBirthday!=null&&mStrBirthday.length==3?Integer.parseInt(mStrBirthday[1]):01,
                mStrBirthday!=null&&mStrBirthday.length==3?Integer.parseInt(mStrBirthday[2]):01);
        picker.setWeightEnable(true);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tvBirthday.setText(year + "-" + month + "-" + day);
//                presenter.changeBirthday(tvBirthday.getText().toString());
                setUserInfo("birthday", tvBirthday.getText().toString());
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    //选择上传方式
    private void choosePhoto() {
        new SheetDialog(EditUserInfoActivity.this)
                .builder()
                .setCancelable(false)
//                .setTitle("请选择您的性别")
                .setCanceledOnTouchOutside(false)
                .addSheetItem("拍照", SheetDialog.SheetItemColor.Blue,
                        new SheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                onGetCameraPer();
                            }
                        })
                .addSheetItem("从相册选择", SheetDialog.SheetItemColor.Blue,
                        new SheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                onShowPhoto();
                            }
                        }).show();
    }

    //相册选择
    private void onShowPhoto() {
// 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(EditUserInfoActivity.this)
//                        .openCamera(PictureMimeType.ofImage())//拍照
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                        .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
//                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(false)// 是否压缩 true or false
                .glideOverride(360, 360)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
                .compressSavePath(createDir())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
//                        .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(360, 360)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .videoQuality(0)// 视频录制质量 0 or 1 int
                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(60)//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    //拍照
    private void onOpenCamera() {
// 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(EditUserInfoActivity.this)
                .openCamera(PictureMimeType.ofImage())//拍照
//                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                        .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(false)// 是否压缩 true or false
                .glideOverride(360, 360)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
                .compressSavePath(createDir())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
//                        .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(360, 360)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .videoQuality(0)// 视频录制质量 0 or 1 int
                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(60)//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    /**
     * 地址选择器
     */
    private void setAddressPick() {
        AddressPickTask task = new AddressPickTask(EditUserInfoActivity.this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
            }

            @Override
            public void onAddressPicked(Province province, Province.City city, Province.City.County county) {
//                mStrProvince=province.getRegion_name();
//                mStrCity=city.getRegion_name();
                if (county == null) {
                    tvAddress.setText(province.getRegion_name() + city.getRegion_name());
//                    addr_region_id = city.region_id;//地区ID
                } else {
//                    mStrArea=county.getRegion_name();
                    tvAddress.setText(province.getRegion_name() + " " + city.getRegion_name() + " " + county.getRegion_name());
                    if (county.getRegion_id() == null) {
//                        addr_region_id = "-1";
                    } else {
//                        addr_region_id = String.valueOf(county.getRegion_id());
                    }
                }
                setUserInfo("location", tvAddress.getText().toString());
            }
        });
        task.execute("浙江", "杭州", "滨江");
//        if (listBean!=null) {
////            if (mGetAddressInfoRes.body().data.addr_region_name.split(" ").length == 1) {
////                task.execute(mGetAddressInfoRes.body().data.addr_region_name.split(" ")[0]);
////            } else if (mGetAddressInfoRes.body().data.addr_region_name.split(" ").length == 2) {
////                task.execute(mGetAddressInfoRes.body().data.addr_region_name.split(" ")[0], mGetAddressInfoRes.body().data.addr_region_name.split(" ")[1]);
////            } else {
////                task.execute(mGetAddressInfoRes.body().data.addr_region_name.split(" ")[0], mGetAddressInfoRes.body().data.addr_region_name.split(" ")[1], mGetAddressInfoRes.body().data.addr_region_name.split(" ")[2]);
////            }
//            task.execute(listBean.province,listBean.city,listBean.region);
//        } else {
//            task.execute("浙江", "杭州", "滨江");
//        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    icon = selectList.get(0);
                    if (icon.isCut() && !icon.isCompressed()) {
                        // 裁剪过
                        path = icon.getCutPath();
                    } else if (icon.isCompressed() || (icon.isCut() && icon.isCompressed())) {
                        // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                        path = icon.getCompressPath();
                    } else {
                        // 原图
                        path = icon.getPath();
                    }
                    ToastUtils.i("路径++++", "" + path);
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
                    UrlOkHttpUtils.isUploadImg = true;
                    try {
                        ToastUtils.i("图片大小", "  " + CommonUtils.getFileSize(new File(path)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    presenter.uploadFiles(path);
                    break;
            }
        }
    }

    public void onGetCameraPer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //权限不够
            if (ContextCompat.checkSelfPermission(EditUserInfoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditUserInfoActivity.this, new String[]{Manifest.permission.CAMERA}, 3);//打开相机
            } else {
                if (ActivityCompat.checkSelfPermission(EditUserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);//sd卡存储权限
                }else {
                    //调用相机
                    onOpenCamera();
                }
            }
        }else {
            //调用相机
            onOpenCamera();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 3:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权
                    onGetCameraPer();
                } else {
                    // 未授权
                    ToastUtils.showTextShort("未授权");
                }
                break;
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权
                    onGetCameraPer();
                } else {
                    // 未授权
                    ToastUtils.showTextShort("未授权");
                }
                break;
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) { //隐藏软键盘
                if (etNickname.getText().toString().length()>0&&etNickname.getText().toString().length()<=16){
                    isNickname = true;
                    setUserInfo("nickName", etNickname.getText().toString());
                }else {
                    ToastUtils.showTextShort("用户名格式错误");
                }
//                ToastUtils.i("","111111111111111111111");
                v.clearFocus();
                llNickname.setFocusable(true);
                llNickname.setFocusableInTouchMode(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 判断当前点击的控件是否为EditText
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) { //点击view==EditText
            ((TextView) v).setCursorVisible(true);
            int[] leftTop = {0, 0};
            //获取输入框当前位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
