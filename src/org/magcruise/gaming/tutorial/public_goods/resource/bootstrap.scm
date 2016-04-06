(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:setDefBootstrap
     (def:loader "org.magcruise.gaming.tutorial.public_goods.resource.PublicGameResourceLoader"
        (def:src "game-definition.scm"))))
