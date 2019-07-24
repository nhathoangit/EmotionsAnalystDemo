package com.nhat910.emotionsanalyst.facedetection;

import com.microsoft.projectoxford.face.contract.Face;

/*
 * Created by NhatHoang on 24/07/2019.
 */
public interface FaceResultListener {
    void onFaceResultListener(Face[] result);
}
