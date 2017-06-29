package cn.jrhlive.meishe.bean;

import android.text.TextUtils;

import com.meicam.sdk.NvsAssetPackageManager;

/**
 * desc: 视频特效
 *
 * Created by jiarh on 17/5/11 14:35.
 */

public class SpecialEffect {

    /**
     主题：.theme
     字幕：.captionstyle
     滤镜：.videofx
     贴纸：.animatedsticker
     转场：.videotransition
     */
    public static final String TYPE_THEME = "theme";
    public static final String TYPE_ANIMATEDSTICKER = "animatedsticker";
    public static final String TYPE_CAPTIONSTYLE = "captionstyle";
    public static final String TYPE_VIDEOFX = "videofx";
    public static final String TYPE_VIDEOTRANSITION = "videotransition";

    private String name;
    private String path;
    /**
     * 授权文件路径
     */
    private String authFilePath;
    /**
     * 特效类型
     */
    private int pacageType;
    /**
     * 特效文件id
     */
    private StringBuilder assetPackageId;
    /**
     * 滤镜
     */
    private FilterEffect filterEffect;
    /**
     * 是否添加字幕
     */
    private boolean addCaption;

    /**
     * 字幕特效
     */
    private CaptionEffect captionEffect;


    public SpecialEffect() {
    }

    public SpecialEffect(String name) {
        this.name = name;
        assetPackageId = new StringBuilder();
        if (!TextUtils.isEmpty(name)){
            setPath("assets:/"+name);
            if (name.endsWith(TYPE_THEME)){
                setPacageType(NvsAssetPackageManager.ASSET_PACKAGE_TYPE_THEME);
            }else if (name.endsWith(TYPE_ANIMATEDSTICKER)){
                setPacageType(NvsAssetPackageManager.ASSET_PACKAGE_TYPE_ANIMATEDSTICKER);
            }else if (name.endsWith(TYPE_CAPTIONSTYLE)){
                setPacageType(NvsAssetPackageManager.ASSET_PACKAGE_TYPE_CAPTIONSTYLE);
            }else if (name.endsWith(TYPE_VIDEOFX)){
                setPacageType(NvsAssetPackageManager.ASSET_PACKAGE_TYPE_VIDEOFX);
            }else if (name.endsWith(TYPE_VIDEOTRANSITION)){
                setPacageType(NvsAssetPackageManager.ASSET_PACKAGE_TYPE_VIDEOTRANSITION);
            }
        }

    }

    public StringBuilder getAssetPackageId() {
        return assetPackageId;
    }

    public void setAssetPackageId(StringBuilder assetPackageId) {
        this.assetPackageId = assetPackageId;
    }

    public FilterEffect getFilterEffect() {
        return filterEffect;
    }

    public void setFilterEffect(FilterEffect filterEffect) {
        this.filterEffect = filterEffect;
    }

    public int getPacageType() {
        return pacageType;
    }

    public void setPacageType(int pacageType) {
        this.pacageType = pacageType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAuthFilePath() {
        return authFilePath;
    }

    public void setAuthFilePath(String authFilePath) {
        this.authFilePath = authFilePath;
    }

    public boolean isAddCaption() {
        return addCaption;
    }

    public void setAddCaption(boolean addCaption) {
        this.addCaption = addCaption;
    }

    public CaptionEffect getCaptionEffect() {
        return captionEffect;
    }

    public void setCaptionEffect(CaptionEffect captionEffect) {
        this.captionEffect = captionEffect;
        setAddCaption(captionEffect==null?false:true);
    }
}
