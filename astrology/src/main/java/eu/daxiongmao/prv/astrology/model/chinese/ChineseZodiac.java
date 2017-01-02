package eu.daxiongmao.prv.astrology.model.chinese;

/**
 * List of Chinese signs.
 * @author Guillaume Diaz
 * @author Wikipedia (https://en.wikipedia.org/wiki/Chinese_astrology) - This is were the data comes from
 * @version 1.0 - 2016/12
 */
public enum ChineseZodiac {

    RAT("鼠", "shǔ", ChineseType.YANG),
    OX("牛", "niú", ChineseType.YIN),
    TIGER("虎", "hǔ", ChineseType.YIN),
    RABBIT("兔", "tù", ChineseType.YANG),
    DRAGON("龍 (龙)", "lóng", ChineseType.YANG),
    SNAKE("蛇", "shé", ChineseType.YIN),
    HORSE("馬（马", "mǎ", ChineseType.YIN),
    GOAT("羊", "yáng", ChineseType.YANG),
    MONKEY("猴", "hóu", ChineseType.YANG),
    ROOSTER("鷄 (鸡)", "jī", ChineseType.YIN),
    DOG("狗", "gǒu", ChineseType.YIN),
    PIG("猪", "zhū", ChineseType.YANG);

    private String chineseSign;

    private String chineseName;
    
    private ChineseType type;

    private ChineseZodiac(final String chineseSign, final String chineseName, ChineseType type) {
        this.chineseSign = chineseSign;
        this.chineseName = chineseName;
        this.type = type;
    }
        
    public String getChineseName() {
        return chineseName;
    }
    
    public String getChineseSign() {
        return chineseSign;
    }
    
    /**
     * @return type of the current year: YIN (feminine) ; YANG (masculine). <br>
     *         In Chinese culture all YIN has a YANG equivalent such as the moon (YIN) and the sun (YANG).
     */
    public ChineseType getType() {
        return type;
    }
}
