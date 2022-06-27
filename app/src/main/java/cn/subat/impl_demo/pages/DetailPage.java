package cn.subat.impl_demo.pages;

import cn.subat.impl_android.fragment.IMBasePage;
import cn.subat.impl_android.utils.IMPLayout;
import cn.subat.impl_android.view.IMTextView;
import cn.subat.impl_demo.model.TopSearch;
import cn.subat.impl_demo.utils.IDColor;

public class DetailPage extends IMBasePage {

    IMTextView contentView;

    @Override
    public void setupView() {
        super.setupView();

        view.setBackgroundColor(IDColor.background);
        TopSearch.Word word = getParam(TopSearch.Word.class,"word");

        contentView = new IMTextView(getContext(),10, IDColor.text);
        contentView.setText(word.query);

        view.addViews(contentView);

        IMPLayout.init(contentView).center(view);
    }
}
