package io.n4b.sample.hellon4b;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bebound.common.exception.BeBoundException;
import com.bebound.common.listener.request.OnFailedListener;
import com.bebound.common.listener.request.OnSuccessListener;
import com.bebound.common.model.request.Request;
import com.bebound.common.model.request.Response;
import com.bebound.sdk.BeBound;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FloatingActionButton btSendRequest;
    private TextView lblResult;
    private EditText editA, editB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSendRequest = (FloatingActionButton) findViewById(R.id.bt_send_request);
        btSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lblResult.setText("");
                setButtonVisible(false);
                sendSimpleSumRequest();
            }
        });
        editA = (EditText) findViewById(R.id.edit_a);
        editB = (EditText) findViewById(R.id.edit_b);
        lblResult = (TextView) findViewById(R.id.lbl_output_result);

        final InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

        final InputFilter[] filters = {filter, new InputFilter.LengthFilter(3)};

        editA.setFilters(filters);
        editB.setFilters(filters);

        Log.d(TAG, "Ready");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "BeBound.isBeBoundServicesFound(): " + BeBound.areBeBoundServicesFound());
        Log.d(TAG, "BeBound.getBeBoundServicesPackageName(): " + BeBound.getBeBoundServicesPackageName());
        Log.d(TAG, "BeBound.isBeAppAuthenticated(): " + BeBound.isBeAppAuthenticated());
        Log.d(TAG, "BeBound.isUserAuthenticated(): " + BeBound.isUserAuthenticated());
    }

    private void sendSimpleSumRequest() {
        if (editA.getText().toString().isEmpty())
            editA.setText("0");
        if (editB.getText().toString().isEmpty())
            editB.setText("0");

        final SimpleSumRequest request =
                SimpleSumRequest.builder()
                                .withA(Integer.parseInt(editA.getText().toString().replace(".", "").replace(",", "")))
                                .withB(Integer.parseInt(editB.getText().toString().replace(".", "").replace(",", "")));

        try {
            Log.d(TAG, "Sending simple_sum request");
            request.toRequest()
                   .onSuccess(new OnSuccessListener() {
                       @Override
                       public boolean onSuccess(Context context, Response response, Request request) {
                           final int result = SimpleSumResponse.fromResponse(response).result;
                           Log.d(TAG, "Result: " + result);
                           setButtonVisible(true);
                           showResult(result);
                           return true;
                       }
                   })
                   .onFailed(new OnFailedListener() {
                       @Override
                       public boolean onResponseError(Context context, Request request, Response response, int responseStatusCode, String responseStatusMessage) {
                           setButtonVisible(true);
                           Log.d(TAG, "Response error: " + responseStatusCode + ", " + responseStatusMessage);
                           return super.onResponseError(context, request, response, responseStatusCode, responseStatusMessage);
                       }

                       @Override
                       public boolean onRequestFailed(Context context, Request request, int requestStatusCode, String requestStatusMessage) {
                           setButtonVisible(true);
                           Log.d(TAG, "Request failed: " + requestStatusCode + ", " + requestStatusMessage);
                           return super.onRequestFailed(context, request, requestStatusCode, requestStatusMessage);
                       }
                   })
                   .send();
        } catch (BeBoundException e) {
            Log.wtf("Hello-N4B", "Error while sending simple sum request", e);
        }
    }

    private void sendSumRequest() {
        final SumRequest.ValueList values = new SumRequest.ValueList()
                .addValue(new SumRequest.Value().withValue(5))
                .addValue(new SumRequest.Value().withValue(10))
                .addValue(new SumRequest.Value().withValue(16));

        final SumRequest request = SumRequest.builder()
                                             .withValues(values);

        try {
            Log.d(TAG, "Sending sum request");
            request.toRequest().send();
        } catch (BeBoundException e) {
            Log.wtf("Hello-N4B", "Error while sending sum request", e);
        }
    }

    private void setButtonVisible(final boolean visible) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (visible)
                    btSendRequest.show();
                else
                    btSendRequest.hide();
                btSendRequest.setEnabled(visible);
            }
        });
    }

    private void showResult(final int result) {
        lblResult.post(new Runnable() {
            @Override
            public void run() {
                lblResult.setText(Integer.toString(result));
            }
        });
    }
}
