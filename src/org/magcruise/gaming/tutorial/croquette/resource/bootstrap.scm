(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
  ;;(builder:setRemoteDebugMode #t)
  (builder:setDefBootstrap
     (def:loader "org.magcruise.gaming.tutorial.croquette.resource.ResourceLoader"
        (def:src "game-definition.scm"))))
