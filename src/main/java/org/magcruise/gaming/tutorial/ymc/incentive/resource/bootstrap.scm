(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:addDefBootstrap
     (def:loader "org.magcruise.gaming.tutorial.ymc.incentive.resource.YmcGameResourceLoader"
        (def:src "game-definition.scm"))))
