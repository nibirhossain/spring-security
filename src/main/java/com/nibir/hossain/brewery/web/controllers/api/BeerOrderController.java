package com.nibir.hossain.brewery.web.controllers.api;

/*
 * Created by Nibir Hossain on 20.12.20
 */

import com.nibir.hossain.brewery.services.BeerOrderService;
import com.nibir.hossain.brewery.web.model.BeerOrderDto;
import com.nibir.hossain.brewery.web.model.BeerOrderPagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(path = "/api/v1/customers/{customerId}/")
@RestController
public class BeerOrderController {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 2;

    @Autowired
    private BeerOrderService beerOrderService;

    @GetMapping(path = "orders")
    public BeerOrderPagedList listOrders(@PathVariable("customerId") UUID customerId,
                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if(pageNumber == null || pageNumber <= 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if(pageSize == null || pageSize <= 0) {
            pageSize = DEFAULT_PAGE_NUMBER;
        }

        return beerOrderService.listOrders(customerId, PageRequest.of(pageNumber, pageSize));
    }

    @PostMapping(path = "orders")
    @ResponseStatus(HttpStatus.CREATED)
    public BeerOrderDto placeOrder(@PathVariable("customerId") UUID customerId, @Validated @RequestBody BeerOrderDto beerOrderDto) {
        return beerOrderService.placeOrder(customerId, beerOrderDto);
    }

    @GetMapping(path = "orders/{orderId}")
    public BeerOrderDto getOrder(@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId) {
        return beerOrderService.getOrderById(customerId, orderId);
    }

    @PutMapping(path = "orders/{orderId}/pickup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pickupOrder(@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId) {
        beerOrderService.pickupOrder(customerId, orderId);
    }
}
