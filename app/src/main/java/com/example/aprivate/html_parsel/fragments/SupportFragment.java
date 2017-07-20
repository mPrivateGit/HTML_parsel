package com.example.aprivate.html_parsel.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aprivate.html_parsel.R;

public class SupportFragment extends Fragment{
    private TextView mTxtTest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_support, container, false);
        mTxtTest = (TextView)v.findViewById(R.id.txt_support_test);
        mTxtTest.setText("Саппорт он же support, так в интернете называется " +
                "служба технической поддержки сервиса, интернет-сайта или сервера. " +
                "В переводе с английского слово support  означает буквально – поддержка. " +
                "Как правило, в службу технической поддержки обращаются для разъяснения " +
                "какого – либо вопроса, касающегося работы системы или в ситуации " +
                "технической неисправности соответственного интернет ресурса. " +
                "Для обращения в саппорт чаще всего необходимо заполнить пользовательскую " +
                "форму с указанием личных данных, темы сообщения и описание проблемы, " +
                "причем, чем полнее будет описана проблема, тем быстрее она решится. " +
                "Ссылка на меню технической поддержки обычно располагается внизу " +
                "страницы и может выглядеть по разному, но объединяет ее именно " +
                "смысловое название: сапорт, суппорт, техническая поддержка, support, " +
                "служа поддержки, и т.д. Каждая крупная компания имеет собственную службу " +
                "технической поддержки, например, microsoft.com, wikipedia.org, apple.com, " +
                "google.com и др. ");
        return v;
    }
}
