/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.inpos.bitrisetest;

import android.util.Log;

import com.android.billingclient.api.BillingClient.BillingResponse;
import com.android.billingclient.api.Purchase;
import com.inpos.bitrisetest.billing.BillingManager;

import java.util.List;

/**
 * Handles control logic of the BaseGamePlayActivity
 */
public class MainController {
    private static final String TAG = "MainController";

    // How many units (1/4 tank is our unit) fill in the tank.
    private static final int TANK_MAX = 4;

    private final UpdateListener mUpdateListener;
    private MainActivity mActivity;

    // Tracks if we currently own subscriptions SKUs
    private boolean mSubcribed;

    // Current amount of gas in tank, in units
    private int mTank;

    public MainController(MainActivity activity) {
        mUpdateListener = new UpdateListener();
        mActivity = activity;
    }

    public UpdateListener getUpdateListener() {
        return mUpdateListener;
    }

    public boolean isUserSubscribed() {
        return mSubcribed;
    }

    /**
     * Handler to billing updates
     */
    private class UpdateListener implements BillingManager.BillingUpdatesListener {
        @Override
        public void onBillingClientSetupFinished() {
            String s = "";
        }

        @Override
        public void onConsumeFinished(String token, @BillingResponse int result) {
            Log.d(TAG, "Consumption finished. Purchase token: " + token + ", result: " + result);
            // If you have more than one sku, you probably need to validate that the token matches
            // the SKU you expect.
            // It could be done by maintaining a map (updating it every time you call consumeAsync)
            // of all tokens into SKUs which were scheduled to be consumed and then looking through
            // it here to check which SKU corresponds to a consumed token.
            if (result == BillingResponse.OK) {
                // Successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit
                Log.d(TAG, "Consumption successful. Provisioning.");
            } else {
            }

            Log.d(TAG, "End consumption flow.");
        }

        @Override
        public void onPurchasesUpdated(List<Purchase> purchaseList) {
            mSubcribed = false;

            if (purchaseList.size() == 0) {
                mSubcribed = false;
                mActivity.updateUI();
                Log.d(TAG, "Purchase list 0 count.");
                return;
            }

            for (Purchase purchase : purchaseList) {

            }
            Log.d(TAG, "Purchase list count > 0");

            mActivity.updateUI();
        }
    }
}