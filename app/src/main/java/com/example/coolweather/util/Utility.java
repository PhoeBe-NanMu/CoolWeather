package com.example.coolweather.util;

import android.text.TextUtils;

import com.example.coolweather.db.CoolWeatherDB;
import com.example.coolweather.model.City;
import com.example.coolweather.model.County;
import com.example.coolweather.model.Province;

/**
 * Created by LeiYang on 2016/9/2 0002.
 * 由于服务器返回的省市县数据都是“代号|城市,代号|城市”这种格式的，所以我
 * 们最好再提供一个工具类来解析和处理这种数据
 */

public class Utility {

    /**
     * 解析和处理服务器返回的省级数据
     * @param coolWeatherDB 封装常用的数据库操作
     * @param response 服务器返回的省市县数据
     * @return 结果是否执行成功
     */
    public synchronized static boolean handleProvinceResponse(CoolWeatherDB coolWeatherDB,String response) {

        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces
                     ) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析从服务器返回的市级数据
     * @param coolWeatherDB
     * @param response
     * @param provinceId
     * @return
     */
    public synchronized static  boolean handleCityResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0 ) {
                for (String p : allCities) {
                    String[] array = p.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析从服务器返回的县级数据
     * @param coolWeatherDB
     * @param response
     * @param cityId
     * @return
     */
    public synchronized static  boolean handleCountyResponse(CoolWeatherDB coolWeatherDB,String response,int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0 ) {
                for (String p : allCounties) {
                    String[] array = p.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

}
