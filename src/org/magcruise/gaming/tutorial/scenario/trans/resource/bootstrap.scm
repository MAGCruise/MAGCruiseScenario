(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:setDefBootstrap
     ;;(def:cp "https://www.dropbox.com/s/ur2n7mrak6v9koa/MAGCruiseGameClasses.jar?dl=1")
     (def:loader "org.magcruise.gaming.tutorial.scenario.trans.resource.ResourceLoader"
        (def:src "connect-to-webui.scm")
        (def:src "game-definition.scm"))))
