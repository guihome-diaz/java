package eu.daxiongmao.prv.server.model.search;

import org.springframework.data.domain.Sort.Direction;

public class SortDTO {

    private String column;
    private Direction direction;

    public String getColumn() {
        return column;
    }
    public void setColumn(final String column) {
        this.column = column;
    }
    public Direction getDirection() {
        return direction;
    }
    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

}
