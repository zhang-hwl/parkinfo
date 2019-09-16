package com.parkinfo.enums;

public enum SettingType {

    INIT_PASSWORD{
        @Override
        public String getDefaultValue() {
            return "123456";
        }
    },
    INIT_SALT{
        @Override
        public String getDefaultValue() {
            return "Ed8dSy";
        }
    };


    public abstract String getDefaultValue();

}
