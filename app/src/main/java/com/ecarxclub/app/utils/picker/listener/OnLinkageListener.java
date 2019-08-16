package com.ecarxclub.app.utils.picker.listener;

import com.ecarxclub.app.utils.picker.model.Province;

/**
 * @author matt
 * blog: addapp.cn
 */

public interface OnLinkageListener {
    /**
     * 选择地址
     *
     * @param province the province
     * @param city    the city
     * @param county   the county ，if {@code hideCounty} is true，this is null
     */
    void onAddressPicked(Province province, Province.City city, Province.City.County county);
}
