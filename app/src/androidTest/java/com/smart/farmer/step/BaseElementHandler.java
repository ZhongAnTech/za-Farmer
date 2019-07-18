package com.smart.farmer.step;

import android.view.accessibility.AccessibilityNodeInfo;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.ICheckCriteria;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.Until;

public abstract class BaseElementHandler extends BaseStep {


    protected String elementId;
    protected String elementDesc;
    protected String elementText;
    protected String elementClazz;
    protected String elementPackage;
    protected String elementTextDesc;
    protected int order = -1;
    protected int scrollFind;
    protected int scrollCount = 5;


    protected String elementIdContains;
    protected String elementDescContains;
    protected String elementTextContains;
    protected String elementClazzContains;
    protected String elementPackageContains;
    protected String elementTextDescContains;


    protected String elementIdPattern;
    protected String elementDescPattern;
    protected String elementTextPattern;
    protected String elementClazzPattern;
    protected String elementPackagePattern;
    protected String elementTextDescPattern;


    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getElementDesc() {
        return elementDesc;
    }

    public void setElementDesc(String elementDesc) {
        this.elementDesc = elementDesc;
    }

    public String getElementText() {
        return elementText;
    }

    public void setElementText(String elementText) {
        this.elementText = elementText;
    }

    public String getElementClazz() {
        return elementClazz;
    }

    public void setElementClazz(String elementClazz) {
        this.elementClazz = elementClazz;
    }

    public String getElementPackage() {
        return elementPackage;
    }

    public void setElementPackage(String elementPackage) {
        this.elementPackage = elementPackage;
    }

    public String getElementTextDesc() {
        return elementTextDesc;
    }

    public void setElementTextDesc(String elementTextDesc) {
        this.elementTextDesc = elementTextDesc;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getScrollFind() {
        return scrollFind;
    }

    public void setScrollFind(int scrollFind) {
        this.scrollFind = scrollFind;
    }

    public int getScrollCount() {
        return scrollCount;
    }

    public void setScrollCount(int scrollCount) {
        this.scrollCount = scrollCount;
    }

    public String getElementIdContains() {
        return elementIdContains;
    }

    public void setElementIdContains(String elementIdContains) {
        this.elementIdContains = elementIdContains;
    }

    public String getElementDescContains() {
        return elementDescContains;
    }

    public void setElementDescContains(String elementDescContains) {
        this.elementDescContains = elementDescContains;
    }

    public String getElementTextContains() {
        return elementTextContains;
    }

    public void setElementTextContains(String elementTextContains) {
        this.elementTextContains = elementTextContains;
    }

    public String getElementClassContains() {
        return elementClazzContains;
    }

    public void setElementClassContains(String elementClazzContains) {
        this.elementClazzContains = elementClazzContains;
    }

    public String getElementPackageContains() {
        return elementPackageContains;
    }

    public void setElementPackageContains(String elementPackageContains) {
        this.elementPackageContains = elementPackageContains;
    }

    public String getElementTextDescContains() {
        return elementTextDescContains;
    }

    public void setElementTextDescContains(String elementTextDescContains) {
        this.elementTextDescContains = elementTextDescContains;
    }

    public String getElementIdPattern() {
        return elementIdPattern;
    }

    public void setElementIdPattern(String elementIdPattern) {
        this.elementIdPattern = elementIdPattern;
    }

    public String getElementDescPattern() {
        return elementDescPattern;
    }

    public void setElementDescPattern(String elementDescPattern) {
        this.elementDescPattern = elementDescPattern;
    }

    public String getElementTextPattern() {
        return elementTextPattern;
    }

    public void setElementTextPattern(String elementTextPattern) {
        this.elementTextPattern = elementTextPattern;
    }

    public String getElementClassPattern() {
        return elementClazzPattern;
    }

    public void setElementClassPattern(String elementClazzPattern) {
        this.elementClazzPattern = elementClazzPattern;
    }

    public String getElementPackagePattern() {
        return elementPackagePattern;
    }

    public void setElementPackagePattern(String elementPackagePattern) {
        this.elementPackagePattern = elementPackagePattern;
    }

    public String getElementTextDescPattern() {
        return elementTextDescPattern;
    }

    public void setElementTextDescPattern(String elementTextDescPattern) {
        this.elementTextDescPattern = elementTextDescPattern;
    }

    public UiObject2 getElement(BySelector bySelector, int timeout) throws UiObjectNotFoundException {

        UiObject2 uiObject;
        if (this.order > 0) {
            List<UiObject2> objs = getUiDevice().wait(Until.findObjects(bySelector), timeout);
            if (objs == null) {
                uiObject = null;
            } else {
                uiObject = objs.get(this.order - 1);
            }
        } else {
            uiObject = getUiDevice().wait(Until.findObject(bySelector), timeout);
        }
        if (uiObject == null)
            throw new UiObjectNotFoundException("element not found");

        return uiObject;
    }


    public UiObject2 getElement(BySelector bySelector, int swipCount, String direction) throws UiObjectNotFoundException {
        UiObject2 uiObject = null;
        for (int i = 0; i < swipCount; i++) {
            uiObject = getUiDevice().wait(Until.findObject(bySelector), 1000);
            if (uiObject != null) {
                break;
            }
            Swipe upSwip = new Swipe();
            upSwip.setDirection(direction);
            upSwip.runSelf();
        }
        if (uiObject == null) {
            throw new UiObjectNotFoundException("element not found");
            // bySelector.
        } else {
            return uiObject;
        }
    }


    public UiObject2 getElement() throws UiObjectNotFoundException {
        BySelector selector = getElementSelector();
        if (this.scrollFind == 1) {
            return getElement(selector, this.scrollCount, "up");
        } else {
            return getElement(selector, 10000);
        }
    }


    public BySelector getElementSelector() {
        BySelector bySelector = By.base();

        if (this.elementId != null) {
            bySelector.res(this.elementId);
        }

        if (this.elementText != null) {
            bySelector.text(this.elementText);
        }

        if (this.elementDesc != null) {
            bySelector.desc(this.elementDesc);
        }

        if (this.elementClazz != null) {
            bySelector.clazz(this.elementClazz);
        }

        if (this.elementPackage != null) {
            bySelector.pkg(this.elementPackage);
        }

        if (this.elementTextDesc != null) {
            bySelector.setCustomCheckCriteria(new customCheckCriteria(this.elementTextDesc));
        }

        //contains

        if (this.elementIdContains != null) {
            bySelector.res(containsPattern(this.elementIdContains));
        }

        if (this.elementTextContains != null) {
            bySelector.textContains(this.elementTextContains);
        }

        if (this.elementDescContains != null) {
            bySelector.descContains(this.elementDescContains);
        }

        if (this.elementClazzContains != null) {
            bySelector.clazz(containsPattern(this.elementClazzContains));
        }

        if (this.elementPackageContains != null) {
            bySelector.pkg(containsPattern(this.elementPackageContains));
        }

        if (this.elementTextDescContains != null) {
            bySelector.setCustomCheckCriteria(
                    new customCheckCriteria(containsPattern(this.elementTextDescContains))
            );
        }

        //pattern

        if (this.elementIdPattern != null) {
            bySelector.res(Pattern.compile(this.elementIdPattern));
        }

        if (this.elementTextPattern != null) {
            bySelector.text(Pattern.compile(this.elementTextPattern));
        }

        if (this.elementDescPattern != null) {
            bySelector.desc(Pattern.compile(this.elementDescPattern));
        }

        if (this.elementClazzPattern != null) {
            bySelector.clazz(Pattern.compile(this.elementClazzPattern));
        }

        if (this.elementPackagePattern != null) {
            bySelector.pkg(Pattern.compile(this.elementPackagePattern));
        }

        if (this.elementTextDescPattern != null) {
            bySelector.setCustomCheckCriteria(
                    new customCheckCriteria(Pattern.compile(this.elementTextDescPattern))
            );
        }

        return bySelector;
    }

    private Pattern containsPattern(String value) {
        return Pattern.compile(String.format("^.*%s.*$", Pattern.quote(value)));
    }


    /**
     * 返回*
     *
     * @param value
     * @return
     */
    private HashMap<String, String> getSelectorObject(String value) {

        HashMap<String, String> hashMap = new HashMap<String, String>();

        try {
            JSONObject jsobj = new JSONObject(value);
            hashMap.put("mode", jsobj.getString("mode"));
            hashMap.put("value", jsobj.getString("value"));
        } catch (JSONException e) {
            hashMap.put("mode", "1");
            hashMap.put("value", value);
        }
        return hashMap;
    }

    class customCheckCriteria implements ICheckCriteria {

        String checkValue;
        Pattern checkPattern;

        customCheckCriteria(String checkValue) {
            this.checkValue = checkValue;
        }

        customCheckCriteria(Pattern checkPattern) {
            this.checkPattern = checkPattern;
        }

        @Override
        public boolean checkCriteria(AccessibilityNodeInfo node) {

            String text = "";
            String desc = "";
            if (node.getText() != null) {
                text = node.getText().toString();
            }
            if (node.getContentDescription() != null) {
                desc = node.getContentDescription().toString();
            }

            if (this.checkValue != null) {

                return text.equals(this.checkValue) || desc.equals(this.checkValue);

            } else if (this.checkPattern != null) {
                return this.checkPattern.matcher(text).matches() || this.checkPattern.matcher(desc).matches();

            }
            return false;
        }

    }

}




