package model;

import lombok.Getter;
import lombok.Setter;
import model.mapper.CountryConsult;

@Getter
@Setter
public class StatisticResponse {

    private CountryConsult longestDistance;
    private CountryConsult shortestDistance;
    private String averageDistance;

}
