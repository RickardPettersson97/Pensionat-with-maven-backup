package com.backend2.backend2_pensionat_with_maven.services;


import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface RabattService {

    public double calculateDiscount(LocalDate startDatum, LocalDate slutDatum, int antalNätterUnderÅret);

    public double applyDiscount(double totalPris, double discount);

}
