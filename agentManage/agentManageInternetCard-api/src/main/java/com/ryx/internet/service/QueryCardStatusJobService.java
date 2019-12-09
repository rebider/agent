package com.ryx.internet.service;

import com.ryx.internet.pojo.OInternetCard;

import java.util.List;

/**
 * Created by RYX on 2019/12/9.
 */
public interface QueryCardStatusJobService {

    List<OInternetCard> fetchDataUpdateCardStatus();

}
