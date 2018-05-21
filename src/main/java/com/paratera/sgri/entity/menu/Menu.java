package com.paratera.sgri.entity.menu;

/**
 * 一级菜单
 * 
 * @author Administrator
 *
 */
public class Menu {
    private Button[] button;

    private Matchrule matchrule;

    public Button[] getButton() {
        return button;
    }

    public void setButton(Button[] button) {
        this.button = button;
    }

    public Matchrule getMatchrule() {
        return matchrule;
    }

    public void setMatchrule(Matchrule matchrule) {
        this.matchrule = matchrule;
    }


}
