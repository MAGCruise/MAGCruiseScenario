(define (def:setup-bootstrap-builder builder ::BootstrapBuilder)
    (builder:setDefBootstrap
     (def:classpath "MAGCruiseGameClasses.jar")
     (def:game-definition "C:\\Users\\nkjm\\AppData\\Local\\Temp\\magcruisecore-1450440635642-5008917917858003974.scm")
     (def:resource-loader "org.magcruise.gaming.tutorial.scenario.trans.TranslationGameResourceLoader")))
