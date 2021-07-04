package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;

import java.util.ArrayList;
import java.util.List;

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

    public List<AddressEntity> getAllAddress(String authorization){
        return new ArrayList<>();
    }

    public StateEntity getStateById(Integer id){
        return new StateEntity();
    }
}
