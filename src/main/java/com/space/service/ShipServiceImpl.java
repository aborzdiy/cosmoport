package com.space.service;

import com.space.model.ShipInfo;
import com.space.model.Ship;
import com.space.model.ShipSearch;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {

    private final ShipRepository shipRepository;
    private final EntityManager entityManager;

    @Autowired
    public ShipServiceImpl(ShipRepository shipRepository, EntityManagerFactory factory) {
        this.shipRepository = shipRepository;
        this.entityManager = factory.createEntityManager();
    }

    @Override
    public List<Ship> readAll() {
        Pageable pageable = PageRequest.of(0, 3);
        return shipRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Ship> readWithParams(ShipSearch search) {

        TypedQuery<Ship> query = generateTypedQuery(search);

        return query
                .setFirstResult(search.getPageNumber() * search.getPageSize())
                .setMaxResults(search.getPageSize())
                .getResultList();
    }

    @Override
    public Ship create(ShipInfo shipInfo) {

        Ship ship = new Ship();
        ship.setName(shipInfo.getName());
        ship.setPlanet(shipInfo.getPlanet());
        ship.setShipType(shipInfo.getShipType());
        ship.setProdDate(shipInfo.getProdDate());
        ship.setUsed(shipInfo.isUsed());
        ship.setSpeed(shipInfo.getSpeed());
        ship.setCrewSize(shipInfo.getCrewSize());

        ship.calcRate();

        return shipRepository.save(ship);
    }

    @Override
    public Ship update(long id, ShipInfo shipInfo) {
        Ship ship = shipRepository.findById(id).orElse(null);

        assert ship!=null;

        if (shipInfo.getName() != null){
            ship.setName(shipInfo.getName());
        }

        if (shipInfo.getPlanet() != null){
            ship.setPlanet(shipInfo.getPlanet());
        }

        if (shipInfo.getShipType() != null){
            ship.setShipType(shipInfo.getShipType());
        }

        if (shipInfo.getProdDate() != null) {
            ship.setProdDate(shipInfo.getProdDate());
        }

        if (shipInfo.getSpeed() > 0){
            ship.setSpeed(shipInfo.getSpeed());
        }

        if (shipInfo.getCrewSize() > 0) {
            ship.setCrewSize(shipInfo.getCrewSize());
        }

        ship.calcRate();

        return shipRepository.save(ship);
    }

    @Override
    public void deleteById(Long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public boolean exists(Long id) {
        return shipRepository.existsById(id);
    }

    @Override
    public Ship read(Long id) {
        return shipRepository.findById(id).orElse(null);
    }

    @Override
    public int shipCounts(ShipSearch search) {

        TypedQuery<Ship> query = generateTypedQuery(search);

        return (int) query
                .getResultStream()
                .count();
    }

    private TypedQuery<Ship> generateTypedQuery(ShipSearch search) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ship> criteriaQuery = builder.createQuery(Ship.class);
        Root<Ship> ship = criteriaQuery.from(Ship.class);

        List<Predicate> predicates = new ArrayList<>();

        // имя корабля
        if (search.getName() != null) {
            predicates.add(
                    builder.like(
                            ship.get("name"),
                            "%" + search.getName() + "%")
            );
        }

        // название планеты
        if (search.getPlanet() != null) {
            predicates.add(
                    builder.like(
                            ship.get("planet"),
                            "%" + search.getPlanet() + "%")
            );
        }

        // тип корабля
        if (search.getShipType() != null) {
            predicates.add(
                    builder.equal(
                            ship.get("shipType"),
                            search.getShipType())
            );
        }

        // новый или б/у
        if (search.getIsUsed() != null) {
            predicates.add(
                    builder.equal(
                            ship.get("isUsed"),
                            Boolean.valueOf(search.getIsUsed()))
            );
        }

        // скорость больше чем
        if (search.getMinSpeed() > 0) {
            predicates.add(
                    builder.greaterThanOrEqualTo(
                            ship.get("speed"),
                            search.getMinSpeed())
            );
        }

        // скорость меньше чем
        if (search.getMaxSpeed() > 0) {
            predicates.add(
                    builder.lessThanOrEqualTo(
                            ship.get("speed"),
                            search.getMaxSpeed())
            );
        }

        // год производства больше чем
        if (search.getAfter() > 0) {
            predicates.add(
                    builder.greaterThanOrEqualTo(
                            ship.get("prodDate"),
                            new Date(search.getAfter()))
            );
        }

        // год производства меньше чем
        if (search.getBefore() > 0) {
            predicates.add(
                    builder.lessThanOrEqualTo(
                            ship.get("prodDate"),
                            new Date(search.getBefore()))
            );
        }

        // рейтинг больше чем
        if (search.getMinRating() > 0) {
            predicates.add(
                    builder.greaterThanOrEqualTo(
                            ship.get("rating"),
                            search.getMinRating())
            );
        }

        // рейтинг меньше чем
        if (search.getMaxRating() > 0) {
            predicates.add(
                    builder.lessThanOrEqualTo(
                            ship.get("rating"),
                            search.getMaxRating())
            );
        }

        // экипаж больше чем
        if (search.getMinCrewSize() > 0) {
            predicates.add(
                    builder.greaterThanOrEqualTo(
                            ship.get("crewSize"),
                            search.getMinCrewSize())
            );
        }

        // экипаж меньше чем
        if (search.getMaxCrewSize() > 0) {
            predicates.add(
                    builder.lessThanOrEqualTo(
                            ship.get("crewSize"),
                            search.getMaxCrewSize())
            );
        }

        Predicate finalPredicate = builder.and(
                predicates.toArray(new Predicate[predicates.size()])
        );

        criteriaQuery.where(finalPredicate);
        criteriaQuery.orderBy(builder.asc(ship.get(search.getOrder().getFieldName())));

        return entityManager.createQuery(criteriaQuery);

    }
}
