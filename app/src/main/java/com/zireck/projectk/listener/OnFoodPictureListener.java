package com.zireck.projectk.listener;

import android.net.Uri;

import java.io.File;

/**
 * Created by Zireck on 27/07/2015.
 */
public interface OnFoodPictureListener {
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public void launchCamera(String currentPictureFileName);
    public Uri getPhotoFileUri(String folderName, String fileName);
    public boolean deletePicture(String fileName);
    public boolean checkIfPictureExists(String fileName);
    public File getPictureFile(String fileName);
}
