package com.nibir.hossain.brewery.web.controllers.api;

/*
 * Created by Nibir Hossain on 20.12.20
 */

import com.nibir.hossain.brewery.domain.security.CustomUser;
import com.nibir.hossain.brewery.security.permissions.BeerOrderCreatePermission;
import com.nibir.hossain.brewery.security.permissions.BeerOrderPickupPermission;
import com.nibir.hossain.brewery.security.permissions.BeerOrderReadPermission;
import com.nibir.hossain.brewery.security.permissions.BeerOrderReadPermissionV2;
import com.nibir.hossain.brewery.services.BeerOrderService;
import com.nibir.hossain.brewery.web.model.BeerOrderDto;
import com.nibir.hossain.brewery.web.model.BeerOrderPagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(path = "/api/v2/")
@RestController
public class BeerOrderControllerV2 {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 2;

    @Autowired
    private BeerOrderService beerOrderService;

    @BeerOrderReadPermissionV2
    @GetMapping(path = "orders")
    public BeerOrderPagedList listOrders(@AuthenticationPrincipal CustomUser user,
                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if(pageSize == null || pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        if(user.getCustomer() != null) {
            return beerOrderService.listOrders(user.getCustomer().getId(), PageRequest.of(pageNumber, pageSize));
        } else {
            return beerOrderService.listOrders(PageRequest.of(pageNumber, pageSize));
        }
    }

    @BeerOrderCreatePermission
    @PostMapping(path = "orders")
    @ResponseStatus(HttpStatus.CREATED)
    public BeerOrderDto placeOrder(@PathVariable("customerId") UUID customerId, @Validated @RequestBody BeerOrderDto beerOrderDto) {
        return beerOrderService.placeOrder(customerId, beerOrderDto);
    }

    @BeerOrderReadPermission
    @GetMapping(path = "orders/{orderId}")
    public BeerOrderDto getOrder(@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId) {
        return beerOrderService.getOrderById(customerId, orderId);
    }

    @BeerOrderPickupPermission
    @PutMapping(path = "orders/{orderId}/pickup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pickupOrder(@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId) {
        beerOrderService.pickupOrder(customerId, orderId);
    }
}
