package com.nhat910.emotionsanalyst;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/*
 * Created by NhatHoang on 24/07/2019.
 */
public abstract class BaseActivity extends AppCompatActivity {

    View.OnClickListener onBackClick = view -> onBackPressed();

    public void setActionBar(View view, String title) {
        TextView tvTitle = view.findViewById(R.id.actionbar_tvTitle);
        View vBack = view.findViewById(R.id.actionbar_imgBack);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
        if (vBack != null) {
            vBack.setOnClickListener(onBackClick);
        }
    }

}
