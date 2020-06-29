@Token
Feature: Token

  Background:
    * def setup = karate.callSingle('../../context/build-context.feature')
    * def authAm = setup.authAm
    * def canal = setup.canal
    * def authorization = setup.authorization
    * def jwtGen = setup.jwtGen
    * def env = setup.env

  Scenario: Token
    * def usuario = __arg
    * def token = jwtGen.createJWT(usuario.rut, usuario.user_id)
