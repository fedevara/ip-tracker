package model.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TimeZone {

    private String localTime;
    private String utcTime;
    private String timeZone;

}
