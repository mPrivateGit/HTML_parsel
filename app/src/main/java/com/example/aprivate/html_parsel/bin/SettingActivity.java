package com.example.aprivate.html_parsel.bin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.log.LogApp;

public class SettingActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnKeyListener {
    protected Button mBtnCancel;
    protected Button mBtnOk;
    protected EditText mEdtName;
    protected EditText mEdtLowPrice;
    protected EditText mEdtHighPrice;
    protected Spinner mSpnCategory;
    protected Spinner mSpnUnderCategory;
    protected Spinner mSpnWebSite;
    protected Spinner mSpnColor;
    protected Spinner mSpnSearchDate;

    protected String mSearchProductName;
    protected String mSearchProductCategory;
    protected String mSearchProductUnderCategory;
    protected String mSearchProductWebSite;
    protected String mSearchProductColor;
    protected String mSearchProductDateAdded;
    protected int mSearchProductLowPrice;
    protected int mSearchProductHighPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);

        /*EditText(ы)*/
        viewEditTexts();

        /*Кнопки*/
        viewButtons();

        /*Спинеры*/
        viewSpinners();
    }

    private void viewEditTexts(){
        mEdtName = (EditText) findViewById(R.id.edt_search_product_full_name);
        mEdtLowPrice = (EditText) findViewById(R.id.edt_search_product_low_price);
        mEdtHighPrice = (EditText) findViewById(R.id.edt_search_product_high_price);
    }

    private void viewButtons(){
        mBtnCancel = (Button) findViewById(R.id.btn_cancel_action);
        mBtnCancel.setOnClickListener(this);
        mBtnOk = (Button) findViewById(R.id.btn_save_action);
        mBtnOk.setOnClickListener(this);
    }

    private void viewSpinners(){
        //Главная категория
        mSpnCategory = (Spinner) findViewById(R.id.spinner_category);
        mSpnCategory.setOnItemSelectedListener(this);
        ArrayAdapter<?> adapterCategory =
                ArrayAdapter.createFromResource(this, R.array.category,
                        android.R.layout.simple_spinner_dropdown_item);
        mSpnCategory.setAdapter(adapterCategory);
        adapterCategory.notifyDataSetChanged();

        //Подкатегория
        mSpnUnderCategory = (Spinner) findViewById(R.id.spinner_under_category);
        mSpnUnderCategory.setVisibility(View.GONE);
        mSpnUnderCategory.setOnItemSelectedListener(this);
        ArrayAdapter<?> adapterUnderCategory =
                ArrayAdapter.createFromResource(this, R.array.Electronics_Computers_or_Office,
                        android.R.layout.simple_spinner_dropdown_item);
        mSpnUnderCategory.setAdapter(adapterUnderCategory);
        adapterUnderCategory.notifyDataSetChanged();

        //Сайты
        mSpnWebSite = (Spinner) findViewById(R.id.spinner_search_site);
        mSpnWebSite.setOnItemSelectedListener(this);
        ArrayAdapter<?> adapterWebSiteCategory =
                ArrayAdapter.createFromResource(this, R.array.WebSites,
                        android.R.layout.simple_spinner_dropdown_item);
        mSpnWebSite.setAdapter(adapterWebSiteCategory);
        adapterWebSiteCategory.notifyDataSetChanged();

        //Цвет
        mSpnColor = (Spinner) findViewById(R.id.spinner_color);
        mSpnColor.setOnItemSelectedListener(this);
        ArrayAdapter<?> adapterColor =
                ArrayAdapter.createFromResource(this, R.array.Colors,
                        android.R.layout.simple_spinner_dropdown_item);
        mSpnColor.setAdapter(adapterColor);
        adapterColor.notifyDataSetChanged();

        //Дата поиска
        mSpnSearchDate = (Spinner) findViewById(R.id.spinner_search_date);
        mSpnSearchDate.setOnItemSelectedListener(this);
        ArrayAdapter<?> adapterSearchDate =
                ArrayAdapter.createFromResource(this, R.array.SearchDates,
                        android.R.layout.simple_spinner_dropdown_item);
        mSpnSearchDate.setAdapter(adapterSearchDate);
        adapterSearchDate.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent,
                               View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinner_category:
                String[] mCategories = getResources()
                        .getStringArray(R.array.category);
                if (position == 1) mSpnUnderCategory.setVisibility(View.VISIBLE);
                else mSpnUnderCategory.setVisibility(View.GONE);
                mSearchProductCategory = mCategories[position];
                if (mSearchProductCategory == mCategories[0]) mSearchProductCategory = null;
                //TODO запись в БД
                break;
            case R.id.spinner_under_category:
                String[] mUnderCategories = getResources()
                        .getStringArray(R.array.Electronics_Computers_or_Office);
                mSearchProductUnderCategory = mUnderCategories[position];
                if (mSearchProductUnderCategory == mUnderCategories[0]) mSearchProductCategory = null;
                //TODO запись в БД
                break;
            case R.id.spinner_search_site:
                String[] mWebSites = getResources().getStringArray(R.array.WebSites);
                mSearchProductWebSite = mWebSites[position];
                //TODO запись в БД
                break;
            case R.id.spinner_color:
                String[] colors = getResources().getStringArray(R.array.Colors);
                mSearchProductColor = colors[position];
                if (mSearchProductColor == colors[0]) mSearchProductColor = null;
                //TODO запись в БД
                break;
            case R.id.spinner_search_date:
                String[] dates = getResources().getStringArray(R.array.SearchDates);
                mSearchProductDateAdded = dates[position];
                if (mSearchProductDateAdded == dates[0]) mSearchProductDateAdded = null;
                //TODO запись в БД
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Boolean result = false;
        switch (v.getId()) {
            case R.id.edt_search_product_full_name:
                result = true;
                break;
            //TODO запись в БД
            case R.id.edt_search_product_low_price:
                result = true;
                break;
            //TODO запись в БД
            case R.id.edt_search_product_high_price:
                result = true;
                break;
            //TODO запись в БД
            }
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel_action:
//                Intent cancel = new Intent(SettingActivity.this, MainActivity.class);
//                startActivity(cancel);

                break;
            case R.id.btn_save_action:
                getProductInfo();
                //mSearchProductLowPrice = Integer.parseInt(mEdtLowPrice.getText().toString());
                //mSearchProductHighPrice = Integer.parseInt(mEdtHighPrice.getText().toString());
                //mSearchProductName = mEdtName.getText().toString();
                getProductInfo();

                validateData();

                LogApp.Log("~~~~~~~", "\n" +
                        "Name: " + mSearchProductName + "\n" +
                        "Category: " + mSearchProductCategory + "\n" +
                        "UnderCategory: " + mSearchProductUnderCategory + "\n" +
                        "LowPrice: " + mSearchProductLowPrice + "\n" +
                        "HighPrice: " + mSearchProductHighPrice + "\n" +
                        "WebSite: " + mSearchProductWebSite + "\n" +
                        "Color: " + mSearchProductColor + "\n" +
                        "DateAdded: " + mSearchProductDateAdded);

                //Todo запись в БД
//                Intent ok = new Intent(SettingActivity.this, MainActivity.class);
//                startActivity(ok);
                break;
        }
    }

    /**todo этот метод возвращает тру если обязательные поля заполнены, проверяет все поля на нал
       если тру идет запись в Бд и отображение в ресаклеп, фолсе требует заполнения обязательных полей **/
    private Boolean getProductInfo() {
        Boolean goToWork = false;

        //Запись имени + проверка
        if (TextUtils.isEmpty(mEdtName.getText().toString())) {
            Toast toast = Toast.makeText(this, "Required field", Toast.LENGTH_SHORT);
            toast.show();
            //TODO не работает! не проверяет длинну строки
        } else if (mEdtName.getText().toString().length()<3 & mEdtName.getText().length()>50){
            Toast toast = Toast.makeText(this,
                    "At least two and not more than fifty characters", Toast.LENGTH_SHORT);
            toast.show();
        } else mSearchProductName = mEdtName.getText().toString();

        //Запись категории


        //

        //if (TextUtils.isEmpty(mSearchProductLowPrice))
            return goToWork;
    }

    private boolean validateData(){
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
