package com.scan.studentformapp.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileUtils {

    public static File getFileFromUri(Context context, Uri uri) {
        try {
            String fileName = getFileName(context, uri);
            File file = new File(context.getCacheDir(), fileName);

            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream == null) return null;

            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFileName(Context context, Uri uri) {
        String name = "file";
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex >= 0) {
                        name = cursor.getString(nameIndex);
                    }
                }
            } finally {
                cursor.close();
            }
        }

        return name;
    }

    public static long getFileSizeInKB(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                    if (sizeIndex >= 0) {
                        long sizeInBytes = cursor.getLong(sizeIndex);
                        return sizeInBytes / 1024; // Convert to KB
                    }
                }
            } finally {
                cursor.close();
            }
        }

        return 0;
    }

    public static MultipartBody.Part createMultipartBodyPart(File file, String partName) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/pdf"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static RequestBody createRequestBody(String text) {
        return RequestBody.create(MediaType.parse("text/plain"), text);
    }
}
