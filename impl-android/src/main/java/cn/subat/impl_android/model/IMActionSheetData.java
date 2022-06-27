package cn.subat.impl_android.model;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

import cn.subat.impl_android.utils.IMConstant;
import cn.subat.impl_android.utils.IMUtils;

public class IMActionSheetData extends IMViewModel{
    public int id;
    public String icon;
    public int iconDrawable;
    public int backgroundColor = -1;
    public int textColor;
    public int marginTop;
    public int marginBottom;
    public int marginHorizontal;
    public int viewHeight;
    public int parent_id;
    public int padding;
    public int direction;
    public int alight;
    public int lineHeight;
    public int radius = -1;
    public boolean selected;
    public boolean lastOne;
    public String remark;

    public IMActionSheetData(){
        viewType = IMConstant.ViewTypeActionSheet;
    }

    public static ArrayList createDataList(String... words){
        ArrayList catList = new ArrayList();
        for (int i = 0; i < words.length; i++) {
            IMActionSheetData category = new IMActionSheetData();
            category.name = words[i];
            catList.add(category);
        }
        return catList;
    }

    public static IMActionSheetData create(String word){
        IMActionSheetData category = new IMActionSheetData();
        category.name = word;
        return category;
    }

    public static IMActionSheetData create(int word){
        IMActionSheetData category = create(IMUtils.getString(word));
        category.id = word;
        return category;
    }

    public static IMActionSheetData makeSpace(int height){
        IMActionSheetData category = new IMActionSheetData();
        category.viewType = IMConstant.ViewTypeSpaceItem;
        category.viewColumn = 1;
        category.viewHeight = height;
        return category;
    }

    public IMActionSheetData setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public IMActionSheetData setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public IMActionSheetData setMarginTop(int marginTop) {
        this.marginTop = marginTop;
        return this;
    }

    public IMActionSheetData setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
        return this;
    }

    public IMActionSheetData setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    public IMActionSheetData setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public IMActionSheetData setMarginHorizontal(int marginHorizontal) {
        this.marginHorizontal = marginHorizontal;
        return this;
    }

    public IMActionSheetData setLastOne(boolean lastOne) {
        this.lastOne = lastOne;
        return this;
    }

    public IMActionSheetData setViewType(int type){
        viewType = type;
        return this;
    }

    public IMActionSheetData setViewColumn(int column){
        viewColumn = column;
        return this;
    }

    public IMActionSheetData setDirection(int direction) {
        this.direction = direction;
        return this;
    }

    public IMActionSheetData setAlight(int alight) {
        this.alight = alight;
        return this;
    }

    public IMActionSheetData setId(int id){
        this.id = id;
        return this;
    }

    public IMActionSheetData setParent_id(int parent_id) {
        this.parent_id = parent_id;
        return this;
    }

    public IMActionSheetData setSelected(boolean selected) {
        this.selected = selected;
        return this;
    }

    public IMActionSheetData setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }
}
