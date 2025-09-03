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
import java.util.Arrays;
import java.util.Optional;

public enum NomineePopupResponse {
    FEATURE_NOT_PRESENT(0, "NO_RESPONSE_CAPTURE_FEATURE", "Response capture feature not present"),
    NOT_SELECTED(1, "NO_POPUP_SHOWN", "No popup shown"),
    YES_SELECTED(2, "YES_SELECTED", "Yes selected"),
    NO_SELECTED(3, "NO_SELECTED", "No selected");

    private int id;
    private String name;
    private String desc;

    private NomineePopupResponse(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public static NomineePopupResponse getNomineePopupResponseByID(int id) {
        Optional<NomineePopupResponse> response = Arrays.stream(NomineePopupResponse.values())
                .filter(val -> val.getId() == id)
                .findFirst();

        return response.orElse(NomineePopupResponse.FEATURE_NOT_PRESENT);
    }

    public static Integer encode(Integer age, NomineePopupResponse currentResponse) throws Exception {
        return age + (currentResponse.getId() * 1000);
    }

    public static NomineePopupResponse decode(Integer age) throws Exception {
        return getNomineePopupResponseByID(age / 1000);
    }
}
