(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:setDefBootstrap
     (def:loader "org.magcruise.gaming.tutorial.ymc.incentive.resource.YmcGameResourceLoader"
        (def:src "game-definition.scm"))))
