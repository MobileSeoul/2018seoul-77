package com.example.skuniv.fleamarket2.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Category {
    static final int CLOTH = 1;
    static final int ETC = 2;
    static final int FANCY = 3;
    static final int BOOK = 4;
    static final int ACC = 5;
    static final int DIGITAL = 6;

    // middle category of cloth
    static final int SHOES = 11;
    static final int TOP = 12;
    static final int BOTTOM = 13;
    static final int CAP = 14;
    static final int GLOVES = 15;

    // middle category of digital
    static final int PC = 31;
    static final int MOBILE = 32;
    static final int APPLIANCES = 33;
    static final int CAR = 34;
    static final int GAME = 35;
    static final int OUDIO = 36;


    // middle category of acc
    static final int RING = 61;
    static final int EARRING = 63;
    static final int NECKLACE = 63;
    static final int BRACELET = 64;
    static final int CLOCK = 65;
    static final int WALLET = 66;
    static final int BAG = 67;

    // middle category of fancy
    static final int NOTE = 81;
    static final int PEN = 82;
    static final int ART = 83;
    static final int TOY = 84;

    // middle category of book
    static final int NOVEL = 101;
    static final int ESSSAY = 102;
    static final int COMIC = 103;
    static final int AUTOBIOGRPHY = 104;
    static final int MAGAZINE = 105;
    static final int POEM = 106;

    // middle category of etc
    static final int PERFUME = 121;
    static final int CANDLE = 122;
    static final int COSMETICS = 123;
    static final int NAIL = 124;
    static final int HAIR = 125;
    static final int INSTRUMENT = 126;

    public static final HashMap<String, Integer> categoryMap = new HashMap<String, Integer>() {
        {
            put("CLOTH", 1);
            put("ETC", 2);
            put("FANCY", 3);
            put("BOOK", 4);
            put("DIGITAL", 6);
            put("ACC", 5);
            put("옷", 1);
            put("기타", 2);
            put("잡화", 3);
            put("도서", 4);
            put("디지털", 6);
            put("악세서리", 5);
            put("신발", SHOES);
            put("상의", TOP);
            put("하의", BOTTOM);
            put("모자", CAP);
            put("장갑", GLOVES);
            put("PC", PC);
            put("모바일", MOBILE);
            put("가전", APPLIANCES);
            put("차량용품", CAR);
            put("게임", GAME);
            put("오디오", OUDIO);
            put("반지", RING);
            put("귀걸이", EARRING);
            put("목걸이", NECKLACE);
            put("팔찌", BRACELET);
            put("시계", CLOCK);
            put("지갑", WALLET);
            put("가방", BAG);
            put("공책", NOTE);
            put("필기구", PEN);
            put("미술용품", ART);
            put("장난감", TOY);
            put("소설", NOVEL);
            put("수필", ESSSAY);
            put("만화", COMIC);
            put("자서전", AUTOBIOGRPHY);
            put("잡지", MAGAZINE);
            put("시집", POEM);
            put("향수", PERFUME);
            put("향초", CANDLE);
            put("화장품", COSMETICS);
            put("네일", NAIL);
            put("헤어", HAIR);
            put("악기", INSTRUMENT);
        }
    };

    public int getCategoryNum(String categoryStr) {
        return categoryMap.get(categoryStr);
    }

    public String getCategoryStr(int categoryNum) {
        Set set = categoryMap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            int value = (int)entry.getValue();
            if(value == categoryNum)
                return (String) entry.getKey();
        }
        return "";
    }
}
