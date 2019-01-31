package com.ryx.credit.common.enumc;


public enum BinaryConversionEnum {
    Conversion_1(1, "1"),
    Conversion_2(2, "2"),
    Conversion_3(3, "3"),
    Conversion_4(4, "4"),
    Conversion_5(5, "5"),
    Conversion_6(6, "6"),
    Conversion_7(7, "7"),
    Conversion_8(8, "8"),
    Conversion_9(9, "9"),
    Conversion_10(10, "A"),
    Conversion_11(11, "B"),
    Conversion_12(12, "C"),
    Conversion_13(13, "D"),
    Conversion_14(14, "E"),
    Conversion_15(15, "F"),
    Conversion_16(16, "G"),
    Conversion_17(17, "H"),
    Conversion_18(18, "J"),
    Conversion_19(19, "K"),
    Conversion_20(20, "L"),
    Conversion_21(21, "M"),
    Conversion_22(22, "N"),
    Conversion_23(23, "P"),
    Conversion_24(24, "Q"),
    Conversion_25(25, "R"),
    Conversion_26(26, "S"),
    Conversion_27(27, "T"),
    Conversion_28(28, "U"),
    Conversion_29(29, "V"),
    Conversion_30(30, "W"),
    Conversion_31(31, "X"),
    Conversion_32(32, "Y");

    private final int num;
    private final String numStr;


    BinaryConversionEnum(int num, String numStr) {
        this.num = num;
        this.numStr = numStr;
    }


    public static String getNumStrByNum(int num) {
        for (BinaryConversionEnum conversionEnum : BinaryConversionEnum.values()) {
            if (num == conversionEnum.getNum()) {
                return conversionEnum.getNumStr();
            }
        }
        return null;
    }

    public static int getNumByNumStr(String numStr) {
        for (BinaryConversionEnum conversionEnum : BinaryConversionEnum.values()) {
            if (numStr.trim().equals(conversionEnum.getNumStr())) {
                return conversionEnum.getNum();
            }
        }
        return 0;
    }

    public int getNum() {
        return num;
    }

    public String getNumStr() {
        return numStr;
    }

    @Override
    public String toString() {
        return "BinaryConversionEnum{" +
                "num=" + num +
                ", numStr='" + numStr + '\'' +
                '}';
    }
}