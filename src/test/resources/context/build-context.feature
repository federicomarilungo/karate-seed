Feature: Build context

  Scenario:
    * def dmClass = Java.type('core.DataManager')
    * def utilsClass = Java.type('core.Utils')
    * def jwtGen = Java.type('core.JWTGenerator')

    * def dm = dmClass.getInstance()
    * def utils = utilsClass.getInstance()

    * def am = dm.getHost();
    * def authAm = dm.getAuthHost();
    * def canal = dm.getCanal();
    * def authorization = dm.getAuthorization();
    * def env = dm.getEnv()

    * def dataPath = 'classpath:src/test/resources/data/' + env;

    * def clienteConFacturacion = read(dataPath+'/clienteConFacturacion.json')
