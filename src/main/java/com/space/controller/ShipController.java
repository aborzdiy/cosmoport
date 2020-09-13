package com.space.controller;

import com.space.model.ShipInfo;
import com.space.model.Ship;
import com.space.model.ShipSearch;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest")
public class ShipController {

    private final ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping(value = "/ships")
    public ResponseEntity<List<Ship>> getShipsList(ShipSearch search) {
        return new ResponseEntity<>(shipService.readWithParams(search), HttpStatus.OK);
    }

    @PostMapping(value = "/ships")
    public ResponseEntity<Ship> newShip(@RequestBody ShipInfo shipInfo) {
        if (shipInfo.canCreateShip()) {
            return new ResponseEntity<>(shipService.create(shipInfo), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/ships/count")
    public ResponseEntity<Integer> getShipsCount(ShipSearch search) {
        return new ResponseEntity<>(shipService.shipCounts(search), HttpStatus.OK);
    }

    @GetMapping(value = "/ships/{id}")
    public ResponseEntity<Ship> getShipById(@PathVariable(name = "id") long id) {

        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!shipService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(shipService.read(id), HttpStatus.OK);
    }

    @PostMapping(value = "/ships/{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable(name = "id") long id, @RequestBody ShipInfo shipInfo) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!shipService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (shipInfo == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (shipInfo.getName() != null
                && (shipInfo.getName().length() > ShipInfo.MAX_NAME_LENGTH
                || shipInfo.getName().isEmpty())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (shipInfo.getCrewSize() < 0
                || shipInfo.getCrewSize() > 9999) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (shipInfo.getProdDate() != null
                && shipInfo.getProdDate().getTime() < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(shipService.update(id, shipInfo), HttpStatus.OK);
    }

    @DeleteMapping(value = "/ships/{id}")
    public ResponseEntity<?> deleteShipById(@PathVariable("id") long id) {

        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (shipService.exists(id)) {
            shipService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
