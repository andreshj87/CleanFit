package com.zireck.calories.presentation.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * Created by Zireck on 28/07/2015.
 */
public class PictureUtils {
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public static final String FILE_EXTENSION = ".jpg";
    private static final String FOLDER_NAME = "CleanFit";
    public static final String TEMP_PICTURE_NAME = "temp" + FILE_EXTENSION;

    public PictureUtils() {

    }

    public static String getFolderName() {
        return PictureUtils.FOLDER_NAME;
    }

    public static String getFullPath(String folderName, String fileName) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), folderName);
        StringBuilder path = new StringBuilder(mediaStorageDir.getPath());
        path.append(File.separator);
        path.append(fileName);

        return path.toString();
    }

    public static String generateFileName() {
        StringBuilder fileName = new StringBuilder();

        fileName.append(String.valueOf(System.currentTimeMillis()));
        fileName.append(PictureUtils.FILE_EXTENSION);

        return fileName.toString();
    }

    public static Intent getIntentForCameraLaunch(String fileName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(PictureUtils.FOLDER_NAME, fileName));
        return intent;
    }

    public static Uri getPhotoFileUri(String fileName) {
        return PictureUtils.getPhotoFileUri(PictureUtils.getFolderName(), fileName);
    }

    public static Uri getPhotoFileUri(String folderName, String fileName) {
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

    public static File getPictureFile(String fileName) {
        Uri uri = getPhotoFileUri(PictureUtils.FOLDER_NAME, fileName);

        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), PictureUtils.FOLDER_NAME);

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(PictureUtils.FOLDER_NAME, "failed to create directory");
            return null;
        }

        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    public static boolean checkIfPictureExists(String fileName) {
        return getPictureFile(fileName).exists();
    }

    public static boolean deletePicture(String fileName) {
        if (checkIfPictureExists(fileName)) {
            return getPictureFile(fileName).delete();
        }

        return false;
    }

    public static boolean renamePictureTo(String fileName, String newFileName) {
        if (checkIfPictureExists(fileName) && !TextUtils.isEmpty(newFileName)) {
            return getPictureFile(fileName).renameTo(getPictureFile(newFileName));
        } else {
            return false;
        }
    }

    public static boolean renameTempPictureTo(String fileName) {
        return renamePictureTo(PictureUtils.TEMP_PICTURE_NAME, fileName);
    }
}
