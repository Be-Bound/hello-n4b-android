package io.n4b.sample.hellon4b;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.bebound.common.model.request.Request;
import com.bebound.common.model.request.Response;

public class ResponseReceiver extends HelloN4B.ResponseReceiver {

    @Override
    public void onSimpleSum(final Context context, Request rawRequest, Response rawResponse, SimpleSumRequest request, SimpleSumResponse response) {
        final int result = response.result;
        Log.d(TAG, "SimpleSum result: " + result);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Response from receiver: " + result, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onSum(final Context context, Request rawRequest, Response rawResponse, SumRequest request, SumResponse response) {
        final int result = response.result;
        Log.d(TAG, "Sum result: " + result);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Response from receiver: " + result, Toast.LENGTH_LONG).show();
            }
        });
    }
}
