CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `dev`@`%` 
    SQL SECURITY DEFINER
VIEW `fog`.`itemListView` AS
    SELECT 
        `i`.`orderId` AS `orderId`,
        `i`.`materialVariantId` AS `materialId`,
        `i`.`amount` AS `amount`,
        `i`.`partFor` AS `partFor`,
        `mv`.`length` AS `length`,
        `m`.`price` AS `price`,
        `m`.`description` AS `materialDescription`,
        `mbf`.`description` AS `buildFunction`,
        `mt`.`description` AS `materialType`
    FROM
        ((((`fog`.`itemList` `i`
        JOIN `fog`.`materialVariant` `mv` ON ((`i`.`materialVariantId` = `mv`.`materialVariantId`)))
        JOIN `fog`.`material` `m` ON ((`mv`.`materialId` = `m`.`materialId`)))
        JOIN `fog`.`materialBuildFunction` `mbf` ON ((`mbf`.`materialBuildFunctionId` = `m`.`materialBuildFunctionId`)))
        JOIN `fog`.`materialType` `mt` ON ((`mt`.`materialTypeId` = `m`.`materialTypeId`)))