(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
  (builder:setDefBootstrap
   (def:remoteDebug #t)
   (def:autoInput #t)
   (def:loader "org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader"
        (def:src "game-definition.scm"))))

