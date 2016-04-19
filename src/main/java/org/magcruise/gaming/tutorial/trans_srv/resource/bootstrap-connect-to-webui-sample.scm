(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:setDefBootstrap
     (def:loader "org.magcruise.gaming.tutorial.trans_srv.resource.TranslationServiceGameResourceLoader"
        (def:src "game-definition.scm"))))

(define (def:setup-services builder ::GameSystemPropertiesBuilder)
  (builder:addProperties
    (def:ui-service-and-register-session
      "http://toho.magcruise.org/world/BackendAPIService"
      "admin"
      "http://xxx.toho.magcruise.org:8080/MAGCruiseBroker/json/GameProcessService"
      "http://xxx.toho.magcruise.org:8080/MAGCruiseBroker/json/GameInteractionService"
      "http://proxy.xxx.toho.magcruise.org/MAGCruiseBroker/json/GameProcessService"
      "http://proxy.xxx.toho.magcruise.org/MAGCruiseBroker/json/GameInteractionService")
    (def:ui-service
      "http://localhost:8080/MAGCruiseBroker/json/GameInteractionService")
    (def:request-to-game-executor-publisher-service
      "http://localhost:8080/MAGCruiseBroker/json/GameInteractionService")))

