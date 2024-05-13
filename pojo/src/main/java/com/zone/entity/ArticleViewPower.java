package com.zone.entity;


import lombok.Getter;
@Getter
public enum ArticleViewPower {
    // 仅自己可见
    ONLY_ME(0),
    // 仅好友可见
    ONLY_FRIEND(1),
    // 所有人可见
    ALL(2);

    private final int value;

    ArticleViewPower(int value) {
        this.value = value;
    }

    public static ArticleViewPower getEnum(int value) {
        for (ArticleViewPower articleView : ArticleViewPower.values()) {
            if (articleView.getValue() == value) {
                return articleView;
            }
        }
        return null;
    }
}
