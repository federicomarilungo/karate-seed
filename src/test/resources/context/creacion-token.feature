@Token
Feature: Token

  Background:
    * def setup = karate.callSingle('../../context/build-context.feature')
    * def authAm = setup.authAm
    * def canal = setup.canal
    * def authorization = setup.authorization
    * def jwtGen = setup.jwtGen
    * def env = setup.env

  Scenario: Token Enviroment Defined
    * def usuario = __arg
    * def creacionToken = env == "qa" ? karate.call('../../context/creacion-token-qa.feature', usuario) : karate.call('../../context/creacion-token-uat.feature', usuario)
    * def token = creacionToken.token