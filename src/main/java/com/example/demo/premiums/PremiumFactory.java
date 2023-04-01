package com.example.demo.premiums;

import com.example.demo.agreement.dao.Type;

public class PremiumFactory {
    public static PremiumType createPremium(Type type) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (PremiumType) Class.forName("com.example.demo.premiums."+type.name()).newInstance();
    }
}
