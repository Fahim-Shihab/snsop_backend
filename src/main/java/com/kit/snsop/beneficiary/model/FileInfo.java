/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.model;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author anwar
 */
@Data
@NoArgsConstructor
public class FileInfo {
    private String fileName;
    private Date lastModified;

    public FileInfo(String fileName, Date lastModified) {
        this.fileName = fileName;
        this.lastModified = lastModified;
    }
    
    
}
