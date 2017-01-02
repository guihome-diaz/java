package eu.daxiongmao.prv.astrology.model.chinese;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representation of a Chinese year.
 *
 * @author Guillaume Diaz
 * @version 1.0 - 2016/12
 */
public class ChineseYear implements Serializable, Comparable<ChineseYear> {

    private static final long serialVersionUID = -201612301700L;

    private ChineseZodiac zodiac;

    private ChineseElement element;

    private LocalDate startDate;

    private LocalDate endDate;

    /**
     * New Chinese year.
     *
     * @param zodiac
     *            zodiac sign
     * @param element
     *            related element (metal, water, wood, fire, earth)
     * @param startDate
     *            start date of the current year [inclusive from 00:00:00]
     * @param endDate
     *            end date of the current year [inclusive till 23:59:59]
     */
    public ChineseYear(final ChineseZodiac zodiac, final ChineseElement element, final LocalDate startDate, final LocalDate endDate) {
        this.zodiac = zodiac;
        this.element = element;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ChineseZodiac getZodiac() {
        return zodiac;
    }

    public ChineseElement getElement() {
        return element;
    }

    /**
     * @return start date of the current year [inclusive from 00:00:00]
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @return end date of the current year [inclusive till 23:59:59]
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * To compare the current year with another. <br>
     * Order is based on:
     * <ul>
     * <li>Start date</li>
     * </ul>
     *
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
     */
    @Override
    public int compareTo(final ChineseYear other) {
        if (other == null) {
            return 1;
        }
        if (startDate == null && other.getStartDate() != null) {
            return -1;
        }
        if (startDate != null && other.getStartDate() == null) {
            return 1;
        }
        return startDate.compareTo(other.getStartDate());
    }

    @Override
    public String toString() {
        final StringBuilder log = new StringBuilder();
        log.append(String.format("%s (%s, %s) [%s]%n", zodiac.name(), zodiac.getChineseSign(), zodiac.getChineseName(), zodiac.getType()));
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLLL yyyy (dd/MM/yyyy)");
        log.append(String.format("Start : %s%n", startDate.format(formatter)));
        log.append("End : ").append(endDate.format(formatter));
        return log.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((element == null) ? 0 : element.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((zodiac == null) ? 0 : zodiac.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChineseYear other = (ChineseYear) obj;
        if (element != other.element) {
            return false;
        }
        if (endDate == null) {
            if (other.endDate != null) {
                return false;
            }
        } else if (!endDate.equals(other.endDate)) {
            return false;
        }
        if (startDate == null) {
            if (other.startDate != null) {
                return false;
            }
        } else if (!startDate.equals(other.startDate)) {
            return false;
        }
        if (zodiac != other.zodiac) {
            return false;
        }
        return true;
    }

}
