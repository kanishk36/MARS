package com.mars.common.widgets;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.mars.R;

public class CustomProgressDialog extends Dialog {

    private String mMessage;

    public CustomProgressDialog(@NonNull Context context) {
        super(context);
    }

    public CustomProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomProgressDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        if(window != null) {
            window.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), android.R.color.transparent));
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }

        setContentView();
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    @SuppressLint("InflateParams")
    private void setContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.progress_loader_layout, null);

        if(mMessage != null) {
            ((TextView) contentView.findViewById(R.id.txtLoadingMessage)).setText(mMessage);
        }
        setContentView(contentView);
    }
}

