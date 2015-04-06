(define-namespace shop "shop")
(define-namespace factory "factory")
(define-namespace farmer "farmer")

(define *shop-names* '(Shop1 Shop2))

(define-alias CroquetteDelivery org.magcruise.gaming.scenario.croquette.msg.CroquetteDelivery)
(define-alias CroquetteOrder org.magcruise.gaming.scenario.croquette.msg.CroquetteOrder)
(define-alias PotatoOrder org.magcruise.gaming.scenario.croquette.msg.PotatoOrder)
(define-alias PotatoDelivery org.magcruise.gaming.scenario.croquette.msg.PotatoDelivery)
(define-alias Shop org.magcruise.gaming.scenario.croquette.player.Shop)
(define-alias Farmer org.magcruise.gaming.scenario.croquette.player.Farmer)
(define-alias Factory org.magcruise.gaming.scenario.croquette.player.Factory)
(define-alias Market org.magcruise.gaming.scenario.croquette.Market)

(define (def:game-scenario)
  (def:ext-context Market)
  (def:ext-player 'Farmer 'agent Farmer)
  (def:ext-player 'Factory 'human Factory
    (Integer[] 300 300 300 300 300 300 300 300 300 300 300))

  (def:ext-player 'Shop1 'human Shop
      (Integer[] 100 100 100 100 100 100 100 100 100 100 100)
      (Integer[] 400 400 400 400 400 400 400 400 400 400 400))
  (def:ext-player 'Shop2 'human Shop
      (Integer[] 150 150 150 150 150 150 150 150 150 150 150)
      (Integer[] 150 150 150 150 150 150 150 150 150 150 150))

  (def:round
    (def:parallel-stage 'init
      (def:task 'Factory 'factory:init)
      (def:players-task *shop-names* 'shop:init))
    (def:parallel-stage 'shop-order-and-pricing-factory-order
      (def:players-task *shop-names* 'shop:order)
      (def:players-task *shop-names* 'shop:pricing)
      (def:stage 'factory-order 
        (def:task 'Factory 'factory:order)
        (def:task 'Farmer 'farmer:receive-order)))
    (def:stage 'factory-receive-order 
      (def:task 'Factory 'factory:receive-order))
    (def:parallel-stage 'closing
      (def:task 'Factory 'factory:closing)
      (def:players-task *shop-names* 'shop:closing)))

  (def:round
    (def:parallel-stage 'refresh
      (def:players-task *shop-names* 'shop:refresh)
      (def:task 'Factory 'factory:refresh)
      (def:task 'Farmer 'farmer:refresh))
    (def:stage 'farmer-delivery
      (def:task 'Farmer 'farmer:delivery)
      (def:task 'Factory 'factory:receive-delivery))
    (def:restage 'shop-order-and-pricing-factory-order)
    (def:restage 'factory-receive-order)
    (def:restage 'closing))

  (def:rounds 6
    (def:restage 'refresh)
    (def:stage 'factory-delivery
      (def:task 'Factory 'factory:delivery)
      (def:players-task *shop-names* 'shop:receive-delivery))
    (def:restage 'farmer-delivery)
    (def:restage 'shop-order-and-pricing-factory-order)
    (def:restage 'factory-receive-order)
    (def:restage 'closing))

  (def:round 
    (def:restage 'refresh)
    (def:restage 'factory-delivery)
    (def:restage 'farmer-delivery)
    (def:parallel-stage 'shop-pricing
      (def:players-task *shop-names* 'shop:pricing))
    (def:restage 'closing))

  (def:round 
    (def:restage 'refresh)
    (def:restage 'factory-delivery)
    (def:restage 'shop-pricing)
    (def:restage 'closing))

  (def:round 
    (def:restage 'refresh)))


(define (shop:refresh ctx ::Market self ::Shop)
  (define other ::Shop (ctx:getOther self))
  (define sale-msg (to-string "コロッケを1個" self:price "円で販売しました．" "お店にはお客さんが" self:demand "人来ました．"
                              self:sales "個が売れました．" "売り上げは" self:earnings "円です．"))
  (ui:show-message self:name
    (<div-class> "alert alert-info"
      (to-string
        (<h4> (- ctx:roundnum 1) "日目が終わりました．")
        (<br>)
        (<h4> "概要")
        (<p> sale-msg)
        (<br>)
        (<h4> "詳細")
        (<ul>
          (<li> "在庫：" self:delivery "個の冷凍コロッケが納品されました．"
                         self:sales "個のコロッケを売りました．"
                         "在庫は" self:stock "個です．"
                         "また，" self:order "個の冷凍コロッケを発注しました．この冷凍コロッケは翌々日に納品予定です．")
          (<li> "販売：" sale-msg)
          (<li> "収支：" "仕入費は" self:materialCost "円，"
                         "在庫費は" self:inventoryCost "円，"
                         "売上高は" self:earnings "円，"
                         "利益は" self:profit "円です．")
          (<li> "競合店：" "競合店は" other:price "円でコロッケを販売し，"  other:demand "人が来店したそうです．"))
        (<br>)
        (<h4> "在庫表")
        (self:tabulateHistory 'delivery 'sales 'stock 'order )
        (<h4> "販売表")
        (self:tabulateHistory 'price 'sales 'earnings 'demand)
        (<h4> "収支表")
        (self:tabulateHistory 'materialCost 'inventoryCost 'earnings 'profit))))
  (ui:request-to-input self:name
    (ui:form (to-string  (<h3> (- ctx:roundnum 1) "日目終了")
                         (<p> "次の日に進みます．")))
    (lambda () #t))
  (ui:show-message self:name (<div-class> "alert alert-warning" (to-string (<h4> ctx:roundnum "日目のはじまりです．"))))
  (self:refresh))

(define (factory:refresh ctx ::Market self ::Factory)
  (ui:show-message self:name
    (<div-class> "alert alert-info"
      (to-string
        (<h4> (- ctx:roundnum 1) "日目が終わりました．")
        (<br>)
        (<h4> "概要")
        (<p>  self:deliveredPotato "個のじゃがいもが納品され，" self:production "個の冷凍コロッケを作成しました．"
             "また，各ショップから" self:orders "の注文を受けました．翌々日開始時点に納品する必要があります．")
        (<br>)
        (<h4> "詳細")
        (<ul>
          (<li> "販売：" self:demand "個の冷凍コロッケの納品が必要でした．" "冷凍コロッケを1個" self:price "円で"
                         self:sales "個納品しました．" "売り上げは" self:earnings "円です．")
          (<li> "発注：" self:orderOfPotato "個のじゃがいもを発注しました．翌日に納品されます．")
          (<li> "受注：" "各ショップから" self:orders "の注文を受けました．")
          (<li> "生産：" self:deliveredPotato "個のじゃがいもが納品されました．支払額は" self:materialCost "円です．"
                         self:production "個の冷凍コロッケを作成しました．" self:machiningCost "円の生産費がかかりました．")
          (<li> "在庫：" "冷凍コロッケの在庫は" self:stock "個になりました．")
          (<li> "収支：" "仕入費は" self:materialCost "円，"
                         "在庫費は" self:inventoryCost "円，"
                         "売上高は" self:earnings "円，"
                         "利益は" self:profit "円です．"))
        (<br>)
        (<h4> "在庫表")
        (self:tabulateHistory 'deliveredPotato 'production 'stock 'orderOfPotato 'orders)
        (<h4> "販売表")
        (self:tabulateHistory 'price 'sales 'earnings 'demand)
        (<h4> "収支表")
        (self:tabulateHistory 'inventoryCost 'materialCost 'machiningCost 'earnings 'profit))))
  (ui:request-to-input self:name
    (ui:form (to-string  (<h3> (- ctx:roundnum 1) "日目終了")
                         (<p> "次の日に進みます．")))
    (lambda () #t))
  (ui:show-message self:name (<div-class> "alert alert-warning" (to-string (<h4> ctx:roundnum "日目のはじまりです．"))))
  (self:refresh)
)

(define (shop:init ctx ::Market self ::Shop)
  (define msg
     (<div-class> "alert alert-warning"
        (to-string (<h4> ctx:roundnum "日目がはじまりました．")
                   "初日(0日目)に発注した冷凍コロッケは翌々日(2日目)に納品され，その日から販売できます．<br>"
                   "現在の冷凍コロッケの在庫は" self:stock "個です．")))
  (ui:request-to-input self:name (ui:form msg) (lambda () #t))
  (ui:show-message self:name msg))

(define (factory:init ctx ::Market self ::Factory)
  (define msg
      (<div-class> "alert alert-warning"
        (to-string (<h4> ctx:roundnum "日目がはじまりました．")
                   "初日(0日目)に受ける注文は翌々日(2日目)開始時に納品しなくてはなりません．<br>"
                   "初日(0日目)にじゃがいもを発注すると，翌日(1日目)に農家から納品されて冷凍コロッケを生産し，翌々日(2日目)にへショップへ納品できます．<br>"
                   "現在の冷凍コロッケの在庫は" self:stock "個です．")))
  (ui:request-to-input self:name (ui:form msg) (lambda () #t))
  (ui:show-message self:name msg))


(define (factory:closing ctx ::Market self ::Factory)
  (self:closing))

(define (shop:closing ctx ::Market self ::Shop)
  (self:closing (ctx:distributeDemand self)))


(define (shop:order ctx ::Market self ::Shop)
  (ui:request-to-input self:name
    (ui:form
      (to-string  (<h4> ctx:roundnum "日目の発注") self:name "さん，コロッケ工場へ発注して下さい．発注したものは，翌々日の販売前に納品される予定です．")
      (ui:number "個数(冷凍コロッケ)" 'num-of-croquette (self:defaultOrders ctx:roundnum) (Min 0) (Max 1000)))
    (lambda (num-of-croquette ::number)
      (self:order num-of-croquette)
      (ui:show-message self:name (<div-class> "alert alert-success" (to-string "冷凍コロッケを" num-of-croquette "個発注しました．翌々日に納品されます．")))
      (manager:send-message 'Factory (make CroquetteOrder self:name num-of-croquette)))))

(define (shop:pricing ctx ::Market self ::Shop)
  (ui:request-to-input self:name
    (ui:form
          (to-string (<h4> ctx:roundnum "日目の販売価格") self:name "さん，今日のコロッケの販売価格を決定して下さい．")
      (ui:number "販売価格(コロッケ)" 'price (self:defaultPrices ctx:roundnum) (Min 50) (Max 200)))
    (lambda (price-of-croquette ::number)
      (ui:show-message self:name (<div-class> "alert alert-success" (to-string "コロッケ1個の販売価格を" price-of-croquette "円に決めました．")))
      (self:price price-of-croquette))))

(define (shop:order-and-pricing ctx ::Market self ::Player)
  (shop:order ctx self)
  (shop:pricing ctx self))

(define (shop:receive-delivery ctx ::Market self ::Shop)
  (define msg ::CroquetteDelivery (self:msgbox:pop))
  (self:receiveDelivery msg:num)
  (ui:show-message self:name 
    (<div-class> "alert alert-info"
      (to-string "冷凍コロッケが" msg:num "個納品されました．在庫数は" self:stock "個になりました．"))))


(define (factory:receive-order ctx ::Market self ::Factory)
  (for-each
    (lambda (msg ::CroquetteOrder)
      (self:receiveOrder msg))
    (self:msgbox:popAll)))


(define (factory:order ctx ::Market self ::Factory)
  (ui:request-to-input self:name
    (ui:form
      (to-string (<h4> ctx:roundnum "日目の発注") self:name "さん，農場へじゃがいもを発注して下さい．発注したものは，翌日に納品されます．")
      (ui:number "個数(ジャガイモ)" 'potato (self:defaultOrdersToFarmer ctx:roundnum) (Min 0) (Max 1000)))
    (lambda (potato ::number)
      (self:order potato)
      (ui:show-message self:name (<div-class> "alert alert-success" (to-string "じゃがいもを" potato "個発注しました．翌日に納品されます．")))
      (manager:send-message 'Farmer (make PotatoOrder self:name potato)))))

(define (factory:delivery ctx ::Market self ::Factory)
  (define stock-before-delivery self:stock)
  (define msg "")
  (for-each
    (lambda (shop-name ::symbol)
      (define delivery (self:delivery ctx shop-name stock-before-delivery))
      (set! msg (to-string msg (to-string shop-name "に冷凍コロッケ" delivery "個を納品しました．")))
      (manager:send-message shop-name (make CroquetteDelivery self:name delivery)))
    *shop-names*)
  (ui:show-message self:name
      (<div-class> "alert alert-info" msg "在庫は" self:stock "個になりました．")))

(define (factory:receive-delivery ctx ::Market self ::Factory)
  (define msg ::PotatoDelivery (self:msgbox:pop))
  (self:receiveDeliveryAndProduce msg:num)
  (ui:show-message self:name
    (<div-class> "alert alert-info"
      (to-string "じゃがいも" msg:num "個が納品され，" self:production "個の冷凍コロッケを生産しました．在庫は" self:stock "個になりました．"))))

  
(define (farmer:receive-order ctx ::Market self ::Farmer)
  (define msg ::PotatoOrder (self:msgbox:pop))
  (self:receiveOrder msg:num))

(define (farmer:delivery ctx ::Market self ::Farmer)
  (manager:send-message 'Factory (make PotatoDelivery self:name (self:delivery ctx))))

(define (farmer:refresh ctx ::Market self ::Farmer)
  (self:refresh))
