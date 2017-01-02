package eu.daxiongmao.prv.astrology.model.chinese;

/**
 * In Chinese astrology the sign is always associate with a particular element.
 *
 * @author Guillaume Diaz
 * @author Wikipedia (https://en.wikipedia.org/wiki/Chinese_astrology) - This is were the data comes from
 * @version 1.0 - 2016/12
 */
public enum ChineseElement {

    METAL("金"), WATER("水"), WOOD("木"), FIRE("火"), EARTH("土");

    private String chineseSign;

    ChineseElement(final String chineseSign) {
        this.chineseSign = chineseSign;
    }

    public String getChineseSign() {
        return chineseSign;
    }
}
