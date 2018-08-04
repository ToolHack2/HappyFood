package com.example.user.smartfoody.GoogleDirection;

import com.example.user.smartfoody.Model.Route;

import java.util.List;

public interface DirectionFinderListener {

    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
