package com.parkinfo.enums;

public enum  DefaultEnum {
    CEO_PARK{
        @Override
        public String getDefaultValue() {
            return "2b441280acf24d87b5a1272c8f1162ea";
        }
    };

    public abstract String getDefaultValue();
}
