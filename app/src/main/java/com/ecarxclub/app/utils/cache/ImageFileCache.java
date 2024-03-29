package com.ecarxclub.app.utils.cache;

/**
 * Created by cwp
 * on 2019/5/17 15:12.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

import com.ecarxclub.app.common.YxbApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 文件缓存
 *
 * @author
 */

public class ImageFileCache {

// sd卡上的缓存文件夹

    private static final String CACHDIR = "DownLoad/kadoupai";

    //自定义路径2
    private static final String CACHE_DIY="";

// 定义缓存文件后缀

    private static final String WHOLESALE_CONV = ".cache";

// 缓存空间大小

    private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;

// 规定的最大缓存

    private static final int CACHE_SIZE = 10;

// 规定1MB大小

    private static final int MB = 1024 * 1024;

// 设置缓存文件过期时间为10天

    private static final long MTIMEDIFF = 10 * 24 * 60 * 60 * 1000;

    public ImageFileCache() {

// 清理文件缓存

        removeCache(getDirectory());

    }

    /**
     * 根据url在图片缓存中得到图片
     *
     * @param url
     * @return
     */

    public Bitmap getImage(final String url) {

        final String path = getDirectory() + "/" + convertUrlToFileName(url);

        File file = new File(path);

        if (file.exists()) {

            Bitmap bmp = BitmapFactory.decodeFile(path);

            if (bmp == null) {

                file.delete();

            } else {

// 更新图片最后修改时间

                updateFileTime(path);

                return bmp;

            }

        }

        return null;

    }

    /**
     * 将图片保存到sd卡
     *
     * @param bm
     * @param url
     */

    public void saveBmpToSd(Bitmap bm, String url) {

        if (bm == null) {

// 需要保存的是一个空值

            return;

        }

// 判断sdcard上的空间

        if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {

// SD空间不足

            return;

        }

        String filename = convertUrlToFileName(url);

        String dir = getDirectory();

        File file = new File(dir + "/" + filename);

        try {

            file.getParentFile().mkdirs();

            file.createNewFile();

            OutputStream outStream = new FileOutputStream(file);

            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);

            outStream.flush();

            outStream.close();

        } catch (FileNotFoundException e) {

            Log.w("ImageFileCache", "FileNotFoundException");

        } catch (IOException e) {

            Log.w("ImageFileCache", "IOException");

        }

    }

    /**
     * 计算存储目录下的文件大小，
     * <p>
     * 当文件总大小大于规定的CACHE_SIZE或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定
     * <p>
     * 那么删除40%最近没有被使用的文件
     *
     * @param dirPath
     */

    private boolean removeCache(String dirPath) {

        File dir = new File(dirPath);

        File[] files = dir.listFiles();

        if (files == null) {

            return true;

        }

// 判断是否可操作sd卡

        if (!existSDCard()) {

            return false;

        }

// 计算出缓存文件总大小

        int dirSize = 0;

        for (int i = 0; i < files.length; i++) {

            if (files[i].getName().contains(WHOLESALE_CONV)) {

// 删除三天过期文件

                removeExpiredCache(files[i]);

                dirSize += files[i].length();

            }

        }

        if (dirSize > CACHE_SIZE * MB

                || FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {

// 删除百分之40

            int removeFactor = (int) ((0.4 * files.length) + 1);

// 根据最后修改时间排序

            Arrays.sort(files, new FileLastModifSort());

            try {

                Log.i("ImageFileCache", "清理缓存文件");

                for (int i = 0; i < removeFactor; i++) {

                    if (files[i].getName().contains(WHOLESALE_CONV)) {

                        files[i].delete();

                    }

                }

            } catch (IndexOutOfBoundsException e) {

                e.printStackTrace();

            }

        }

        if (freeSpaceOnSd() <= CACHE_SIZE) {
            return false;
        }

        return true;

    }

    /**
     * 根据文件的最后修改时间进行排序
     */
    private class FileLastModifSort implements Comparator {
        @Override
        public int compare(Object arg0, Object arg1) {

            if (((File)arg0).lastModified() > ((File)arg1).lastModified()) {

                return 1;

            } else if (((File)arg0).lastModified() == ((File)arg1).lastModified()) {

                return 0;

            } else {

                return -1;

            }

        }
    }

    /**
     * 删除超过三天的过期文件
     */

    public void removeExpiredCache(File file) {

        if (System.currentTimeMillis() - file.lastModified() > MTIMEDIFF) {

            Log.i("ImageFileCache", "清除三天过期缓存文件");

            file.delete();

        }

    }

    /**
     * 修改文件的最后修改时间 这里需要考虑,是否将使用的图片日期改为当前日期
     *
     * @param path
     */

    public void updateFileTime(String path) {

        File file = new File(path);

        long newModifiedTime = System.currentTimeMillis();

        file.setLastModified(newModifiedTime);

    }

    /**
     * 计算sdcard上的剩余空间
     *
     * @return
     */

    private int freeSpaceOnSd() {

        StatFs stat = new StatFs(Environment.getExternalStorageDirectory()

                .getPath());

        double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat

                .getBlockSize()) / MB;

        return (int) sdFreeMB;

    }

    /**
     * 将url转成文件名
     *
     * @param url
     * @return
     */

    private String convertUrlToFileName(String url) {

        String[] strs = url.split("/");

        return strs[strs.length - 1] + WHOLESALE_CONV;

    }

    /**
     * 获得缓存目录
     *
     * @return
     */

    private static String getDirectory() {

        String dir = getSDPath() + "/" + CACHDIR;

        String substr = dir.substring(0, 4);

        if (substr.equals("/mnt")) {

            dir = dir.replace("/mnt", "");

        }

        return dir;

    }

    /**
     * 取SD卡路径
     *
     * @return
     */

    public static String getSDPath() {

        File sdDir = null;
        if (existSDCard()) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }

        if (sdDir != null) {

            return sdDir.toString();

        } else {

            return "";

        }

    }

    public static String ClearCache() {

//        ImageMemoryCache.clear();

        String clearResult1 = ClearFiles(getDirectory());

        String clearResult2 = ClearFiles(getSDPath()+CACHE_DIY);

        String result = "";

        if (!clearResult1.equals("缓存清除成功") || !clearResult2.equals("缓存清除成功")) {

            if (!clearResult1.equals("缓存清除成功")) {

                result += clearResult1;

            }

            if (!clearResult2.equals("缓存清除成功") && !result.equals(clearResult1)) {

                result += clearResult2;

            }

        } else {

            result = "缓存清除成功";

        }

        return result;

    }

    private static String ClearFiles(String dirPath) {

        File dir = new File(dirPath);

        File[] files = dir.listFiles();

        if (files == null) {

            return "缓存清除成功";

        }

// 判断是否可操作sd卡

        if (!existSDCard()) {

            return "没有权限操作sd card";

        }

        for (int i = 0; i < files.length; i++) {

            if (files[i].getName().contains(WHOLESALE_CONV)) {

                files[i].delete();

            }

        }

        return "缓存清除成功";

    }

    public static long calculateImageSize() {

        String dirPath = getDirectory();

        File dir = new File(dirPath);

        File[] files = dir.listFiles();

        if (files == null) {

            return 0;

        }

// 判断是否可操作sd卡

        if (!existSDCard()) {

            return 0;

        }

// 计算出缓存文件总大小

        int dirSize = 0;

        for (int i = 0; i < files.length; i++) {

            if (files[i].getName().contains(WHOLESALE_CONV)) {

                dirSize += files[i].length();

            }

        }

        return dirSize;

    }

    //SD是否存在
    private static boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            Toast.makeText(YxbApplication.getContext(),"您SD卡不存在",Toast.LENGTH_SHORT).show();
            return false;
    }

}