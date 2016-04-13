(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:setDefBootstrap
     (def:loader "org.magcruise.gaming.tutorial.trans_srv.resource.TranslationServiceGameResourceLoader"
        (def:src "game-definition.scm")
        (def:src "connect-to-webui.scm"))))

