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
public enum StatusEnum {
    ACTIVE,
    INACTIVE,
    PENDING;

    public static StatusEnum getStatus(Integer ordinal) {

        if (ordinal == null) {
            return null;
        }

        for (StatusEnum en : StatusEnum.values()) {
            if (en.ordinal() == ordinal) {
                return en;
            }
        }
        return null;
    }
}
