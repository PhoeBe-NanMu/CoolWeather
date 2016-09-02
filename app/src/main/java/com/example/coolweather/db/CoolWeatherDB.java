package com.example.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coolweather.model.City;
import com.example.coolweather.model.County;
import com.example.coolweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeiYang on 2016/8/30 0030.
 *
 * 该部分把常用的数据库操作封装起来，以便后面使用
 *
 * ContentValues 和HashTable类似都是一种存储的机制
 * 但是两者最大的区别就在于，ContentValues只能存储基本类型的数据，像string，int之类的，不能存储对象这种东西，而HashTable却可以存储对象。
 * ContentValues initialValues = new ContentValues();
 * initialValues.put(key,values);
 * SQLiteDataBase sdb ;
 * sdb.insert(database_name,null,initialValues);
 * 插入成功就返回记录的id否则返回-1；
 *
 */

public class CoolWeatherDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "cool_weather";

    /**
     * 数据库版本
     */
    public static final int VERSION = 1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     */
    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper coolWeatherOpenHelper = new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
        db = coolWeatherOpenHelper.getWritableDatabase();

    }

    /**
     * 获取CoolWeatherDB的实例
     */
    public synchronized static CoolWeatherDB getInstance(Context context) {

        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    /**
     * 将Province实例存储在数据库
     */
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }

    /**
     * 从数据库中读取全国所有省份的信息
     */
    public List<Province> loadProvinces() {
        List<Province> list = new ArrayList<>() ;
        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        while (cursor.moveToNext()) {
            Province province = new Province();
            province.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
            province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
            list.add(province);
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    /**
     * 将City实例保存到数据库
     */
    public void saveCity(City city) {
        if (city != null ) {
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
        }
    }

    /**
     * 从数据库中获取全国每个城市的信息
     */
    public List<City> loadCities(int provinceId) {
        List<City> list = new ArrayList<>();
        Cursor cursor = db.query("City",null,"province_id = ?",new String[]{String.valueOf(provinceId)},null,null,null);
        while (cursor.moveToNext()) {
            City city = new City();
            city.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
            city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
            city.setProvinceId(provinceId);
            list.add(city);
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    /**
     * 将County实例保存到数据库中
     */
    public void saveCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            db.insert("County",null,values);
        }
    }

    /**
     * 从数据库中获取每个县的信息
     */
    public List<County> loadCounties(int cityId) {
        List<County> list = new ArrayList<>();
        Cursor cursor = db.query("County",null,"city_id = ?",new String[]{String.valueOf(cityId)},null,null,null);
        while (cursor.moveToNext()) {
            County county = new County();
            county.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
            county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
            county.setCityId(cityId);
            list.add(county);
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }
}
