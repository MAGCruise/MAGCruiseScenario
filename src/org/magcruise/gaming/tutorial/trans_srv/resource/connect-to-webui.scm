(define (def:setup-game-system-properties-builder builder ::GameSystemPropertiesBuilder)
  (builder:addProperties
   (def:game-interaction-service
     "http://phoenix.toho.magcruise.org/MAGCruiseBroker/json/GameInteractionService")
   (def:ui-service
     "http://toho.magcruise.org/world/BackendAPIService" "admin")))

