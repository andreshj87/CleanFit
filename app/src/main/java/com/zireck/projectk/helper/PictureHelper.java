package com.zireck.projectk.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.zireck.projectk.R;
import com.zireck.projectk.util.BitmapUtils;

import java.io.File;

/**
 * Created by Zireck on 28/07/2015.
 */
public class PictureHelper {
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public static final String FILE_EXTENSION = ".jpg";

    private String mFolderName;

    public PictureHelper(Context context) {
        mFolderName = context.getResources().getString(R.string.app_name);
    }

    public String getFolderName() {
        return mFolderName;
    }

    public String generateFileName() {
        StringBuilder fileName = new StringBuilder();

        fileName.append(String.valueOf(System.currentTimeMillis()));
        fileName.append(PictureHelper.FILE_EXTENSION);

        return fileName.toString();
    }

    public Intent getIntentForCameraLaunch(String fileName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(mFolderName, fileName));
        return intent;
    }

    @Deprecated
    public Bitmap getPictureBitmap(Uri pictureUri) {
        return BitmapFactory.decodeFile(pictureUri.getPath());
    }

    public Bitmap getSampledPictureBitmap(Uri pictureUri, int width, int height) {
        return BitmapUtils.decodeSampledBitmapFromUri(pictureUri, width, height);
    }

    public Bitmap getSampledPictureBitmap(String fileName, int width, int height) {
        return getSampledPictureBitmap(getPhotoFileUri(mFolderName, fileName), width, height);
    }

    public Uri getPhotoFileUri(String folderName, String fileName) {
        // Get safe storage directory for photos
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), folderName);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(folderName, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
    }

    public File getPictureFile(String fileName) {
        Uri uri = getPhotoFileUri(mFolderName, fileName);

        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), mFolderName);

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(mFolderName, "failed to create directory");
            return null;
        }

        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    public boolean checkIfPictureExists(String fileName) {
        return getPictureFile(fileName).exists();
    }

    public boolean deletePicture(String fileName) {
        if (checkIfPictureExists(fileName)) {
            return getPictureFile(fileName).delete();
        }

        return false;
    }
}
