package com.nghiatl.common.file

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.*
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Nghia-PC on 8/1/2015.
 */
object FileUtil {
    /**
     * Copy file từ Asset to File
     *
     * @param sourcePath Nguồn file copy
     * @param targetPath      Đích copy đến
     * @param override        Ghi đè
     * @return Kết quả, True = Thành công
     */
    fun copyFile(sourcePath: String?, targetPath: String, override: Boolean): Boolean {
        try {
            // Kiểm tra ghi đè, tồn tại
            if (!override) {
                val file = File(targetPath)
                if (file.exists()) return false
            }
            makeFileDirectory(targetPath) // tạo thư mục

            // Mở file = InputStream
            val input: InputStream = FileInputStream(sourcePath)
            val output: OutputStream = FileOutputStream(targetPath)
            return writeStream(input, output)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun writeStream(input: InputStream, output: OutputStream): Boolean {
        try {
            // Chuyển byte từ Input => Output
            val buffer = ByteArray(1024)
            var length: Int
            while (input.read(buffer).also { length = it } > 0) {
                output.write(buffer, 0, length)
            }

            // Close the streams
            output.flush()
            output.close()
            input.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * Tạo 1 Đường Dẫn File
     *
     * @param folderPath   đường dẫn phụ
     * @param prefix    key word đầu tiên (userId) của file
     * @param extension phần mở rộng của file (không chấm)
     * @return
     */
    fun makeFilePathByTime(folderPath: String, prefix: String, extension: String): String {
        var resultPath = ""
        val date = makeFileNameByTime()
        resultPath = folderPath + "/" + prefix + "_" + date + "." + extension
        return resultPath
    }

    fun makeFileNameByTime(): Long {
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault())
        return java.lang.Long.valueOf(dateFormat.format(Date()))
    }

    /**
     * Tạo thư mục chứa file
     * @param filePath
     */
    fun makeFileDirectory(filePath: String): Boolean {
        val folderTargetPath = getFileDirectory(filePath)
        val folder = File(folderTargetPath)
        return if (!folder.exists() || !folder.isDirectory) folder.mkdirs() else true
    }

    /**
     * Get Folder from File path
     * @param filePath
     * @return
     */
    fun getFileDirectory(filePath: String): String {
        return filePath.substring(0, filePath.lastIndexOf("/"))
    }

    /**
     * Lấy file Name từ filePath
     * @param filePath
     * @return
     */
    fun getFileName(filePath: String): String {
        return filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length)
    }

    /**
     * Lấy phần mở rông của File
     * @param url
     * @return
     */
    fun getFileExt(url: String): String? {
        var url = url
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"))
        }
        return if (url.lastIndexOf(".") == -1) {
            null
        } else {
            var ext = url.substring(url.lastIndexOf(".") + 1)
            if (ext.contains("%")) {
                ext = ext.substring(0, ext.indexOf("%"))
            }
            if (ext.contains("/")) {
                ext = ext.substring(0, ext.indexOf("/"))
            }
            ext.lowercase(Locale.getDefault())
        }
    }

    /**
     * Tạo file nếu chưa tồn tại
     */
    @JvmStatic
    @Throws(IOException::class)
    fun createNewFile(filePath: String): Boolean {
        val logFile = File(filePath)
        if (!logFile.exists()) {
            // tạo thư mục
            if (!makeFileDirectory(filePath)) return false
            if (!logFile.createNewFile()) return false
        }
        return true
    }

    /**
     * Kiểm tra file Rỗng, không có dữ liệu
     */
    @JvmStatic
    @Throws(IOException::class)
    fun hasData(filePath: String?): Boolean {
        val br = BufferedReader(FileReader(filePath))
        return br.readLine() != null
    }

    /**
     * Mở thư mục bằng App sẵn có
     * @param folderPath = Environment.getExternalStorageDirectory().getPath() + tên folder
     */
    fun openFolder(context: Context, folderPath: String?, fileType: String?) {
        // loại file xem
        var fileType = fileType
        if (fileType == null) fileType = "*/*"
        val intent = Intent(Intent.ACTION_VIEW)
        val uri = Uri.parse(folderPath)
        intent.setDataAndType(uri, fileType)
        //startActivity(Intent.createChooser(intent, "Open folder"));
        context.startActivity(intent)
    }

    /**
     * Get all file path in source path folder
     * @param sourceFile
     * @param resultFiles list file path return
     * @return
     */
    @JvmStatic
    fun getFiles(sourceFile: File, resultFiles: MutableList<File?>) {
        if (sourceFile.isDirectory) {
            // recursive folder
            val files = sourceFile.listFiles()
            for (currFile in files) {
                getFiles(currFile, resultFiles)
            }
        } else {
            // add file
            resultFiles.add(sourceFile)
        }
    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String?): String {
        val manager = context.assets
        val inputStream = manager.open(jsonFileName!!)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charset.forName("UTF-8"))
    }

    /*public void openFileWithType(Context context, String filePath) {
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
    }*/
}