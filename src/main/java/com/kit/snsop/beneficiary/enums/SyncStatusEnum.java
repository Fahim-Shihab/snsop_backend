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
public enum SyncStatusEnum {
    PENDING,
    SYNCED,
    FAILED;

    public static SyncStatusEnum getStatus(Integer ordinal) {

        if (ordinal == null) {
            return null;
        }

        for (SyncStatusEnum en : SyncStatusEnum.values()) {
            if (en.ordinal() == ordinal) {
                return en;
            }
        }
        return null;
    }
}
