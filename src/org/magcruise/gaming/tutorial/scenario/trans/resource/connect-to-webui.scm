(define (def:setup-game-system-properties-builder builder ::GameSystemPropertiesBuilder)
  (builder:addProperties
   (def:ui
     "http://toho.magcruise.org/world/BackendAPIService"
     "nkjm"
     "http://localhost:8080/magcruise-it/json/RequestToGameExecutorRelayService")
   (def:request-to-game-executor-publisher
     "http://toho.magcruise.org/magcruise-it/json/RequestToGameExecutorRelayService")))
