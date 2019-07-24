package com.nhat910.emotionsanalyst;

import android.os.Bundle;
import android.view.View;

import com.nhat910.emotionsanalyst.utils.StartActivityUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.actMain_frmAnalyzePhoto, R.id.actMain_frmAnalyzeVideo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.actMain_frmAnalyzePhoto:
                StartActivityUtils.toAnalyzePhoto(this);
                break;
            case R.id.actMain_frmAnalyzeVideo:
                break;
        }
    }
}
