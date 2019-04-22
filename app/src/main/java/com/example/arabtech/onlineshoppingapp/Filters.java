package com.example.arabtech.onlineshoppingapp;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filters {

    private ArrayList<Product> products;
    private ArrayList<Product> filtered;

    public Filters(ArrayList<Product> products) {
        this.products = products;
        filtered = new ArrayList<Product>();
    }

    public ArrayList<Product> getFiltered() {
        return filtered;
    }

    public void setFiltered(ArrayList<Product> filtered) {
        this.filtered = filtered;
    }

    public void categoryFilter(String category) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.getCategory().equals(category)) {
                filtered.add(p);
            }
        }
    }

    public void priceFilter(int price) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            float p_price;
            String REGEX = "\\d+(.\\d+)?";
            Pattern pattern = Pattern.compile(REGEX);
            Matcher matcher = pattern.matcher(p.getPrice());
            if (matcher.find()) {
                p_price = Float.parseFloat(p.getPrice().substring(matcher.start(), matcher.end()));
                if (p_price <= price) {
                    filtered.add(p);
                }
            }
        }
    }
}
