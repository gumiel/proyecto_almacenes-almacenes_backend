package com.gestion.almacenes.commons;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Data
@Table(name = "reports")
public class Report  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean showHeader = true;
    private boolean showFooter = true;
    private boolean showHeaderLogo = true;
    private boolean showHeaderBusiness = true;
    private boolean showFooterLine = true;
    private boolean showFooterUser = true;
    private boolean showFooterPagination = true;
    private boolean showFooterCode = true;
    private boolean showFooterDate = true;
    @Column(length = 50, nullable = false)
    private String code;
    @Column(length = 50, nullable = false)
    private String reportFileName;
    @Column(length = 50, nullable = false)
    private String reportName;
    private String tittle;
    private String subTittle;
    private String details;

}
