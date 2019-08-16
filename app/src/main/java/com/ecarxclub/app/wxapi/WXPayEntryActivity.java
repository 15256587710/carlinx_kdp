package com.ecarxclub.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import static com.ecarxclub.app.common.YxbApplication.WX_ID;

/**
 * 微信支付
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WX_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        ToastUtils.i("resp",resp.errCode+"_______________________"+resp.errStr);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                ToastUtils.showTextShort("支付成功");
                EventBus.getDefault().post(new EventMessage(Constant.USER_PAY_WEICHATE,""));
//                Intent intent=new Intent(WXPayEntryActivity.this,PaySuccessResultActivity.class);
//                startActivity(intent);
            } else {
                ToastUtils.showTextShort("支付失败");
            }
        }
        finish();
    }
}