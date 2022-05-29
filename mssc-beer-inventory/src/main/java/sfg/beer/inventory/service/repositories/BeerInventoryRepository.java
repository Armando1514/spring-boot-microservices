/*
 *  Copyright 2019 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package sfg.beer.inventory.service.repositories;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import sfg.beer.inventory.service.domain.BeerInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by jt on 2019-01-26.
 */
public interface BeerInventoryRepository extends JpaRepository<BeerInventory, UUID> {

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    List<BeerInventory> findAllByBeerId(UUID beerId);
    List<BeerInventory> findAllByUpc(String upc);
}
