package lk.ijse.stockmanage102.dto;

/*
    @author DanujaV
    @created 9/16/23 - 2:33 PM   
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierDto {
    private String supplierId;
    private String name;
    private String shop;
    private String tel;
}
