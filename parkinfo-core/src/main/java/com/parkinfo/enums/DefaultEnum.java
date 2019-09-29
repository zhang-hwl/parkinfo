package com.parkinfo.enums;

public enum  DefaultEnum {
    CEO_PARK{
        @Override
        public String getDefaultValue() {
            return "总裁园区";
        }
    };

    public abstract String getDefaultValue();
}
