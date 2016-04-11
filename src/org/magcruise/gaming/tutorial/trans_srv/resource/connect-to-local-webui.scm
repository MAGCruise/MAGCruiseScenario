(define (def:setup-services builder ::GameSystemPropertiesBuilder)
  (builder:addProperties
    (def:ui-service
      "http://localhost:8080/MAGCruiseBroker/json/GameInteractionService"
      "admin")
    (def:request-to-game-executor-publisher-service
      "http://localhost:8080/MAGCruiseBroker/json/GameInteractionService")))

