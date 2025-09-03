/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.enums;

/**
 *
 * @author anwar
 */
public enum CurrencyEnum {
    SUDANESE_POUND,
    USD,
    POUND,
    EURO;

    public static CurrencyEnum getCurrency(Integer ordinal) {
        if (ordinal == null) {
            return null;
        }
        for (CurrencyEnum en : CurrencyEnum.values()) {
            if (en.ordinal() == ordinal) {
                return en;
            }
        }
        return null;
    }
}
