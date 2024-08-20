package com.myproject.CortaCaminho.domain.Url.Services;

import com.myproject.CortaCaminho.domain.Url.Url;
import org.springframework.stereotype.Service;

@Service

public class ViewCounterService {
    public void addViews(Url url){
        url.setViews(url.getViews()+1);
    }
}
