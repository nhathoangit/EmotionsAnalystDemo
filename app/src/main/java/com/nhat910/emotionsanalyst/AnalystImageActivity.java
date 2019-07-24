package com.nhat910.emotionsanalyst;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.microsoft.projectoxford.face.contract.Face;
import com.nhat910.emotionsanalyst.facedetection.FaceDetection;
import com.nhat910.emotionsanalyst.facedetection.FaceResultListener;
import com.nhat910.emotionsanalyst.utils.AppUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnalystImageActivity extends BaseActivity implements FaceResultListener {

    @BindView(R.id.actAnalystImage_ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.actAnalystImage_vContainer)
    View vContainer;

    private FaceDetection faceDetection;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyst_image);
        ButterKnife.bind(this);
        setActionBar(vContainer, getString(R.string.analyze_photo));
        faceDetection = new FaceDetection(this,this);
    }

    @OnClick({R.id.actAnalystImage_ivAvatar, R.id.actAnalystImage_frmAnalyze})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.actAnalystImage_ivAvatar:
                ImagePicker.create(this)
                        .single()
                        .start();
                break;
            case R.id.actAnalystImage_frmAnalyze:
               faceDetection.detectAndFrame(bitmap);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
            bitmap = BitmapFactory.decodeFile(image.getPath());
            Glide.with(this).load(image.getPath()).into(ivAvatar);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onFaceResultListener(Face[] result) {
        ivAvatar.setImageBitmap(AppUtils.drawFaceRectanglesOnBitmap(bitmap, result));
    }
}
