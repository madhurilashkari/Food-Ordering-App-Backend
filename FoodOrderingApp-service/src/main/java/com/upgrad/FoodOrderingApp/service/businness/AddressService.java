package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;

/**
 * Created by murarka on 07/03/21.
 */
public class AddressService {

    public StateEntity getStateByUuid(String uuid){
        return new StateEntity();
    }

    public AddressEntity saveAddress(AddressEntity addressEntity,String authorization){
        return addressEntity;
    }
}
