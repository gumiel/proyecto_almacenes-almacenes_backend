
-- Almacenes
INSERT INTO public.storehouse (active, storehouse_type_id, created_date, last_modified_date, code, "name", description, created_by, last_modified_by) VALUES(true, (select id from public.storehouse_type where code='TIPO-REG' limit 1 ), NULL, NULL, 'ALM-CBBA', 'Almacen Cochabamba', 'Almacen regional para todo Cochabamba', NULL, NULL);
INSERT INTO public.storehouse (active, storehouse_type_id, created_date, last_modified_date, code, "name", description, created_by, last_modified_by) VALUES(true, (select id from public.storehouse_type where code='TIPO-REG' limit 1 ), NULL, NULL, 'ALM-LP', 'Almacen La Paz', 'Almacen regional para todo La Paz', NULL, NULL);
