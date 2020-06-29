@Invoice
Feature: Invoice
  ## Información de pagos del Cliente

  Background:
    * def setup = karate.callSingle('../../context/build-context.feature')
    * def am = setup.am + "/api/v1/" + "invoice/"
    * def dm = setup.dm
    * def utils = setup.utils


    #Ruts de prueba
    * def clienteConFacturacion = setup.clienteConFacturacion

    * def schemaVacio =
    """
    {
      size:10,
      page:0,
      total_pages:0,
      invoices_views:[]
    }
    """
    * def schema =
    """
    { size:#number,
      page:#number,
      total_pages:#number,
      invoices_views:[{
        "billing_id":"#string",
        "billing_due_date":"#string",
        "billing_date":"#string",
        "total_debt":{
          "symbol":"#string",
          "currency":"#string",
          "value":#number
          },
        "minimum_payment":{
          "symbol":"#string",
          "currency":"#string",
          "value":#number
          },
        "billed_amount":{
          "symbol":"#string",
          "currency":"#string",
          "value":#number
          }
      }
      ]
    }
    """

  Scenario Outline:  CASO 1 : Validación de Cliente con información de Pagos - Con Facturación
    * def usuario =
    """
    {
      "rut" : "<rut>",
      "clave" : "<clave>",
      "user_id" : <user_id>
    }
    """
    * def today = utils.getToday()
    * def sesion = karate.call('../../context/creacion-token.feature', usuario)
    * def result = dm.executeUpdateBy("actualizar_fecha_facturacion_por_id_cuenta.sql", today, <id_cuenta>)
    Given url am + <id_cuenta>
    And header Authorization = 'Bearer '+ sesion.token
    When method get
    Then status 200
    And match response == schema

    Examples:
      | clienteConFacturacion |