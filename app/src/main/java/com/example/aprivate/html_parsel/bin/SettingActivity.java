package com.example.aprivate.html_parsel.bin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {
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
                if (position == 0){
                    mSearchProductCategory = null;
                    mSearchProductUnderCategory = null;
                    mSpnUnderCategory.setSelection(0);
                }
                if (position > 0) mSpnUnderCategory.setVisibility(View.VISIBLE);
                else mSpnUnderCategory.setVisibility(View.GONE);
                mSearchProductCategory = mCategories[position];
                if (mSearchProductCategory == mCategories[0]) mSearchProductCategory = null;
                //TODO запись в БД
                break;
            case R.id.spinner_under_category:
                String[] mUnderCategories = getResources()
                        .getStringArray(R.array.Electronics_Computers_or_Office);
                mSearchProductUnderCategory = mUnderCategories[position];
                if (mSearchProductUnderCategory == mUnderCategories[0]) mSearchProductUnderCategory = null;
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel_action:
//                Intent cancel = new Intent(SettingActivity.this, MainActivity.class);
//                startActivity(cancel);

                break;
            case R.id.btn_save_action:
                if (validateData()==true) {
                    LogApp.Log("~~~~~~~", "\n" +
                            "Name: " + mSearchProductName + "\n" +
                            "Category: " + mSearchProductCategory + "\n" +
                            "UnderCategory: " + mSearchProductUnderCategory + "\n" +
                            "LowPrice: " + mSearchProductLowPrice + "\n" +
                            "HighPrice: " + mSearchProductHighPrice + "\n" +
                            "WebSite: " + mSearchProductWebSite + "\n" +
                            "Color: " + mSearchProductColor + "\n" +
                            "DateAdded: " + mSearchProductDateAdded);

                    //Todo запись в БД и закрытие активити
//                Intent ok = new Intent(SettingActivity.this, MainActivity.class);
//                startActivity(ok);
                } else {
                    Toast validate = Toast.makeText(this,
                            "validate() java method error!", Toast.LENGTH_LONG);
                    validate.show();
                }
                break;
        }
    }

    private boolean validateData(){
        boolean validate;
        //проверка имени продукта
        if (TextUtils.isEmpty(mEdtName.getText().toString())){
            Toast empty = Toast.makeText(this,
                    "The product name field can not be empty", Toast.LENGTH_LONG);
            empty.show();
            validate = false;
        } else if (mEdtName.getText().length() < 2){
            Toast smallText = Toast.makeText(this,
                    "Minimum length of the product name 2 characters", Toast.LENGTH_LONG);
            smallText.show();
            validate = false;
        } else {
            mSearchProductName = mEdtName.getText().toString();
            validate = true;
        }

        //проерка значений минимальной-максимальной цены
        //сначала проверка заполнены ли поля
        if (!TextUtils.isEmpty(mEdtLowPrice.getText().toString())&&
                !TextUtils.isEmpty(mEdtHighPrice.getText().toString())){
            mSearchProductLowPrice = Integer.parseInt(mEdtLowPrice.getText().toString());
            mSearchProductHighPrice = Integer.parseInt(mEdtHighPrice.getText().toString());
            //если заполнены проверяем
            if (mSearchProductLowPrice>mSearchProductHighPrice){
                Toast highLow = Toast.makeText(this,
                        "The minimum price can not be more than the maximum", Toast.LENGTH_LONG);
                highLow.show();
                validate = false;
            }
        } else if (!TextUtils.isEmpty(mEdtLowPrice.getText().toString())){
            mSearchProductLowPrice = Integer.parseInt(mEdtLowPrice.getText().toString());
        } else if (!TextUtils.isEmpty(mEdtHighPrice.getText().toString())){
            mSearchProductHighPrice = Integer.parseInt(mEdtHighPrice.getText().toString());
        } else if (TextUtils.isEmpty(mEdtLowPrice.getText().toString())){
            mSearchProductLowPrice = 0;
        } else if (TextUtils.isEmpty(mEdtHighPrice.getText().toString())){
            mSearchProductHighPrice = 0;
        } else {
            Toast unknown = Toast.makeText(this,
                    "Unknown error!", Toast.LENGTH_LONG);
            unknown.show();
        }

        //контрольная поврка после редактирования полей
        if (TextUtils.isEmpty(mEdtLowPrice.getText().toString())){
            mSearchProductLowPrice = 0;
        }
        if (TextUtils.isEmpty(mEdtHighPrice.getText().toString())){
            mSearchProductHighPrice = 0;
        }

        return validate;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
