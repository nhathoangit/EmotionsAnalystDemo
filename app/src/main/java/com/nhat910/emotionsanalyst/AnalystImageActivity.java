package com.nhat910.emotionsanalyst;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.microsoft.projectoxford.face.contract.Face;
import com.nhat910.emotionsanalyst.facedetection.EmotionModel;
import com.nhat910.emotionsanalyst.facedetection.FaceDetection;
import com.nhat910.emotionsanalyst.facedetection.FaceResultListener;
import com.nhat910.emotionsanalyst.utils.AppUtils;
import com.ultramegasoft.radarchart.RadarHolder;
import com.ultramegasoft.radarchart.RadarView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnalystImageActivity extends BaseActivity implements FaceResultListener {

    @BindView(R.id.actAnalystImage_ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.actAnalystImage_vContainer)
    View vContainer;
    @BindView(R.id.actAnalystImage_chart)
    RadarView chart;

    private FaceDetection faceDetection;
    private Bitmap bitmap;
    private EmotionModel emotionModel;
    private int totalFaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyst_image);
        ButterKnife.bind(this);
        setActionBar(vContainer, getString(R.string.analyze_photo));
        faceDetection = new FaceDetection(this, this);
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
        totalFaces = result.length;
        ivAvatar.setImageBitmap(AppUtils.drawFaceRectanglesOnBitmap(bitmap, result));
        emotionModel = new EmotionModel();
        emotionModel = getAverageEmotionPoint(result, totalFaces);
        initChart(emotionModel);

    }

    private EmotionModel getEmotions(Face[] faces) {
        EmotionModel emotion = new EmotionModel();
        for (int i = 0; i < faces.length; i++) {
            emotion.anger += faces[i].faceAttributes.emotion.anger;
            emotion.contempt += faces[i].faceAttributes.emotion.contempt;
            emotion.disgust += faces[i].faceAttributes.emotion.disgust;
            emotion.fear += faces[i].faceAttributes.emotion.fear;
            emotion.happiness += faces[i].faceAttributes.emotion.happiness;
            emotion.sadness += faces[i].faceAttributes.emotion.sadness;
            emotion.neutral += faces[i].faceAttributes.emotion.neutral;
            emotion.surprise += faces[i].faceAttributes.emotion.surprise;
        }
        return emotion;
    }

    private EmotionModel getAverageEmotionPoint(Face[] faces, double total) {
        EmotionModel emotion = getEmotions(faces);
        emotion.anger = emotion.anger / total * 100;
        emotion.contempt = emotion.contempt / total * 100;
        emotion.disgust = emotion.disgust / total * 100;
        emotion.fear = emotion.fear / total * 100;
        emotion.happiness = emotion.happiness / total * 100;
        emotion.sadness = emotion.sadness / total * 100;
        emotion.neutral = emotion.neutral / total * 100;
        emotion.surprise = emotion.surprise / total * 100;
        return emotion;
    }

    private void initChart(EmotionModel emotion) {
        ArrayList<RadarHolder> mData = new ArrayList<RadarHolder>() {
            {
                add(new RadarHolder("Anger", (int) Math.round(emotion.anger)));
                add(new RadarHolder("Contempt", (int) Math.round(emotion.contempt)));
                add(new RadarHolder("Disgust", (int) Math.round(emotion.disgust)));
                add(new RadarHolder("Fear", (int) Math.round(emotion.fear)));
                add(new RadarHolder("Happiness", (int) Math.round(emotion.happiness)));
                add(new RadarHolder("Sadness", (int) Math.round(emotion.sadness)));
                add(new RadarHolder("neutral", (int) Math.round(emotion.neutral)));
                add(new RadarHolder("Surprise", (int) Math.round(emotion.surprise)));
            }
        };
        chart.setData(mData);
        chart.setPolygonColor(Color.RED);
    }

}
