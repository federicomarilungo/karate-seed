UPDATE schemaExample.tableExample
SET fecha_facturacion_actual = CAST(? as DATE)
WHERE id_cuenta = ?