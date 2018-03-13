package com.inpos.bitrisetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.billingclient.api.BillingClient;
import com.inpos.bitrisetest.billing.BillingManager;

public class MainActivity extends AppCompatActivity {

    // Properties
    private BillingManager mBillingManager;
    private MainController mMainController;

    // Initialization

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Start the controller and load game data
        mMainController = new MainController(this);

        // Create and initialize BillingManager which talks to BillingLibrary
        mBillingManager = new BillingManager(this, mMainController.getUpdateListener());
        this.updateUI();

//        String urlString = "https://www.delphitools.info/NickelIron/index.html";
//        WebView webView = findViewById(R.id.webView);
//        webView.clearCache(true);
//
//
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//            }
//        });
//
//        CookieManager.getInstance().setCookie(urlString, "lenci=cookie123");
//        webView.loadUrl(urlString);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Note: We query purchases in onResume() to handle purchases completed while the activity
        // is inactive. For example, this can happen if the activity is destroyed during the
        // purchase flow. This ensures that when the activity is resumed it reflects the user's
        // current purchases.
        if (mBillingManager != null && mBillingManager.getBillingClientResponseCode() == BillingClient.BillingResponse.OK) {
            mBillingManager.queryPurchases();
        }
    }

    @Override
    public void onDestroy() {
        if (mBillingManager != null) {
            mBillingManager.destroy();
        }

        super.onDestroy();
    }

    // Public methods

    public void subcribeTapped(View v) {
        this.mBillingManager.initiatePurchaseFlow("test", "subs");
    }

    public void updateUI() {
        ((TextView) findViewById(R.id.tvStatus)).setText(this.mMainController.isUserSubscribed() ? "Subscribed" : "Click the button to subscribe");
    }
}
