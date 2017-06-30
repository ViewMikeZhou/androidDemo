package androiddemo.zhou.com.androiddemo;

import android.Manifest;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WifiActivity extends AppCompatActivity {

    WifiManager mWifiManager;
    WifiInfo mWifiInfo;
    List<ScanResult> mWifiList;
    List<WifiConfiguration> mWifiConfiguration;
    private int linkingID;
    private EditText mEtPassWord;
    private ListView mLv;
    private List<String> mWifis;
    private ArrayAdapter<String> mAdapter;
    private boolean isConnect;
    private WifiConfiguration mTempConfig;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    int wifistate = (int) msg.obj;
                    Toast.makeText(WifiActivity.this, "wifistate:" + wifistate, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int wifiState = mWifiManager.getWifiState();
            mHandler.obtainMessage(1, wifiState).sendToTarget();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        mWifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
        int ipAddress = mWifiInfo.getIpAddress();
        Log.d("WifiActivity", "wifiinfo  --->" + mWifiInfo.toString());
        Log.d("WifiActivity", "ipAddress  --->" + ipAddress + "加工后IP --->" + getlocalip(mWifiInfo));
        Log.d("WifiActivity", "MAC  --->" + getAdressMacByInterface());
        PermissionUtils.setPermisson(this, "定位啊", Manifest.permission.ACCESS_FINE_LOCATION);


        mHandler.postDelayed(mRunnable, 1000);


        mEtPassWord = (EditText) findViewById(R.id.et_password);
        mEtPassWord.setText("hd2016@)!!");
        mLv = (ListView) findViewById(R.id.lv);
        mWifis = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mWifis);
        mLv.setAdapter(mAdapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isConnect) {
                    isConnect = true;
                    String trim = mEtPassWord.getText().toString().trim();
                    if (TextUtils.isEmpty(trim)) {
                        Toast.makeText(WifiActivity.this, "请输入密码", Toast.LENGTH_SHORT);
                    }
                    WifiConfiguration config = CreateWifiInfo(mWifis.get(position), trim, 3);
                    addNetwork(config);
                    //connectToWifi(mWifis.get(position), trim);
                    Toast.makeText(WifiActivity.this, "连接wifi", Toast.LENGTH_SHORT).show();
                } else {
                    isConnect = false;
                    WifiConfiguration wifiConfiguration = IsExsits(mWifis.get(position));
                    removeNetConnect(wifiConfiguration);
                    Toast.makeText(WifiActivity.this, "断开wifi", Toast.LENGTH_SHORT).show();
                }


            }


        });

    }

    //扫描
    public void scan(View view) {
        if (openWifi()) {
            startScan();
        }
    }

    public boolean openWifi() {//打开wifi
        if (!mWifiManager.isWifiEnabled()) {
            Log.i("WifiActivity", "setWifiEnabled.....");
            mWifiManager.setWifiEnabled(true);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.i("WifiActivity", "setWifiEnabled.....end");
        }
        return mWifiManager.isWifiEnabled();
    }

    public void startScan() {//wifi扫描
        mWifis.clear();
        boolean scan = mWifiManager.startScan();
        Log.i("WifiActivity", "startScan result:" + scan);
        mWifiList = mWifiManager.getScanResults();
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();
        clearRepeat(mWifiList);
        mAdapter.notifyDataSetChanged();
        if (mWifiConfiguration != null) {
            for (WifiConfiguration wifiConfiguration : mWifiConfiguration) {
                //  Log.e("WifiActivity", "wifiConfiguration:" + wifiConfiguration.toString());
                Log.d("WifiActivity", "wifiConfiguration:" + wifiConfiguration.SSID + "network ...." + mWifiManager.addNetwork(wifiConfiguration));
            }
        }


    }

    private void clearRepeat(List<ScanResult> wifiList) {
        Set set = new HashSet();
        for (ScanResult scanResult : wifiList) {
            if (!"".equals(scanResult.SSID)) {
                Log.e("WifiActivity", "scanResult :" + scanResult.toString());
                 set.add(scanResult.SSID);

            }
        }
         mWifis.addAll(set);

    }

    public WifiConfiguration CreateWifiInfo(String SSID, String password, int Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.allowedKeyManagement.clear();

        if (Type == 1) //WIFICIPHER_NOPASS
        {
            config.wepKeys[0] = "";//连接无密码热点时加上这两句会出错
            config.wepTxKeyIndex = 0;
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }
        if (Type == 2) //WIFICIPHER_WEP
        {
            config.SSID = "\"" + SSID + "\"";
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 3) //WIFICIPHER_WPA
        {
            config.SSID = "\"" + SSID + "\"";
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.priority = 40;
            config.status = WifiConfiguration.Status.ENABLED;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);

        }
        return config;
    }

    private WifiConfiguration IsExsits(String ssid) {
        for (WifiConfiguration wifiConfiguration : mWifiConfiguration) {
            if (wifiConfiguration.SSID.equals("\"" + ssid + "\"")) {
                Log.d("WifiActivity", "network :" + mWifiManager.addNetwork(wifiConfiguration));
                return wifiConfiguration;
            }
        }
        return null;
    }


    // 添加一个网络并连接
    public void addNetwork(WifiConfiguration wcg) {
        int netId = mWifiManager.addNetwork(wcg);
        Log.e("WifiActivity", "netId:" + netId);
        if (netId == -1) {
            wcg.networkId = 10;
            netId = getExistingNetworkId(wcg.SSID);
            Log.e("WifiActivity", "netId:-->getExistingNetworkId -->" + netId);
        }

        mWifiManager.disconnect();
        mWifiManager.enableNetwork(netId, true);
        mWifiManager.reconnect();
    }

    private void removeNetConnect(WifiConfiguration wifiConfiguration) {

        int netId = mWifiManager.addNetwork(wifiConfiguration);
        Log.e("WifiActivity", "netId:" + netId);
        if (netId == -1) {
            netId = getExistingNetworkId(wifiConfiguration.SSID);
            Log.e("WifiActivity", "netId:-->getExistingNetworkId -->" + netId);
        }
        mWifiManager.disconnect();
        mWifiManager.disableNetwork(netId);
    }


    /**
     * 或取本机的ip地址
     */
    private String getlocalip(WifiInfo wifiInfo) {
        int ipAddress = wifiInfo.getIpAddress();
        //  Log.d(Tag, "int ip "+ipAddress);
        if (ipAddress == 0) return null;
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }

    //发送数据
    public void send(View view) {

    }

    public void connectToWifi(String ssid, String password) {
        try {
            //   WifiManager wifiManager = (WifiManager) super.getSystemService(android.content.Context.WIFI_SERVICE);
            WifiConfiguration wc = new WifiConfiguration();
            wc.SSID = "\"" + ssid + "\"";
            wc.preSharedKey = "\"" + password + "\"";

            //    config.preSharedKey = "\"" + Password + "\"";
            wc.hiddenSSID = true;
            wc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wc.status = WifiConfiguration.Status.ENABLED;


            mWifiManager.setWifiEnabled(true);
            int netId = mWifiManager.addNetwork(wc);
            Log.e("WifiActivity", "netId:" + netId);
            if (netId == -1) {
                netId = getExistingNetworkId(wc.SSID);
                Log.e("WifiActivity", "netId:-->getExistingNetworkId -->" + netId);
            }
            mWifiManager.disconnect();
            mWifiManager.enableNetwork(netId, true);
            //  mWifiManager.reconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getExistingNetworkId(String SSID) {
        List<WifiConfiguration> configuredNetworks = mWifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            for (WifiConfiguration existingConfig : configuredNetworks) {
                if (existingConfig.SSID.equals(SSID)) {
                    return existingConfig.networkId;
                }
            }
        }
        return -1;
    }

    public static String getAdressMacByInterface() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }



  /*  private static String getAddressMacByFile(WifiManager wifiMan) throws Exception {
        String ret;
        int wifiState = wifiMan.getWifiState();

        wifiMan.setWifiEnabled(true);
        File fl = new File(fileAddressMac);
        FileInputStream fin = new FileInputStream(fl);
        StringBuilder builder = new StringBuilder();
        int ch;
        while((ch = fin.read()) != -1){
            builder.append((char)ch);
        }

        ret = builder.toString();
        fin.close();

        boolean enabled = WifiManager.WIFI_STATE_ENABLED == wifiState;
        wifiMan.setWifiEnabled(enabled);
        return ret;
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWifiManager = null;
    }
}
