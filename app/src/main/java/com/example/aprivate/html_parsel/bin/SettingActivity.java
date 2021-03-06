package com.example.aprivate.html_parsel.bin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.SearchProduct;
import com.example.aprivate.html_parsel.data.WorkerDataBaseSearchProduct;
import com.example.aprivate.html_parsel.log.LogApp;


public class SettingActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String PRODUCT_USER_ID = "selected_product_id";
    private static final String KEY_INSTANCE_NAME = "name";
    private static final String KEY_INSTANCE = "index";
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

    private String mSearchProductId;
    private String mSearchProductName;
    private String mSearchProductCategory;
    private String mSearchProductUnderCategory;
    private String mSearchProductWebSite;
    private String mSearchProductColor;
    private String mDateUserCreateProduct;
    private String mSearchProductDateAdded;
    private int mNeedSearch = 0;
    private int mSearchProductLowPrice;
    private int mSearchProductHighPrice;
    private WorkerDataBaseSearchProduct workerDB;

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
        int maxLengthProductName = 33;
        int maxLengthLowPrice = 8;
        int maxLengthHighPrice = 8;
        InputFilter [] inputFiltersName = new InputFilter[1];
        InputFilter [] inputFiltersLowPrice = new InputFilter[1];
        InputFilter [] inputFiltersHighPrice = new InputFilter[1];
        inputFiltersName[0] = new InputFilter.LengthFilter(maxLengthProductName);
        inputFiltersLowPrice[0] = new InputFilter.LengthFilter(maxLengthLowPrice);
        inputFiltersHighPrice[0] = new InputFilter.LengthFilter(maxLengthHighPrice);

        mEdtName = (EditText) findViewById(R.id.edt_search_product_full_name);
        mEdtName.setFilters(inputFiltersName);
        mEdtLowPrice = (EditText) findViewById(R.id.edt_search_product_low_price);
        mEdtLowPrice.setFilters(inputFiltersLowPrice);
        mEdtHighPrice = (EditText) findViewById(R.id.edt_search_product_high_price);
        mEdtHighPrice.setFilters(inputFiltersHighPrice);
    }

    private void viewButtons(){
        mBtnCancel = (Button) findViewById(R.id.btn_dialog_cancel_action);
        mBtnCancel.setOnClickListener(this);
        mBtnOk = (Button) findViewById(R.id.btn_dialog_save_action);
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
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent.hasExtra(PRODUCT_USER_ID)){
            String strId = intent.getExtras().getString(PRODUCT_USER_ID);
            LogApp.Log("-----------", "\n");
            LogApp.Log("onStart in SettingActivity: ", "id - выбраного объекта = "
                    + strId + "\n");
            workerDB = new WorkerDataBaseSearchProduct(this, strId);
            SearchProduct target = workerDB.readObjectFromDb();

            //ID
            mSearchProductId = target.getProductId();
            //Date create
            mDateUserCreateProduct = target.getDateUserAdded();
            //Name product
            mEdtName.setText(target.getProductName());
            //low price
            if (target.getLowPrice() != 0) {
                mEdtLowPrice.setText(String.valueOf(target.getLowPrice()));
            } else mEdtLowPrice.setHint(R.string.str_null_price);
            //High price
            if (target.getHighPrice() != 0){
                mEdtHighPrice.setText(String.valueOf(target.getHighPrice()));
            } else mEdtHighPrice.setHint(R.string.str_null_price);
            // Need Search
            mNeedSearch = target.getNeedSearch();

            LogApp.Log("onStart() in STA: ", "\n" + target.toString());
        }
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
                if (mSearchProductCategory.equals(mCategories[0]))
                    mSearchProductCategory = null;
                break;
            case R.id.spinner_under_category:
                String[] mUnderCategories = getResources()
                        .getStringArray(R.array.Electronics_Computers_or_Office);
                mSearchProductUnderCategory = mUnderCategories[position];
                if (mSearchProductUnderCategory.equals(mUnderCategories[0]))
                    mSearchProductUnderCategory = null;
                break;
            case R.id.spinner_search_site:
                String[] mWebSites = getResources().getStringArray(R.array.WebSites);
                mSearchProductWebSite = mWebSites[position];
                break;
            case R.id.spinner_color:
                String[] colors = getResources().getStringArray(R.array.Colors);
                mSearchProductColor = colors[position];
                if (mSearchProductColor.equals(colors[0]))
                    mSearchProductColor = null;
                break;
            case R.id.spinner_search_date:
                String[] dates = getResources().getStringArray(R.array.SearchDates);
                mSearchProductDateAdded = dates[position];
                if (mSearchProductDateAdded.equals(dates[0]))
                    mSearchProductDateAdded = null;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // метод идет в связке
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_dialog_cancel_action:
                Intent cancel = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(cancel);
                break;
            case R.id.btn_dialog_save_action:
                if (validateData()) {
                    workerDB = new WorkerDataBaseSearchProduct(this,
                            mSearchProductId, mSearchProductName,
                            mSearchProductLowPrice, mSearchProductHighPrice,
                            mSearchProductCategory, mSearchProductUnderCategory,
                            mSearchProductWebSite, mSearchProductDateAdded,
                            mDateUserCreateProduct, mNeedSearch);
                    workerDB.writeObjectInDb();
                    Intent ok = new Intent(SettingActivity.this, MainActivity.class);
                    startActivity(ok);
                } else {
                    LogApp.Log("validate() java method error!",
                            "ПОЛЬЗОВАТЕЛЬ ПЫТАЕТСЯ ВВЕСТИ НЕКОРРЕКТНЫЕ ДАННЫЕ");
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

        //проверка значений минимальной-максимальной цены
        //сначала проверка заполнены ли поля
        if (!TextUtils.isEmpty(mEdtLowPrice.getText().toString())&&
                !TextUtils.isEmpty(mEdtHighPrice.getText().toString())){
            mSearchProductLowPrice = Integer.parseInt(mEdtLowPrice.getText().toString());
            mSearchProductHighPrice = Integer.parseInt(mEdtHighPrice.getText().toString());
            LogApp.Log("SettingActivity: ", "validateData()" + "\n" +
                        "mSearchProductLowPrice = " + mSearchProductLowPrice + "\n" +
                        "mSearchProductHighPrice = " + mSearchProductHighPrice);
            //если заполнены проверяем
            if (mSearchProductLowPrice > mSearchProductHighPrice){
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
