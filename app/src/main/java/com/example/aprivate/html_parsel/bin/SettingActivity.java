package com.example.aprivate.html_parsel.bin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aprivate.html_parsel.R;
import com.example.aprivate.html_parsel.log.LogApp;
import com.example.aprivate.html_parsel.services.SearchService;

public class SettingActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    protected Button mBtnCancel;
    protected Button mBtnOk;
    protected EditText mEdtName;
    protected EditText mEdtLowPrice;
    protected EditText mEdtHighPrice;
    protected Spinner mSpnCategory;
    protected Spinner mUnderCategory;
    protected Spinner mSpnWebSite;
    protected Spinner mSpnColor;
    protected Spinner mSpnSearchDate;

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
        mUnderCategory = (Spinner) findViewById(R.id.spinner_under_category);
        ArrayAdapter<?> adapterUnderCategory =
                ArrayAdapter.createFromResource(this, R.array.Electronics_Computers_or_Office,
                        android.R.layout.simple_spinner_dropdown_item);
        mUnderCategory.setAdapter(adapterUnderCategory);
        mUnderCategory.setVisibility(View.GONE);

        //сайты
        mSpnWebSite = (Spinner) findViewById(R.id.spinner_search_site);
        ArrayAdapter<?> adapterWebSiteCategory =
                ArrayAdapter.createFromResource(this, R.array.WebSites,
                        android.R.layout.simple_spinner_dropdown_item);
        mSpnWebSite.setAdapter(adapterWebSiteCategory);

        mSpnColor = (Spinner) findViewById(R.id.spinner_color);
        ArrayAdapter<?> adapterColor =
                ArrayAdapter.createFromResource(this, R.array.Colors,
                        android.R.layout.simple_spinner_dropdown_item);
        mSpnColor.setAdapter(adapterColor);

        mSpnSearchDate = (Spinner) findViewById(R.id.spinner_search_date);
        ArrayAdapter<?> adapterSearchDate =
                ArrayAdapter.createFromResource(this, R.array.SearchDates,
                        android.R.layout.simple_spinner_dropdown_item);
        mSpnSearchDate.setAdapter(adapterSearchDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent,
                               View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinner_category:
                String[] choose = getResources().getStringArray(R.array.category);
                if (position == 1){
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Ваш выбор: " + choose[position], Toast.LENGTH_SHORT);
                    toast1.show();
                    mUnderCategory.setVisibility(View.VISIBLE);
                } else mUnderCategory.setVisibility(View.GONE);

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
                Intent cancel = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(cancel);
                break;
            case R.id.btn_save_action:

                //Todo сохрани данные
                Intent ok = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(ok);
                break;
        }
    }
}
