package com.nghiatl.common.file;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.nghiatl.common.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Nghia-PC on 8/1/2015.
 */
public class FileUtil {

    /**
     * Copy file từ Asset to File
     *
     * @param sourcePath Nguồn file copy
     * @param targetPath      Đích copy đến
     * @param override        Ghi đè
     * @return Kết quả, True = Thành công
     */
    public static boolean copyFile(String sourcePath, String targetPath, boolean override) {
        try {
            // Kiểm tra ghi đè, tồn tại
            if (!override) {
                File file = new File(targetPath);
                if (file.exists())
                    return false;
            }
            makeFileDirectory(targetPath);  // tạo thư mục

            // Mở file = InputStream
            InputStream input = new FileInputStream(sourcePath);
            OutputStream output = new FileOutputStream(targetPath);
            return writeStream(input, output);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean writeStream(InputStream input, OutputStream output){
        try {
            // Chuyển byte từ Input => Output
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            input.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Tạo 1 Đường Dẫn File
     *
     * @param folderPath   đường dẫn phụ
     * @param prefix    key word đầu tiên (userId) của file
     * @param extension phần mở rộng của file (không chấm)
     * @return
     */
    public static String makeFilePathByTime(String folderPath, String prefix, String extension) {
        String resultPath = "";

        long date = makeFileNameByTime();

        resultPath = folderPath + "/" + prefix + "_" + date + "." + extension;

        return resultPath;
    }

    public static long makeFileNameByTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
        return Long.valueOf(dateFormat.format(new Date()));
    }

    /**
     * Tạo thư mục chứa file
     * @param filePath
     */
    public static boolean makeFileDirectory(String filePath){
        String folderTargetPath = getFileDirectory(filePath);
        File folder = new File(folderTargetPath);

        if (!folder.exists() || !folder.isDirectory())
            return folder.mkdirs();

        return true;
    }

    /**
     * Get Folder from File path
     * @param filePath
     * @return
     */
    public static String getFileDirectory(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf("/"));
    }

    /**
     * Lấy file Name từ filePath
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
    }

    /**
     * Lấy phần mở rông của File
     * @param url
     * @return
     */
    public static String getFileExt(String url) {
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.contains("%")) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.contains("/")) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();
        }
    }

    /**
     * Tạo file nếu chưa tồn tại
     */
    static boolean createNewFile(String filePath) throws IOException {
        File logFile = new File(filePath);
        if (!logFile.exists())
        {
            // tạo thư mục
            if (!makeFileDirectory(filePath)) return false;
            if(!logFile.createNewFile()) return false;
        }
        return true;
    }


    /**
     * Kiểm tra file Rỗng, không có dữ liệu
     */
    public static boolean hasData(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        return br.readLine() != null;
    }

    /**
     * Mở thư mục bằng App sẵn có
     * @param folderPath = Environment.getExternalStorageDirectory().getPath() + tên folder
     */
    public static void openFolder(Context context, String folderPath, @Nullable String fileType)
    {
        // loại file xem
        if (fileType == null) fileType = "*/*";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(folderPath);
        intent.setDataAndType(uri, fileType);
        //startActivity(Intent.createChooser(intent, "Open folder"));
        context.startActivity(intent);
    }


    /**
     * Get all file path in source path folder
     * @param sourceFile
     * @param resultFiles list file path return
     * @return
     */
    public static void getFiles(File sourceFile, List<File> resultFiles) {
        if (sourceFile.isDirectory()) {
            // recursive folder
            File[] files = sourceFile.listFiles();
            for(File currFile : files){
                getFiles(currFile, resultFiles);
            }
        } else {
            // add file
            resultFiles.add(sourceFile);
        }
    }


    public static String loadJSONFromAsset(Context context, String jsonFileName) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public void openFileWithType(Context context, String filePath) {
        MimeTypeMap myMime = MimeTypeMap.getSingleton();
        Intent newIntent = new Intent(Intent.ACTION_VIEW);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String mimeType = myMime.getMimeTypeFromExtension(FileUtil.getFileExt(filePath));
        Uri fileUri = Uri.fromFile(new File(filePath));
        if (Build.VERSION.SDK_INT >= 21)
            fileUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", new File(filePath));
        newIntent.setDataAndType(fileUri, mimeType);

        try {
            context.startActivity(newIntent);
        } catch (Exception ex) {
            Toast.makeText(context, "No handler for this type of file.", Toast.LENGTH_LONG).show();
        }
    }
}
