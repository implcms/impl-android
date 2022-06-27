package cn.subat.impl_demo.model;

import java.util.ArrayList;

import cn.subat.impl_android.model.IMViewModel;

public class TopSearch {
    public Words top_search;
    public static class Words{
        public ArrayList<Word> words;
    }
    public static class Word extends IMViewModel {
        public String query;
    }
}
