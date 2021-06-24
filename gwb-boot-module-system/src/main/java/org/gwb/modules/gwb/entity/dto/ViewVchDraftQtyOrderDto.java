package org.gwb.modules.gwb.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ViewVchDraftQtyOrderDto extends ViewVchDraftQtyOrder {
    private String vchTypeName;

    private String usedTypeName;
}
