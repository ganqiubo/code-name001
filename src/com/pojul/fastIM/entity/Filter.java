package com.pojul.fastIM.entity;

import com.google.gson.Gson;

import java.util.List;

public class Filter extends BaseEntity{

    private boolean isFilterEnabled;
    private boolean isWhiteListEnabled;
    private List<String> whiteListNames;
    private boolean isBlackListEnabled;
    private List<String> blackListNames;
    private boolean isAgeEnabled;
    private AgeRange ageRange;
    private boolean isSexEnabled;
    private int sex;//0: 女; 1: 男
    private int isCertificatEnabled;//0: false; 1: true
    private int credit;
    
    public boolean isFilterEnabled() {
        return isFilterEnabled;
    }

    public void setFilterEnabled(boolean filterEnabled) {
        isFilterEnabled = filterEnabled;
    }

    public boolean isWhiteListEnabled() {
        return isWhiteListEnabled;
    }

    public void setWhiteListEnabled(boolean whiteListEnabled) {
        isWhiteListEnabled = whiteListEnabled;
    }

    public List<String> getWhiteListNames() {
        return whiteListNames;
    }

    public void setWhiteListNames(List<String> whiteListNames) {
        this.whiteListNames = whiteListNames;
    }

    public boolean isBlackListEnabled() {
        return isBlackListEnabled;
    }

    public void setBlackListEnabled(boolean blackListEnabled) {
        isBlackListEnabled = blackListEnabled;
    }

    public List<String> getBlackListNames() {
        return blackListNames;
    }

    public void setBlackListNames(List<String> blackListNames) {
        this.blackListNames = blackListNames;
    }

    public boolean isAgeEnabled() {
        return isAgeEnabled;
    }

    public void setAgeEnabled(boolean ageEnabled) {
        isAgeEnabled = ageEnabled;
    }

    public AgeRange getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(AgeRange ageRange) {
        this.ageRange = ageRange;
    }

    public boolean isSexEnabled() {
        return isSexEnabled;
    }

    public void setSexEnabled(boolean sexEnabled) {
        isSexEnabled = sexEnabled;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int isCertificatEnabled() {
        return isCertificatEnabled;
    }

    public void setCertificatEnabled(int certificatEnabled) {
        isCertificatEnabled = certificatEnabled;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
