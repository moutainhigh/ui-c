package com.sep.distribution.dto;

import lombok.Data;

import java.util.List;

@Data
public class InvoicesDto {
    private List<InvoiceDto> list;
}
