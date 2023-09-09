package lk.ijse.stockmanage102.dto.tm;

/*
    @author DanujaV
    @created 9/9/23 - 4:44 PM   
*/

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
/*@Getter
@Setter
@ToString*/
public class ItemTm {   //lombok
    private String code;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}
