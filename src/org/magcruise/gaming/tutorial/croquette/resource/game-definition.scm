(define-alias CroquetteDelivery org.magcruise.gaming.tutorial.croquette.msg.CroquetteDelivery)
(define-alias CroquetteOrder org.magcruise.gaming.tutorial.croquette.msg.CroquetteOrder)
(define-alias PotatoOrder org.magcruise.gaming.tutorial.croquette.msg.PotatoOrder)
(define-alias PotatoDelivery org.magcruise.gaming.tutorial.croquette.msg.PotatoDelivery)
(define-alias Shop org.magcruise.gaming.tutorial.croquette.actor.Shop)
(define-alias Farmer org.magcruise.gaming.tutorial.croquette.actor.Farmer)
(define-alias Factory org.magcruise.gaming.tutorial.croquette.actor.CroquetteFactory)
(define-alias Market org.magcruise.gaming.tutorial.croquette.actor.Market)

(define-namespace croquette "croquette")
(define-namespace shop "shop")
(define-namespace factory "factory")
(define-namespace farmer "farmer")

(define *shop-names* '(Shop1 Shop2))

(define (def:setup-game-builder builder ::GameBuilder)

  ;;(croquette:def-assign builder 'user1 'user2 'admin)
  (builder:addDefContext (def:context Market))

  (builder:addDefPlayers
    (def:player 'Farmer 'agent Farmer)
    (def:player 'Factory 'human Factory
      (list 300 300 300 300 300 300 300 300 300 300 300))

    (def:player 'Shop1 'human Shop
        (list 100 100 100 100 100 100 100 100 100 100 100)
        (list 400 400 400 400 400 400 400 400 400 400 400))
    (def:player 'Shop2 'human Shop
        (list 150 150 150 150 150 150 150 150 150 150 150)
        (list 150 150 150 150 150 150 150 150 150 150 150)))

  (builder:addDefRounds
    (def:round
      (def:parallel-stage 'init
        (def:task 'Factory 'factory:init)
        (def:players-task *shop-names* 'shop:init))
      (def:parallel-stage 'shop-order-and-pricing-factory-order
        (def:players-task *shop-names* 'shop:order)
        (def:players-task *shop-names* 'shop:pricing)
        (def:stage 'factory-order
          (def:task 'Factory 'factory:order)
          (def:task 'Farmer 'receiveOrder)))
      (def:stage 'factory-receive-order
        (def:task 'Factory 'receiveOrder))
      (def:parallel-stage 'closing
        (def:task 'Factory 'factory:closing)
        (def:players-task *shop-names* 'shop:closing))))

  (builder:addDefRounds
    (def:round
      (def:parallel-stage 'refresh
        (def:players-task *shop-names* 'shop:refresh)
        (def:task 'Factory 'factory:refresh)
        (def:task 'Farmer 'refresh))
      (def:stage 'farmer-delivery
        (def:task 'Farmer 'delivery)
        (def:task 'Factory 'receiveDelivery))
      (def:restage 'shop-order-and-pricing-factory-order)
      (def:restage 'factory-receive-order)
      (def:restage 'closing)))

  (builder:addDefRounds
    (def:rounds 6
      (def:restage 'refresh)
      (def:stage 'factory-delivery
        (def:task 'Factory 'factory:delivery)
        (def:players-task *shop-names* 'shop:receive-delivery))
      (def:restage 'farmer-delivery)
      (def:restage 'shop-order-and-pricing-factory-order)
      (def:restage 'factory-receive-order)
      (def:restage 'closing)))

  (builder:addDefRounds
    (def:round
      (def:restage 'refresh)
      (def:restage 'factory-delivery)
      (def:restage 'farmer-delivery)
      (def:parallel-stage 'shop-pricing
        (def:players-task *shop-names* 'shop:pricing))
      (def:restage 'closing)))

  (builder:addDefRounds
    (def:round
      (def:restage 'refresh)
      (def:restage 'factory-delivery)
      (def:restage 'shop-pricing)
      (def:restage 'closing))
    (def:round
      (def:restage 'refresh))))



(define (croquette:assign builder ::GameBuilder u1 ::symbol u2 ::symbol u3 ::symbol)
  (builder:addDefAssignRequests
    (def:assignment-request 'Factory (symbol->string u1))
    (def:assignment-request 'Shop1 (symbol->string u2))
    (def:assignment-request 'Shop2 (symbol->string u3))))



(define (shop:refresh ctx ::Market self ::Shop)
  (define other ::Shop (ctx:getOther self))
  (define sale-msg (to-string "コロッケを1個" self:price "円で販売しました．" "お店にはお客さんが" self:demand "人来ました．"
                              self:sales "個が売れました．" "売り上げは" self:earnings "円です．"))
  (self:showMessage
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
  (self:syncRequestToInput
    (ui:form (to-string  (<h3> (- ctx:roundnum 1) "日目終了")
                         (<p> "次の日に進みます．")))
    (lambda () #t))
  (self:showMessage (<div-class> "alert alert-warning" (to-string (<h4> ctx:roundnum "日目のはじまりです．"))))
  (self:refresh))

(define (factory:refresh ctx ::Market self ::Factory)
  (self:showMessage
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
  (self:syncRequestToInput
    (ui:form (to-string  (<h3> (- ctx:roundnum 1) "日目終了")
                         (<p> "次の日に進みます．")))
    (lambda () #t))
  (self:showMessage (<div-class> "alert alert-warning" (to-string (<h4> ctx:roundnum "日目のはじまりです．"))))
  (self:refresh)
)

(define (shop:init ctx ::Market self ::Shop)
  (define msg
     (<div-class> "alert alert-warning"
        (to-string (<h4> ctx:roundnum "日目がはじまりました．")
                   "初日(0日目)に発注した冷凍コロッケは翌々日(2日目)に納品され，その日から販売できます．<br>"
                   "現在の冷凍コロッケの在庫は" self:stock "個です．")))
  (self:syncRequestToInput  (ui:form msg) (lambda () #t))
  (self:showMessage msg))

(define (factory:init ctx ::Market self ::Factory)
  (define msg
      (<div-class> "alert alert-warning"
        (to-string (<h4> ctx:roundnum "日目がはじまりました．")
                   "初日(0日目)に受ける注文は翌々日(2日目)開始時に納品しなくてはなりません．<br>"
                   "初日(0日目)にじゃがいもを発注すると，翌日(1日目)に農家から納品されて冷凍コロッケを生産し，翌々日(2日目)にへショップへ納品できます．<br>"
                   "現在の冷凍コロッケの在庫は" self:stock "個です．")))
  (self:syncRequestToInput  (ui:form msg) (lambda () #t))
  (self:showMessage msg))


(define (factory:closing ctx ::Market self ::Factory)
  (self:closing))

(define (shop:closing ctx ::Market self ::Shop)
  (self:closing (ctx:distributeDemand self)))


(define (shop:order ctx ::Market self ::Shop)
  (self:syncRequestToInput
    (ui:form
      (to-string  (<h4> ctx:roundnum "日目の発注") self:name "さん，コロッケ工場へ発注して下さい．発注したものは，翌々日の販売前に納品される予定です．")
      (ui:number "個数(冷凍コロッケ)" 'num-of-croquette (self:defaultOrders ctx:roundnum) (Min 0) (Max 1000)))
    (lambda (num-of-croquette ::number)
      (self:order num-of-croquette)
      (self:showMessage (<div-class> "alert alert-success" (to-string "冷凍コロッケを" num-of-croquette "個発注しました．翌々日に納品されます．")))
      (self:sendMessage (make CroquetteOrder self:name 'Factory num-of-croquette)))))

(define (shop:pricing ctx ::Market self ::Shop)
  (self:syncRequestToInput
    (ui:form
          (to-string (<h4> ctx:roundnum "日目の販売価格") self:name "さん，今日のコロッケの販売価格を決定して下さい．")
      (ui:number "販売価格(コロッケ)" 'price (self:defaultPrices ctx:roundnum) (Min 50) (Max 200)))
    (lambda (price-of-croquette ::number)
      (self:showMessage (<div-class> "alert alert-success" (to-string "コロッケ1個の販売価格を" price-of-croquette "円に決めました．")))
      (self:price price-of-croquette))))

(define (shop:order-and-pricing ctx ::Market self ::Player)
  (shop:order ctx self)
  (shop:pricing ctx self))

(define (shop:receive-delivery ctx ::Market self ::Shop)
  (define msg ::CroquetteDelivery (self:takeMessage))
  (self:receiveDelivery msg:num)
  (self:showMessage
    (<div-class> "alert alert-info"
      (to-string "冷凍コロッケが" msg:num "個納品されました．在庫数は" self:stock "個になりました．"))))


(define (factory:order ctx ::Market self ::Factory)
  (self:syncRequestToInput
    (ui:form
      (to-string (<h4> ctx:roundnum "日目の発注") self:name "さん，農場へじゃがいもを発注して下さい．発注したものは，翌日に納品されます．")
      (ui:number "個数(ジャガイモ)" 'potato (self:defaultOrdersToFarmer ctx:roundnum) (Min 0) (Max 1000)))
    (lambda (potato ::number)
      (self:order potato)
      (self:showMessage (<div-class> "alert alert-success" (to-string "じゃがいもを" potato "個発注しました．翌日に納品されます．")))
      (self:sendMessage (make PotatoOrder self:name 'Farmer potato)))))

(define (factory:delivery ctx ::Market self ::Factory)
  (define stock-before-delivery self:stock)
  (define msg "")
  (for-each
    (lambda (shop-name ::symbol)
      (define delivery (self:delivery ctx shop-name stock-before-delivery))
      (set! msg (to-string msg (to-string shop-name "に冷凍コロッケ" delivery "個を納品しました．")))
      (self:sendMessage (make CroquetteDelivery self:name shop-name delivery)))
    *shop-names*)
  (self:showMessage
      (<div-class> "alert alert-info" msg "在庫は" self:stock "個になりました．")))

(define (factory:receive-delivery-msg num production stock) ::String
  (<div-class> "alert alert-info"
    (to-string "じゃがいも" num "個が納品され，" production "個の冷凍コロッケを生産しました．在庫は" stock "個になりました．")))




(define (farmer:delivery ctx ::Market self ::Farmer)
  (self:sendMessage (make PotatoDelivery self:name  'Factory (self:delivery ctx))))

