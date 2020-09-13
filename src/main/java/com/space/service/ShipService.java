package com.space.service;

import com.space.model.ShipInfo;
import com.space.model.Ship;
import com.space.model.ShipSearch;

import java.util.List;

public interface ShipService {

    List<Ship> readAll();

    List<Ship> readWithParams(ShipSearch search);

    boolean exists(Long id);

    Ship read(Long id);

    int shipCounts(ShipSearch search);

    Ship create(ShipInfo shipInfo);

    Ship update(long id, ShipInfo shipInfo);

    void deleteById(Long id);
}
