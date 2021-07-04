package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;

import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by murarka on 07/02/21.
 */

@RestController
@RequestMapping("/")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(method = RequestMethod.POST,
            path = "/address" ,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)


    public ResponseEntity<SaveAddressResponse> addressSave
            (@RequestHeader("authorization") final String authorization ,final SaveAddressRequest saveAddressRequest) throws AuthorizationFailedException {

        AddressEntity addressEntity = new AddressEntity();

        addressEntity.setFlatBuilNumber(saveAddressRequest.getFlatBuildingName());
        addressEntity.setLocality(saveAddressRequest.getLocality());
        addressEntity.setCity(saveAddressRequest.getCity());
        addressEntity.setPincode(saveAddressRequest.getPincode());
        addressEntity.setStateEntity(addressService.getStateByUuid(saveAddressRequest.getStateUuid()));
        addressEntity.setUuid(UUID.randomUUID().toString());
        addressEntity.setActive(1);

        AddressEntity  saveAddressEntity = addressService.saveAddress(addressEntity,authorization);



        SaveAddressResponse saveAddressResponse = new SaveAddressResponse()
                .id(saveAddressEntity.getUuid())
                .status("ADDRESS SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SaveAddressResponse>(saveAddressResponse, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET,
            path = "/address/customer" ,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public ResponseEntity<AddressListResponse>getAllPermanentAddress(
            @RequestHeader("authorization") final String authorization)throws AuthorizationFailedException
    {
        String[] bearerToken = authorization.split("Bearer ");
        List<AddressEntity> addressEntityList = addressService.getAllAddress(bearerToken[1]);

        AddressListResponse addressListResponse = new AddressListResponse();
        for (AddressEntity ae : addressEntityList) {
            StateEntity se = addressService.getStateById(ae.getStateEntity().getId());

            AddressListState addressListState = new AddressListState();

            // Sets the state to each address element
            addressListState.setStateName(se.getStateName());

            // Adds the city, flat building name, locality, pincode and state to the addressList
            AddressList addressList = new AddressList().id(UUID.fromString(ae.getUuid())).city(ae.getCity())
                    .flatBuildingName(ae.getFlatBuilNumber()).locality(ae.getLocality())
                    .pincode(ae.getPincode()).state(addressListState);

            // Adds the addressList to addressListResponse
            addressListResponse.addAddressesItem(addressList);
        }

        return new ResponseEntity<AddressListResponse>(addressListResponse, HttpStatus.OK);

    }
}
