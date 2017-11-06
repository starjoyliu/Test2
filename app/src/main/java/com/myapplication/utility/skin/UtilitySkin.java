package com.myapplication.utility.skin;

import com.myapplication.utility.skin.key.SkinKey;
import com.myapplication.utility.skin.key.SkinMode;
import com.myapplication.utility.skin.object.CommonSkin;
import com.myapplication.utility.skin.object.NormalSkin;

/**
 * skin切換工具
 * Created by star on 2017/11/6.
 */
public class UtilitySkin {
    private SkinMode skinMode = null;

    private volatile static UtilitySkin u;

    public UtilitySkin (){}

    public static UtilitySkin getNewInstance() {
        if(u == null) {
            synchronized (UtilitySkin.class) {
                if(u == null) {
                    u = new UtilitySkin();
                }
            }
        }
        return u;
    }

    /**
     * init skin
     * @param skinMode {@link SkinMode}
     */
    public void initSkin(String skinMode){
        if (skinMode.equals(SkinMode.NORMAL_SKIN.name())){
            this.skinMode = SkinMode.NORMAL_SKIN;
        }
    }

    /**
     * 取得色碼
     * @param skinKey
     * @return
     */
    public int getColor(SkinKey skinKey){
        if(skinMode == SkinMode.NORMAL_SKIN){
            if(skinKey == SkinKey.BLACK){
                return NormalSkin.BLACK;
            }else if(skinKey == SkinKey.WHITE){
                return NormalSkin.WHITE;
            }else{
                return NormalSkin.BLACK;
            }
        }else{
            return CommonSkin.BLACK;
        }
    }

    /**
     * 取得 drawable
     * @param skinKey
     * @return
     */
    public int getDrawable(SkinKey skinKey){
        if(skinMode == SkinMode.NORMAL_SKIN){
            if(skinKey.equals(SkinKey.ICON_MAIN)){
                return NormalSkin.ICON_MAIN;
            }else{
                return NormalSkin.ICON_MAIN;
            }
        }else{
            return NormalSkin.ICON_MAIN;
        }
    }
}
