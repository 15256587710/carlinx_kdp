package com.ecarxclub.app.base;

public interface BaseView {
    /**
     * 显示dialog
     */
    void showLoading();
    /**
     * 隐藏 dialog
     */

    void hideLoading();

    /**
     * 显示下载文件dialog
     */
    void showLoadingFileDialog();
    /**
     * 隐藏下载文件dialog
     */
    void hideLoadingFileDialog();

    /**
     * 下载进度
     */
    void onProgress(long totalSize, long downSize);

    /**
     * 显示错误信息
     *
     * @param msg
     */
    void showError(String msg);

    /**
     * 错误码
     */
    void onErrorCode(BaseModel model);
}
